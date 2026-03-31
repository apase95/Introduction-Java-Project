package com.example.salesmis.model.entity;

import com.example.salesmis.model.enumtype.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 20)
    private String orderNo;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private DiningTable diningTable;

    @Column(name = "total_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.NEW;

    @Column(length = 255)
    private String note;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    /** Thêm một dòng chi tiết vào đơn hàng và thiết lập liên kết ngược. */
    public void addDetail(OrderDetail detail) {
        orderDetails.add(detail);
        detail.setSalesOrder(this);
    }

    /** Xóa sạch toàn bộ chi tiết đơn hàng hiện tại. */
    public void clearDetails() {
        orderDetails.forEach(d -> d.setSalesOrder(null));
        orderDetails.clear();
    }

    public Long getId() { return id; }
    public String getOrderNo() { return orderNo; }
    public LocalDate getOrderDate() { return orderDate; }
    public Customer getCustomer() { return customer; }
    public DiningTable getDiningTable() { return diningTable; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public String getNote() { return note; }
    public List<OrderDetail> getOrderDetails() { return orderDetails; }

    public void setId(Long id) { this.id = id; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setDiningTable(DiningTable diningTable) { this.diningTable = diningTable; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setNote(String note) { this.note = note; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    /** Trả về mã đơn hàng để hiển thị trong danh sách. */
    @Override
    public String toString() {
        return orderNo;
    }

    /** So sánh hai đơn hàng dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesOrder that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    /** Trả về hash code đồng nhất với equals. */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
