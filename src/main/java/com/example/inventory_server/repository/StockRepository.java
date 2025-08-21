package com.example.inventory_server.repository;

import com.example.inventory_server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock>
{
    List<Stock> findAllByOrderByPart_Type_TypeAscPart_SpecificationAscLocation_Section_SectionAscLocation_NumberAsc();
    List<Stock> findByPart_Type_IdOrderByPart_SpecificationAsc(Long typeId);
    List<Stock> findByPart_Maker_IdOrderByPart_SpecificationAsc(Long makerId);
    List<Stock> findByLocation_Section_IdOrderByPart_SpecificationAsc(Long sectionId);

}
