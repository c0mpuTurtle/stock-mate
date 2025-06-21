package org.example.stockmate.page.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    private Long userId;

    @Column(name = "date", columnDefinition = "varchar(30)", nullable = false)
    private String date;

    @Builder
    public Order(Long id, Long userId, String date) {
        this.id = id;
        this.userId = userId;
        this.date = date;
    }
}
