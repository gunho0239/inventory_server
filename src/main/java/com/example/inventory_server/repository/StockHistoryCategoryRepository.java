package com.example.inventory_server.repository;

import com.example.inventory_server.entity.StockHistoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryCategoryRepository extends JpaRepository<StockHistoryCategory, Long> {}
