package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 3000)
    private String contents;

//    post객체 입장에서는 한사람이 여러개 글을 쓸수 있으므로 N;1
    @ManyToOne(fetch = FetchType.LAZY)
//    author_id는 db 컬럼 명, 별다른 옵션이 없을경우 pk에 fk가 설정
    @JoinColumn(name = "author_id")
//    @JoinColumn(nullable = false, name = "author_id", referencedColumnName = "email")
    private Author author;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_time;
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime update_time;

    @Builder
    public Post(String title, String contents, Author author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
//        author 객체의 postList를 초기화 시켜준 후
//        this.author.getPostList().add(this);
    }

    public void Update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
