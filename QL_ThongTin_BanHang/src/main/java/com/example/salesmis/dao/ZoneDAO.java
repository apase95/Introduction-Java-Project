package com.example.salesmis.dao;

import com.example.salesmis.model.entity.Zone;
import java.util.List;
import java.util.Optional;

public interface ZoneDAO {
    List<Zone> findAll();
    Optional<Zone> findById(Long id);
}
