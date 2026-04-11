package com.example.inventory_server.repository;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long>, JpaSpecificationExecutor<StockHistory> {
    List<StockHistory> findAllByOrderByDateAsc();
    List<StockHistory> findByCategory_IdOrderByDateAsc(Long categoryId);

    @Query("SELECT sh.date FROM StockHistory sh WHERE sh.category.category = :categoryName ORDER BY sh.date DESC")
    List<LocalDateTime> findDatesByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT sh FROM StockHistory sh WHERE sh.date = :dateTime AND sh.category.category = :categoryName")
    List<StockHistory> findByDateAndCategoryName(@Param("dateTime") LocalDateTime dateTime, @Param("categoryName") String categoryName);
}