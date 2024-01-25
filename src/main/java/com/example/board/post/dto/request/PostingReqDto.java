package com.example.board.post.dto.request;

import lombok.Data;

@Data
public class PostingReqDto {
    private String title;
    private String contents;
    private String email;
}
