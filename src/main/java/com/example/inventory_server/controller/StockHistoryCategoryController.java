package com.example.inventory_server.controller;

import com.example.inventory_server.entity.StockHistoryCategory;
import com.example.inventory_server.service.StockHistoryCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-history-categories")
public class StockHistoryCategoryController {
    private final StockHistoryCategoryService stockHistoryCategoryService;

    public StockHistoryCategoryController(StockHistoryCategoryService stockHistoryCategoryService) {
        this.stockHistoryCategoryService = stockHistoryCategoryService;
    }

    @GetMapping
    public List<StockHistoryCategory> getSections() {
        return stockHistoryCategoryService.getAllCategories();
    }


}
