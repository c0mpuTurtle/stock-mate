package org.example.stockmate.page.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "product_name", columnDefinition = "varchar(200)", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Column(name = "sale_Yn", columnDefinition = "char(10)", nullable = false)
    private char saleYn;

    @Column(name = "count", columnDefinition = "bigint", nullable = false)
    private Long count;

    @Column(name = "price", columnDefinition = "bigint", nullable = false)
    private Long price;

    @Builder

    public Product(Long id, String name, Category category, SubCategory subCategory, char saleYn, Long count, Long price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.saleYn = saleYn;
        this.count = count;
        this.price = price;
    }
}
