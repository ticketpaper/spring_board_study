package com.example.board.post.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime created_time;
}
