package org.example.stockmate.page.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "sub_category_name", columnDefinition = "varchar(100)", nullable = false)
    private String name;

    @Builder
    public SubCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

