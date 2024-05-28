package com.kr.earlyword.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createAt;
}