package org.quanly.bll;

import org.quanly.dal.ProductDAL;
import org.quanly.dto.ProductDTO;
import java.util.List;

public class ProductBLL {
    private final ProductDAL dal = new ProductDAL();

    public List<ProductDTO> getAllProducts() {
        return dal.getAll();
    }

    public String addProduct(ProductDTO p) {
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            return "Tên sản phẩm không được để trống!";
        }
        if (p.getPrice() <= 0) {
            return "Giá sản phẩm phải lớn hơn 0!";
        }
        if (p.getQuantity() < 0) {
            return "Số lượng không được âm!";
        }

        return dal.insert(p) ? "Thêm sản phẩm thành công!" : "Lỗi khi thêm vào cơ sở dữ liệu!";
    }
}

