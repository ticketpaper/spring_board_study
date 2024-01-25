package com.example.board.author.controller;


import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

// WebMvcTest를 이용해서 Controlleer 게층을 테스트, 모든 스프링빈을 생성하고 주입하지는 않음.
@WebMvcTest(AuthorController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

//    @Test
////    @WithMockUser // security 의존성 추가 필요
//    void authorDetailTest() throws Exception{
//        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
//        authorDetailResDto.setName("테스트");
//        authorDetailResDto.setEmail("테스트이메일@테스트.com");
//        authorDetailResDto.setPassword("테스트트트");
//        Mockito.when(authorService.findAuthorDetail(1L)).thenReturn(authorDetailResDto);
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", authorDetailResDto.getName()));
//    }
}
