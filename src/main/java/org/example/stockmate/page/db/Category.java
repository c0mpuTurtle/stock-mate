package org.example.stockmate.page.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "category_name", columnDefinition = "varchar(50)", nullable = false)
    private String name;

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

