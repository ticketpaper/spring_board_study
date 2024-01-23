package com.example.board.author.dto.request;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private Long id;
    private String email;
    private String name;
    private String password;
}
