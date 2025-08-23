package com.example.inventory_server.specification;

import com.example.inventory_server.entity.StockHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class StockHistorySpecifications {

    public static Specification<StockHistory> hasCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, builder) -> {
            if (startDate == null && endDate == null) {
                System.out.println(1);
                return null;
            }
            if (startDate == null) {
                System.out.println(2);
                return builder.lessThanOrEqualTo(root.get("date"), endDate);
            }
            if (endDate == null) {
                System.out.println(3);
                return builder.greaterThanOrEqualTo(root.get("date"), startDate);
            }
            System.out.println(4);
            return builder.between(root.get("date"), startDate, endDate);
        };
    }


    public static Specification<StockHistory> hasCategoryId(Long categoryId) {
        return (root, query, builder) -> {
            if (categoryId == null) {
                return null; // null일 땐 조건 없음
            }
            return builder.equal(root.join("category").get("id"), categoryId);
        };
    }

    public static Specification<StockHistory> hasType(String type) {
        return (root, query, builder) ->
                type == null ? null : builder.like(root.get("type"), "%" + type + "%");
    }

    public static Specification<StockHistory> hasSpecification(String specification) {
        return (root, query, builder) ->
                specification == null ? null : builder.like(root.get("specification"), "%" + specification + "%");
    }

    public static Specification<StockHistory> hasMaker(String maker) {
        return (root, query, builder) ->
                maker == null ? null : builder.like(root.get("maker"), "%" + maker + "%");
    }

    public static Specification<StockHistory> hasMemo(String memo) {
        return (root, query, builder) ->
                memo == null ? null : builder.like(root.get("memo"), "%" + memo + "%");
    }
}
