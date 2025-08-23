package com.example.inventory_server.service;

import com.example.inventory_server.entity.Location;
import com.example.inventory_server.entity.Stock;
import com.example.inventory_server.specification.StockSpecifications;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.inventory_server.repository.StockRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StockService {
    private final StockRepository stockRepo;

    public StockService(StockRepository stockRepo) {
        this.stockRepo = stockRepo;
    }

    public List<Stock> getAllStocks() {
        return stockRepo.findAllByOrderByPart_Type_TypeAscPart_SpecificationAscLocation_Section_SectionAscLocation_NumberAsc();
    }

    public Stock getStockById(Long id) {
        return stockRepo.findById(id).orElseThrow();
    }

    public List<Stock> getStockByTypeId(Long typeId) {
        return stockRepo.findByPart_Type_IdOrderByPart_SpecificationAsc(typeId);
    }

    public List<Stock> getStockByMakerId(Long makerId) {
        return stockRepo.findByPart_Maker_IdOrderByPart_SpecificationAsc(makerId);
    }

    public List<Stock> getStockBySectionId(Long sectionId) {
        return stockRepo.findByLocation_Section_IdOrderByPart_SpecificationAsc(sectionId);
    }


    public List<Stock> getStocks(Long typeId, Long makerId, String specification, Long sectionId, Integer number) {
        Specification<Stock> spec = Specification.where(StockSpecifications.hasTypeId(typeId))
                .and(StockSpecifications.hasMakerId(makerId))
                .and(StockSpecifications.hasSpecification(specification))
                .and(StockSpecifications.hasSectionId(sectionId))
                .and(StockSpecifications.hasNumber(number));

        Sort sort = Sort.by(
                Sort.Order.asc("part.type.type"),
                Sort.Order.asc("part.specification"),
                Sort.Order.asc("part.maker.maker"),
                Sort.Order.asc("location.section.section"),
                Sort.Order.asc("location.number")
        );


        return stockRepo.findAll(spec, sort);
    }

    public Stock saveStock(Stock stock) {
        return stockRepo.save(stock);
    }

    public List<Stock> saveAllStocks(List<Stock> stocks) {
        List<Stock> savedStocks = new ArrayList<>();

        for (Stock stock : stocks) {
            savedStocks.add(stockRepo.save(stock));
        }

        return savedStocks;
    }

    @Transactional
    public void deleteStock(Long stockId, Long version) {
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("해당 재고는 이미 삭제되었을 수 있습니다. 새로고침 후 다시 시도해주세요."));

        if (!stock.getVersion().equals(version)) {
            throw new OptimisticLockingFailureException("해당 재고는 이미 수정되었습니다. 새로고침 후 다시 시도해주세요.");
        }

        stockRepo.deleteById(stockId);
    }

    @Transactional
    public void updateStock(Stock newStock) {
        Stock stock = stockRepo.findById(newStock.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 재고는 이미 삭제되었을 수 있습니다. 새로고침 후 다시 시도해주세요."));

        if (!stock.getVersion().equals(newStock.getVersion())) {
            throw new OptimisticLockingFailureException("해당 재고는 이미 수정되었습니다. 새로고침 후 다시 시도해주세요.");
        }

        stock.setQuantity(newStock.getQuantity());
        stock.setLocation(newStock.getLocation());
        stockRepo.save(stock);
    }

//    @Transactional
//    public void updateStockQuantity(Long stockId, Integer newQuantity, Long version) {
//        Stock stock = stockRepo.findById(stockId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 재고는 이미 삭제되었을 수 있습니다. 새로고침 후 다시 시도해주세요."));
//
//        if (!stock.getVersion().equals(version)) {
//            throw new OptimisticLockingFailureException("해당 재고는 이미 수정되었습니다. 새로고침 후 다시 시도해주세요.");
//        }
//
//        stock.setQuantity(newQuantity);
//        stockRepo.save(stock);
//    }

    @Transactional
    public void updateStockLocation(Long stockId, Integer relocatedQuantity, Location newLocation, Long version) {
        Stock originalStock = stockRepo.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("해당 재고는 이미 삭제되었을 수 있습니다. 새로고침 후 다시 시도해주세요."));

        if (!originalStock.getVersion().equals(version)) {
            throw new OptimisticLockingFailureException("해당 재고는 이미 수정되었습니다. 새로고침 후 다시 시도해주세요.");
        }


        // 전체 수량 이동시
        if (Objects.equals(relocatedQuantity, originalStock.getQuantity())) {
            originalStock.setLocation(newLocation);
        }
        else { // 일부 수량 이동시
            Stock relocatedStock = new Stock();
            relocatedStock.setPart(originalStock.getPart());
            relocatedStock.setQuantity(relocatedQuantity);
            relocatedStock.setLocation(newLocation);

            stockRepo.save(relocatedStock); // 이동 수량만큼 새로 생성

            originalStock.setQuantity(originalStock.getQuantity() - relocatedQuantity); // 기존 재고는 이동 수량을 제외하여 갱신
        }

        stockRepo.save(originalStock);
    }
}
