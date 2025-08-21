package com.example.inventory_server.repository;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long>{
    List<StockHistory> findAllByOrderByDateAsc();
    List<StockHistory> findByCategory_IdOrderByDateAsc(Long categoryId);
}