package com.emotionBot.db.member.entity;

import com.emotionBot.enums.KeyboardYn;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @Column(name = "login_id", columnDefinition = "varchar(100)", nullable = false)
    private String loginId;

    @Column(name = "password", columnDefinition = "varchar(100)", nullable = false)
    private String password;

    @Column(name = "nickname", columnDefinition = "varchar(100)", nullable = false)
    private String nickname;

    @Column(name = "clover", columnDefinition = "bigint(10000)", nullable = false)
    private int clover;

    @Column(name = "keyboard_mode", columnDefinition = "varchar(5)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private KeyboardYn keyBoardYn;
}
