package com.example.salesmis.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_code", nullable = false, unique = true, length = 20)
    private String zoneCode;

    @Column(name = "zone_name", nullable = false, length = 100)
    private String zoneName;

    @Column(name = "surcharge_percent", precision = 5, scale = 2)
    private BigDecimal surchargePercent = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY)
    private List<DiningTable> diningTables = new ArrayList<>();

    public Zone() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZoneCode() { return zoneCode; }
    public void setZoneCode(String zoneCode) { this.zoneCode = zoneCode; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public BigDecimal getSurchargePercent() { return surchargePercent; }
    public void setSurchargePercent(BigDecimal surchargePercent) { this.surchargePercent = surchargePercent; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<DiningTable> getDiningTables() { return diningTables; }
    public void setDiningTables(List<DiningTable> diningTables) { this.diningTables = diningTables; }

    @Override
    public String toString() { return zoneName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zone that)) return false;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return getClass().hashCode(); }
}
