package com.example.board.author.domain;

import com.example.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//위와같이 모든 매개변수가 있는 생성자를 생성하는 어노테이션과 Buidler를 클래스에 붙여
//모든 매개변수가 있는 생성자 위에 Builder어노테이션을 붙인것과 같은 효과가 있음
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter // cascade.persist 테스트를 위한
//    mappedBy에 연관관계의 주인을 명시하고, fk를 관리하는 변수명을 명시
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    author를 조회할때 post객체가 필요할때에 선언한다.
    private List<Post> postList;


    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

//    builder 어노테이션을 통해 빌더패턴으로 객체 생성
//    매개변수의 세팅순서, 매개변수의 개수등을 유연하게 세팅
    @Builder
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void update(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

