package com.example.inventory_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private StockHistoryCategory category;

    private String note;
    private String type;
    private String specification;
    private String maker;
    private String unit;
    private Integer beforeQuantity;
    private Integer afterQuantity;
    private String beforeLocation;
    private String afterLocation;
    private String person;

    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }
}
