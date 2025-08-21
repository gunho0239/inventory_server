package com.example.inventory_server.specification;

import com.example.inventory_server.entity.Stock;
import org.springframework.data.jpa.domain.Specification;

public class StockSpecifications {

    public static Specification<Stock> hasTypeId(Long typeId) {
        return (root, query, builder) -> {
            if (typeId == null) {
                return null; // null일 땐 조건 없음
            }
            return builder.equal(root.join("part").join("type").get("id"), typeId);
        };
    }
    public static Specification<Stock> hasMakerId(Long makerId) {
        return (root, query, builder) -> {
            if (makerId == null) {
                return null; // null일 땐 조건 없음
            }
            return builder.equal(root.join("part").join("maker").get("id"), makerId);
        };
    }
    public static Specification<Stock> hasSpecification(String specification) {
        return (root, query, builder) ->
                specification == null ? null : builder.like(root.join("part").get("specification"), "%" + specification + "%");
    }

    public static Specification<Stock> hasSectionId(Long sectionId) {
        return (root, query, builder) -> {
            if (sectionId == null) {
                return null; // null일 땐 조건 없음
            }
            return builder.equal(root.join("location").join("section").get("id"), sectionId);
        };
    }
    public static Specification<Stock> hasNumber(Integer number) {
        return (root, query, builder) ->
                number == null ? null : builder.equal(root.join("location").get("number"), number);
    }


}
