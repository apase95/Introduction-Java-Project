package com.example.salesmis.service.impl;

import com.example.salesmis.dao.CustomerDAO;
import com.example.salesmis.dao.ProductDAO;
import com.example.salesmis.dao.RecipeDAO;
import com.example.salesmis.dao.SalesOrderDAO;
import com.example.salesmis.dao.DiningTableDAO;
import com.example.salesmis.model.dto.OrderLineInput;
import com.example.salesmis.model.entity.Customer;
import com.example.salesmis.model.entity.DiningTable;
import com.example.salesmis.model.entity.OrderDetail;
import com.example.salesmis.model.entity.Product;
import com.example.salesmis.model.entity.Recipe;
import com.example.salesmis.model.entity.SalesOrder;
import com.example.salesmis.model.enumtype.OrderStatus;
import com.example.salesmis.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private final SalesOrderDAO salesOrderDAO;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;
    private final DiningTableDAO diningTableDAO;
    private final RecipeDAO recipeDAO;

    /** Constructor nhận các DAO cần thiết từ bên ngoài (dependency injection). */
    public OrderServiceImpl(SalesOrderDAO salesOrderDAO, CustomerDAO customerDAO, ProductDAO productDAO, DiningTableDAO diningTableDAO, RecipeDAO recipeDAO) {
        this.salesOrderDAO = salesOrderDAO;
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.diningTableDAO = diningTableDAO;
        this.recipeDAO = recipeDAO;
    }

    /** Lấy toàn bộ danh sách đơn hàng. */
    @Override
    public List<SalesOrder> getAllOrders() {
        return salesOrderDAO.findAll();
    }
    /** Tìm kiếm đơn hàng; trả toàn bộ nếu từ khóa trống. */
    @Override
    public List<SalesOrder> searchOrders(String keyword) {
        if (keyword == null || keyword.isBlank()) return salesOrderDAO.findAll();
        return salesOrderDAO.searchByKeyword(keyword.trim());
    }

    /** Lấy đơn hàng theo ID; ném ngoại lệ nếu không tìm thấy. */
    @Override
    public SalesOrder getOrderById(Long id) {
        return salesOrderDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng id = " + id));
    }

    /** Tạo mới đơn hàng: validate, kết nối thực thể, trừ kho (nếu COMPLETED), tính tổng tiền. */
    @Override
    public SalesOrder createOrder(String orderNo, LocalDate orderDate, Long customerId, Long tableId,
                                  OrderStatus status, String note, List<OrderLineInput> lines) {
        validate(orderNo, orderDate, customerId, lines);

        if (salesOrderDAO.findByOrderNo(orderNo.trim()).isPresent()) {
            throw new IllegalArgumentException("Mã đơn hàng đã tồn tại.");
        }

        Customer customer = customerDAO.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng."));

        DiningTable table = null;
        if (tableId != null) {
            table = diningTableDAO.findById(tableId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bàn/vị trí."));
        }

        SalesOrder order = new SalesOrder();
        order.setOrderNo(orderNo.trim());
        order.setOrderDate(orderDate);
        order.setCustomer(customer);
        order.setDiningTable(table);
        order.setStatus(status);
        order.setNote(note);

        if (status == OrderStatus.COMPLETED) {
            Map<Long, Integer> stockChanges = new HashMap<>();
            for (OrderLineInput line : lines) {
                stockChanges.put(line.getProductId(), stockChanges.getOrDefault(line.getProductId(), 0) - line.getQuantity());
            }
            List<Product> productsToUpdate = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : stockChanges.entrySet()) {
                Product p = productDAO.findById(entry.getKey())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm id = " + entry.getKey()));
                if (p.getStockQty() + entry.getValue() < 0) {
                    throw new IllegalArgumentException("Sản phẩm " + p.getProductName() + " không đủ tồn kho.");
                }
                p.setStockQty(p.getStockQty() + entry.getValue());
                productsToUpdate.add(p);
            }
            for (Product p : productsToUpdate) {
                productDAO.save(p);
            }
        }

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineInput line : lines) {
            Product product = productDAO.findById(line.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm id = " + line.getProductId()));

            Recipe recipe = null;
            if (line.getRecipeId() != null) {
                recipe = recipeDAO.findById(line.getRecipeId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kích cỡ/biến thể id = " + line.getRecipeId()));
            }

            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setRecipe(recipe);
            detail.setQuantity(line.getQuantity());
            detail.setUnitPrice(line.getUnitPrice());
            detail.setLineTotal(line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity())));

            total = total.add(detail.getLineTotal());
            order.addDetail(detail);
        }

        order.setTotalAmount(total);
        return salesOrderDAO.save(order);
    }

    /** Cập nhật đơn hàng: hoàn kho cũ, trừ kho mới (nếu COMPLETED), thay thế toàn bộ chi tiết. */
    @Override
    public SalesOrder updateOrder(Long id, String orderNo, LocalDate orderDate, Long customerId, Long tableId,
                                  OrderStatus status, String note, List<OrderLineInput> lines) {
        validate(orderNo, orderDate, customerId, lines);
        SalesOrder existing = getOrderById(id);
        Customer customer = customerDAO.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng."));

        DiningTable table = null;
        if (tableId != null) {
            table = diningTableDAO.findById(tableId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bàn/vị trí."));
        }

        salesOrderDAO.findByOrderNo(orderNo.trim()).ifPresent(found -> {
            if (!found.getId().equals(id)) {
                throw new IllegalArgumentException("Mã đơn hàng đã được dùng cho đơn khác.");
            }
        });

        Map<Long, Integer> stockChanges = new HashMap<>();

        if (existing.getStatus() == OrderStatus.COMPLETED) {
            for (OrderDetail oldDetail : existing.getOrderDetails()) {
                Long pId = oldDetail.getProduct().getId();
                stockChanges.put(pId, stockChanges.getOrDefault(pId, 0) + oldDetail.getQuantity());
            }
        }

        if (status == OrderStatus.COMPLETED) {
            for (OrderLineInput line : lines) {
                Long pId = line.getProductId();
                stockChanges.put(pId, stockChanges.getOrDefault(pId, 0) - line.getQuantity());
            }
        }

        List<Product> productsToUpdate = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : stockChanges.entrySet()) {
            if (entry.getValue() == 0) continue;
            Product p = productDAO.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm id = " + entry.getKey()));
            if (p.getStockQty() + entry.getValue() < 0) {
                throw new IllegalArgumentException("Sản phẩm " + p.getProductName() + " không đủ tồn kho.");
            }
            p.setStockQty(p.getStockQty() + entry.getValue());
            productsToUpdate.add(p);
        }

        for (Product p : productsToUpdate) {
            productDAO.save(p);
        }

        existing.setOrderNo(orderNo.trim());
        existing.setOrderDate(orderDate);
        existing.setCustomer(customer);
        existing.setDiningTable(table);
        existing.setStatus(status);
        existing.setNote(note);
        existing.clearDetails();

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineInput line : lines) {
            Product product = productDAO.findById(line.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm id = " + line.getProductId()));

            Recipe recipe = null;
            if (line.getRecipeId() != null) {
                recipe = recipeDAO.findById(line.getRecipeId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kích cỡ/biến thể id = " + line.getRecipeId()));
            }

            OrderDetail detail = new OrderDetail();
            detail.setProduct(product);
            detail.setRecipe(recipe);
            detail.setQuantity(line.getQuantity());
            detail.setUnitPrice(line.getUnitPrice());
            detail.setLineTotal(line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
            total = total.add(detail.getLineTotal());
            existing.addDetail(detail);
        }

        existing.setTotalAmount(total);
        return salesOrderDAO.update(existing);
    }

    /** Xóa đơn hàng đã COMPLETED: hoàn trả tồn kho các sản phẩm rồi xóa. */
    @Override
    public void deleteOrder(Long id) {
        SalesOrder existing = getOrderById(id);
        if (existing.getStatus() == OrderStatus.COMPLETED) {
            Map<Long, Integer> stockChanges = new HashMap<>();
            for (OrderDetail oldDetail : existing.getOrderDetails()) {
                Long pId = oldDetail.getProduct().getId();
                stockChanges.put(pId, stockChanges.getOrDefault(pId, 0) + oldDetail.getQuantity());
            }
            for (Map.Entry<Long, Integer> entry : stockChanges.entrySet()) {
                Product p = productDAO.findById(entry.getKey()).orElse(null);
                if (p != null) {
                    p.setStockQty(p.getStockQty() + entry.getValue());
                    productDAO.save(p);
                }
            }
        }
        salesOrderDAO.deleteById(id);
    }

    /** Xác thực các trường bắt buộc của đơn hàng; ném ngoại lệ nếu dữ liệu thiếu hoặc không hợp lệ. */
    private void validate(String orderNo, LocalDate orderDate, Long customerId, List<OrderLineInput> lines) {
        if (orderNo == null || orderNo.isBlank()) throw new IllegalArgumentException("Order No không được trống.");
        if (orderDate == null) throw new IllegalArgumentException("Ngày đơn hàng không được trống.");
        if (customerId == null) throw new IllegalArgumentException("Khách hàng không được trống.");
        if (lines == null || lines.isEmpty()) throw new IllegalArgumentException("Đơn hàng phải có ít nhất 1 dòng chi tiết.");
        for (OrderLineInput line : lines) {
            if (line.getProductId() == null) throw new IllegalArgumentException("Thiếu sản phẩm.");
            if (line.getQuantity() <= 0) throw new IllegalArgumentException("Số lượng phải > 0.");
            if (line.getUnitPrice() == null || line.getUnitPrice().signum() < 0) {
                throw new IllegalArgumentException("Đơn giá không hợp lệ.");
            }
        }
    }
}
