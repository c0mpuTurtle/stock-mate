package org.example.stockmate.page.controller;

import lombok.Getter;

@Getter
public class ProductSalesSummaryDto {
    private String name;
    private String category;
    private String subCategory;
    private Long price;
    private Long count; // 총 판매량

    public ProductSalesSummaryDto(String name, String category, String subCategory, Long price, Long count) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.count = count;
    }
}

