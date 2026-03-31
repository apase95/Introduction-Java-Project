package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Zone;
import java.util.List;
import java.util.Optional;

public interface ZoneDAO {
    /** Lấy danh sách tất cả khu vực. */
    List<Zone> findAll();
    /** Tìm khu vực theo ID. */
    Optional<Zone> findById(Long id);
}
