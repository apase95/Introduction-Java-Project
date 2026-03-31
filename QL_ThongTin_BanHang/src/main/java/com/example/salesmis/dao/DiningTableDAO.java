package com.example.salesmis.dao;

import com.example.salesmis.model.entity.DiningTable;
import java.util.List;
import java.util.Optional;

public interface DiningTableDAO {
    /** Lấy danh sách tất cả bàn ăn kèm khu vực. */
    List<DiningTable> findAll();
    /** Tìm bàn ăn theo ID kèm khu vực. */
    Optional<DiningTable> findById(Long id);
}
