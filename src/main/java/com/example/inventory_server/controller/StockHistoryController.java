package com.example.inventory_server.controller;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.web.bind.annotation.*;
import com.example.inventory_server.service.StockHistoryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-histories")
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;

    public StockHistoryController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping
    public List<StockHistory> getStockHistories() {
        return stockHistoryService.getAllStockHistories();
    }

    @GetMapping("/{id}")
    public StockHistory getStockHistory(@PathVariable Long id) {
        return stockHistoryService.getStockHistoryById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<StockHistory> getStockHistoryByCategoryId(@PathVariable Long categoryId) { return stockHistoryService.getStockHistoryByCategoryId(categoryId); }

    @GetMapping("/search")
    public List<StockHistory> getStockHistories(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String specification,
            @RequestParam(required = false) String maker,
            @RequestParam(required = false) String memo
    ) {
        return stockHistoryService.getStockHistories(startDate, endDate, categoryId, type, specification, maker, memo);
    }

    @PostMapping("/single")
    public StockHistory createStockHistory(@RequestBody StockHistory stockHistory) {
        return stockHistoryService.saveStockHistory(stockHistory);
    }

    @PostMapping("/bulk")
    public List<StockHistory> createStockHistories(@RequestBody List<StockHistory> stockHistories) {
        return stockHistoryService.saveAllHistories(stockHistories);
    }

    @GetMapping("/released/dates")
    public List<LocalDateTime> getReleasedDates() {
        return stockHistoryService.getReleasedDates();
    }

    @GetMapping("/released")
    public List<StockHistory> getReleasedStocksByDate(@RequestParam LocalDateTime date) {
        return stockHistoryService.getReleasedStocksByDate(date);
    }


}