package com.example.inventory_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PartType type;
    private String specification;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private PartMaker maker;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private PartUnit unit;
}
