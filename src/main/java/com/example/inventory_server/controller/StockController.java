package com.example.inventory_server.controller;

import com.example.inventory_server.dto.BulkRequestResponseWithId;
import com.example.inventory_server.dto.SingleRequestResponse;
import com.example.inventory_server.entity.Stock;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.inventory_server.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> getStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Stock getStock(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    @GetMapping("/type/{typeId}")
    public List<Stock> getStockByTypeId(@PathVariable Long typeId) { return stockService.getStockByTypeId(typeId); }

    @GetMapping("/maker/{makerId}")
    public List<Stock> getStockByMakerId(@PathVariable Long makerId) { return stockService.getStockByMakerId(makerId); }

    @GetMapping("/section/{sectionId}")
    public List<Stock> getStockBySectionId(@PathVariable Long sectionId) { return stockService.getStockBySectionId(sectionId); }

    @GetMapping("/search")
    public List<Stock> getStocks(
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long makerId,
            @RequestParam(required = false) String specification,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Integer number
    ) {
        return stockService.getStocks(typeId, makerId, specification, sectionId, number);
    }


    @PostMapping("/single")
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @PostMapping("/bulk")
    public List<Stock> createStocks(@RequestBody List<Stock> stocks) {
        return stockService.saveAllStocks(stocks);
    }

    @DeleteMapping("/single")
    public ResponseEntity<SingleRequestResponse> deleteStock(@RequestBody Stock stock) {
        SingleRequestResponse response = new SingleRequestResponse();

        try {
            stockService.deleteStock(stock.getId(), stock.getVersion());
            response.setSuccess(true);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("예상치 못한 오류 발생");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BulkRequestResponseWithId> deleteStocks(@RequestBody List<Stock> stocks) {
        BulkRequestResponseWithId result = stockService.deleteStocks(stocks);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/single")
    public ResponseEntity<SingleRequestResponse> updateStock(@RequestBody Stock stock) {
        SingleRequestResponse response = new SingleRequestResponse();

        try {
            stockService.updateStock(stock);
            response.setSuccess(true);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("예상치 못한 오류 발생");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/bulk")
    public ResponseEntity<BulkRequestResponseWithId> updateStocks(@RequestBody List<Stock> stocks) {
        BulkRequestResponseWithId result = stockService.updateStocks(stocks);

        return ResponseEntity.ok(result);
    }

//    @PutMapping("/quantity")
//    public ResponseEntity<SingleRequestResponse> updateStockQuantity(@RequestBody Stock stock) {
//        SingleRequestResponse response = new SingleRequestResponse();
//
//        try {
//            stockService.updateStockQuantity(stock.getId(), stock.getQuantity(), stock.getVersion());
//            response.setSuccess(true);
//        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
//            response.setSuccess(false);
//            response.setMessage(e.getMessage());
//        } catch (Exception e) {
//            response.setSuccess(false);
//            response.setMessage("예상치 못한 오류 발생");
//        }
//
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/location")
    public ResponseEntity<SingleRequestResponse> updateStockLocation(@RequestBody Stock stock) {
        SingleRequestResponse response = new SingleRequestResponse();

        try {
            stockService.updateStockLocation(stock.getId(), stock.getQuantity(), stock.getLocation(), stock.getVersion());
            response.setSuccess(true);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("예상치 못한 오류 발생");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/locations")
    public ResponseEntity<BulkRequestResponseWithId> updateStockLocations(@RequestBody List<Stock> stocks) {
        BulkRequestResponseWithId result = stockService.updateStockLocations(stocks);

        return ResponseEntity.ok(result);
    }

}