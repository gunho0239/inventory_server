package com.example.inventory_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Version
    private Long version;
}
