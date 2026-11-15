package com.example.inventory_server.service;

import com.example.inventory_server.dto.BulkRequestResponseWithId;
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
import java.util.Optional;

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
        Optional<Stock> existingOpt = stockRepo.findByPart_IdAndLocation_Id(
                stock.getPart().getId(), stock.getLocation().getId());

        if (existingOpt.isPresent()) {
            Stock existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + stock.getQuantity());
            stockRepo.save(existing);
        }
        else {
            stockRepo.save(stock);
        }

        return stock;
    }

    public List<Stock> saveAllStocks(List<Stock> stocks) {
        List<Stock> savedStocks = new ArrayList<>();

        for (Stock stock : stocks) {
            Optional<Stock> existingOpt = stockRepo.findByPart_IdAndLocation_Id(
                    stock.getPart().getId(), stock.getLocation().getId());

            if (existingOpt.isPresent()) {
                Stock existing = existingOpt.get();
                existing.setQuantity(existing.getQuantity() + stock.getQuantity());
                stockRepo.save(existing);
            } else {
                stockRepo.save(stock);
            }
            savedStocks.add(stock);
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
    public BulkRequestResponseWithId deleteStocks(List<Stock> stocks) {
        BulkRequestResponseWithId result = new BulkRequestResponseWithId();

        for (Stock stock : stocks) {
            Long stockId = stock.getId();
            Long version = stock.getVersion();

            try {
                Optional<Stock> stockOpt = stockRepo.findById(stockId);

                if (stockOpt.isEmpty()) {
                    result.addFailedId(stockId);
                    continue;
                }

                Stock inquiredStock = stockOpt.get();

                if (!inquiredStock.getVersion().equals(version)) {
                    result.addFailedId(stockId);
                    continue;
                }

                stockRepo.deleteById(stockId);
                result.addSuccessId(stockId);
            } catch (OptimisticLockingFailureException e) {
                result.addFailedId(stockId);
            } catch (Exception e) {
                result.addFailedId(stockId);
            }
        }

        return result;
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

    @Transactional
    public BulkRequestResponseWithId updateStocks(List<Stock> newStocks) {
        BulkRequestResponseWithId result = new BulkRequestResponseWithId();

        for (Stock newStock: newStocks) {
            Long stockId = newStock.getId();

            try {
                Optional<Stock> stockOpt = stockRepo.findById(stockId);

                if (stockOpt.isEmpty()) {
                    result.addFailedId(stockId);
                    continue;
                }

                Stock inquiredStock = stockOpt.get();

                if (!inquiredStock.getVersion().equals(newStock.getVersion())) {
                    result.addFailedId(stockId);
                    continue;
                }

                inquiredStock.setQuantity(newStock.getQuantity());
                inquiredStock.setLocation(newStock.getLocation());
                stockRepo.save(inquiredStock);
                result.addSuccessId(stockId);
            } catch (OptimisticLockingFailureException e) {
                result.addFailedId(stockId);
            } catch (Exception e) {
                result.addFailedId(stockId);
            }
        }

        return result;
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

        Optional<Stock> destinationOpt = stockRepo.findByPart_IdAndLocation_Id(
                originalStock.getPart().getId(), newLocation.getId());

        if (destinationOpt.isPresent()) {
            Stock destination = destinationOpt.get();
            destination.setQuantity(destination.getQuantity() + relocatedQuantity);
            stockRepo.save(destination);

            if (Objects.equals(relocatedQuantity, originalStock.getQuantity())) {
                stockRepo.delete(originalStock);
            } else {
                originalStock.setQuantity(originalStock.getQuantity() - relocatedQuantity);
                stockRepo.save(originalStock);
            }
        } else {
            if (Objects.equals(relocatedQuantity, originalStock.getQuantity())) {
                originalStock.setLocation(newLocation);
            } else {
                Stock relocatedStock = new Stock();
                relocatedStock.setPart(originalStock.getPart());
                relocatedStock.setQuantity(relocatedQuantity);
                relocatedStock.setLocation(newLocation);
                stockRepo.save(relocatedStock);

                originalStock.setQuantity(originalStock.getQuantity() - relocatedQuantity);
            }
            stockRepo.save(originalStock);
        }
    }

    @Transactional
    public BulkRequestResponseWithId updateStockLocations(List<Stock> changedStocks) {
        BulkRequestResponseWithId result = new BulkRequestResponseWithId();

        for (Stock changedStock : changedStocks) {
            Long stockId = changedStock.getId();

            try {
                Optional<Stock> stockOpt = stockRepo.findById(stockId);

                if (stockOpt.isEmpty()) {
                    result.addFailedId(stockId);
                    continue;
                }

                Stock originalStock = stockOpt.get();

                if (!originalStock.getVersion().equals(changedStock.getVersion())) {
                    result.addFailedId(stockId);
                    continue;
                }

                Integer moveQuantity = changedStock.getQuantity();
                Location moveLocation = changedStock.getLocation();

                Optional<Stock> destinationOpt = stockRepo.findByPart_IdAndLocation_Id(
                        originalStock.getPart().getId(), moveLocation.getId());

                if (destinationOpt.isPresent()) {
                    Stock destination = destinationOpt.get();
                    destination.setQuantity(destination.getQuantity() + moveQuantity);
                    stockRepo.save(destination);

                    if (Objects.equals(moveQuantity, originalStock.getQuantity())) {
                        stockRepo.delete(originalStock);
                    } else {
                        originalStock.setQuantity(originalStock.getQuantity() - moveQuantity);
                        stockRepo.save(originalStock);
                    }
                } else {
                    if (Objects.equals(moveQuantity, originalStock.getQuantity())) {
                        originalStock.setLocation(moveLocation);
                    } else {
                        Stock relocatedStock = new Stock();
                        relocatedStock.setPart(originalStock.getPart());
                        relocatedStock.setQuantity(moveQuantity);
                        relocatedStock.setLocation(moveLocation);
                        stockRepo.save(relocatedStock);

                        originalStock.setQuantity(originalStock.getQuantity() - moveQuantity);
                    }
                    stockRepo.save(originalStock);
                }

                result.addSuccessId(stockId);
            } catch (OptimisticLockingFailureException e) {
                result.addFailedId(stockId);
            } catch (Exception e) {
                result.addFailedId(stockId);
            }
        }

        return result;
    }
}
