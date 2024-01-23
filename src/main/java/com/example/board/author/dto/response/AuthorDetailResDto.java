package com.example.board.author.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private String role;
}
