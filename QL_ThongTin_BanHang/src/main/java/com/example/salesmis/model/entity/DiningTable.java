package com.example.salesmis.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dining_tables")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(nullable = false, length = 20)
    private String status = "AVAILABLE";

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "diningTable", fetch = FetchType.LAZY)
    private List<SalesOrder> salesOrders = new ArrayList<>();

    /** Constructor mặc định không tham số, JPA yêu cầu. */
    public DiningTable() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<SalesOrder> getSalesOrders() { return salesOrders; }
    public void setSalesOrders(List<SalesOrder> salesOrders) { this.salesOrders = salesOrders; }

    /** Trả về tên bàn kèm khu vực để hiển thị trong ComboBox. */
    @Override
    public String toString() { return tableName + " (" + (zone != null ? zone.getZoneName() : "") + ")"; }

    /** So sánh hai bàn ăn dựa trên ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiningTable that)) return false;
        return id != null && Objects.equals(id, that.id);
    }
    /** Trả về hash code đồng nhất với equals. */
    @Override
    public int hashCode() { return getClass().hashCode(); }
}
