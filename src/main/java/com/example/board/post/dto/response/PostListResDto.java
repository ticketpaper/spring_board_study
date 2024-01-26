package com.example.board.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    private String author_name;
}
