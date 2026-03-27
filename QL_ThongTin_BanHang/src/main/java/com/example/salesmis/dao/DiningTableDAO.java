package com.example.salesmis.dao;

import com.example.salesmis.model.entity.DiningTable;
import java.util.List;
import java.util.Optional;

public interface DiningTableDAO {
    List<DiningTable> findAll();
    Optional<DiningTable> findById(Long id);
}
