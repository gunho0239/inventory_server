package com.example.inventory_server.service;

import com.example.inventory_server.entity.StockHistoryCategory;
import com.example.inventory_server.repository.StockHistoryCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryCategoryService {
    private final StockHistoryCategoryRepository categoryRepo;

    public StockHistoryCategoryService(StockHistoryCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<StockHistoryCategory> getAllCategories() {
        return categoryRepo.findAll();
    }

}