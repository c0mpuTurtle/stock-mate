package org.example.stockmate.page.db;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "student_id", columnDefinition = "varchar(20)", nullable = false)
    private String loginId;

    @Column(name = "department", columnDefinition = "varchar(50)", nullable = false)
    private String password;

    @Column(name = "name", columnDefinition = "varchar(50)", nullable = false)
    private String name;

    @Builder
    public Manager(Long id, String loginId, String password, String name) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}
