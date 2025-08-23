package com.example.inventory_server.repository;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long>, JpaSpecificationExecutor<StockHistory> {
    List<StockHistory> findAllByOrderByDateAsc();
    List<StockHistory> findByCategory_IdOrderByDateAsc(Long categoryId);
}