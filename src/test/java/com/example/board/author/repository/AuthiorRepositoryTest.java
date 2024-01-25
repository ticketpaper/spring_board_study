package com.example.board.author.repository;


import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

// DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 DB를 원상복구 복구 시킴
// 모든 스프링빈을 생성하지 않고, DB테스트 특화 어노테이션
// replace = AutoConfigureTestDatabase.Replace.ANY : H2DB(spring 내장 인메모리)가 기본설정
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

// SpringBootTest 어노테이션은 자동 롤백 기능 없음, 별도 롤백 코드 or 어노테이션 필요
// SpringBootTest 어노테이션은 실제 스프링 실행과 동일하게 스프링빈 생성 및 주입
//@SpringBootTest
//@Transactional

@ActiveProfiles("test") // application-test.yml 파일을 찾아 설정값 세팅
public class AuthiorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest() {
//        객체를 만들어서 save -> 재조회 -> 만든 객체랑 비교
//        준비(prepare, given)
        Author author = Author.builder()
                .name("계속 생성해도 사라지는 애야")
                .email("어짜피이메일@없는데.com")
                .password("패스워드야")
                .role(Role.ADMIN)
                .build();
//        실행 (excute, when)
        authorRepository.save(author);
        Author authorDb = authorRepository.findByEmail("어짜피이메일@없는데.com").orElse(null);

//        검증(then)
//        Assertions 클래스의 기능을 통해 오류의 원인파악, null처리, 가시적으로 성공/실패 볼 수 있음
        assertEquals(author.getEmail(), authorDb.getEmail());


    }


}
