package com.earlyword.domain;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Member {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private LocalDateTime createDate;
    // @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String email, String password, String nickname, String phone, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.role = role;
    }

    public Member update(String email) {
        this.email = email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}