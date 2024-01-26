package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.request.AuthorUpdateReqDto;
import com.example.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

//    가짜객체를 만드는 작업을 목킹이라고 함
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAllTest() {
//        Mock Repository 기능 구현
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author());
        authorList.add(new Author());

        Mockito.when(authorRepository.findAll()).thenReturn(authorList);

//        검증
        assertEquals(authorList.size(),authorService.authorList().size());
    }

    @Test
    void updateTest() {
        // Given
        String email = "테슷트@테스트.com";
        Author author = Author.builder()
                .name("테32스323트")
                .email(email)
                .password("텟스틋")
                .build();

        // Mock Repository 설정
        Mockito.when(authorRepository.findByEmail(email)).thenReturn(Optional.of(author));

        // When
        // 테스트할 DTO 생성
        AuthorUpdateReqDto authorUpdateReqDto = new AuthorUpdateReqDto();
        authorUpdateReqDto.setName("수정해버렸스");
        authorUpdateReqDto.setEmail(email);
        authorUpdateReqDto.setPassword("수정해버려");

        // 테스트할 메서드 호출
        authorService.authorUpdate(authorUpdateReqDto);

        // Then
        // 결과 검증
        assertEquals(authorUpdateReqDto.getName(), author.getName());
        assertEquals(authorUpdateReqDto.getPassword(), author.getPassword());
    }

}
