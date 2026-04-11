package com.example.inventory_server.service;

import com.example.inventory_server.entity.StockHistory;
import com.example.inventory_server.specification.StockHistorySpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.StockHistoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<StockHistory> getStockHistories(LocalDateTime startDate, LocalDateTime endDate, Long categoryId, String type, String specification, String maker, String memo) {
        Specification<StockHistory> spec = Specification.where(StockHistorySpecifications.hasCreatedDateBetween(startDate, endDate))
                .and(StockHistorySpecifications.hasCategoryId(categoryId))
                .and(StockHistorySpecifications.hasType(type))
                .and(StockHistorySpecifications.hasSpecification(specification))
                .and(StockHistorySpecifications.hasMaker(maker))
                .and(StockHistorySpecifications.hasMemo(memo));

        Sort sort = Sort.by(
                Sort.Order.desc("date")
        );

        return stockHistoryRepo.findAll(spec, sort);
    }

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

    public List<LocalDateTime> getReleasedDates() {
        return stockHistoryRepo.findDatesByCategoryName("release").stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<StockHistory> getReleasedStocksByDate(LocalDateTime dateTime) {
        return stockHistoryRepo.findByDateAndCategoryName(dateTime, "release");
    }
}
