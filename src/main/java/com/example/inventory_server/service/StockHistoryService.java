package com.example.inventory_server.service;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.StockHistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepo;

    public StockHistoryService(StockHistoryRepository stockHistoryRepo) {
        this.stockHistoryRepo = stockHistoryRepo;
    }

    public List<StockHistory> getAllStockHistories() {
        return stockHistoryRepo.findAllByOrderByDateAsc();
    }

    public StockHistory getStockHistoryById(Long id) {
        return stockHistoryRepo.findById(id).orElseThrow();
    }

    public List<StockHistory> getStockHistoryByCategoryId(Long categoryId) { return stockHistoryRepo.findByCategory_IdOrderByDateAsc(categoryId); }

    public StockHistory saveStockHistory(StockHistory stockHistory) {
        return stockHistoryRepo.save(stockHistory);
    }

    public List<StockHistory> saveAllHistories(List<StockHistory> stockHistories) {
        List<StockHistory> savedHistories = new ArrayList<>();

        for (StockHistory history : stockHistories) {
            savedHistories.add(stockHistoryRepo.save(history));
        }

        return savedHistories;
    }
}
