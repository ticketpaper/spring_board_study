package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dto.request.AuthorSaveReqDto;
import com.example.board.author.dto.request.AuthorUpdateReqDto;
import com.example.board.author.dto.response.AuthorDetailResDto;
import com.example.board.author.dto.response.AuthorListResDto;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("USER")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }
//        일반 생성자 방식
//        Author author = new Author(
//                authorSaveReqDto.getName(),
//                authorSaveReqDto.getEmail(),
//                authorSaveReqDto.getPassword(),
//                role
//        );

//        빌더패턴
        Author author = Author.builder()
                        .name(authorSaveReqDto.getName())
                        .email(authorSaveReqDto.getEmail())
                        .password(authorSaveReqDto.getPassword())
                        .build();

////        cascade.persist 테스트
////        부모테이블을 통해 자식테이블에 객체를 동시에 생성
////        List<Post> postList = new ArrayList<>();
//        Post.builder()
//                .title(author.getName()+" 입니다.")
//                .contents("테스트 입니다.")
//                .author(author)
//                .build();
//        postList.add(post);
//        author.setPostList(postList);

        authorRepository.save(author);

    }

    public List<AuthorListResDto> authorList() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtoList = new ArrayList<>();
        for (Author author : authorList) {
            AuthorListResDto authorListResDto1 = new AuthorListResDto();
            authorListResDto1.setId(author.getId());
            authorListResDto1.setName(author.getName());
            authorListResDto1.setEmail(author.getEmail());
            authorListResDtoList.add(authorListResDto1);
        }
        return authorListResDtoList;
    }

    public Author findById(Long id) throws EntityNotFoundException{
        Author byId = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return byId;
    }

    public Author findByEmail(String email) throws EntityNotFoundException{
        Author author = authorRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return author;
    }

    public AuthorDetailResDto findAuthorDetail(Long id){
        Author byId = this.findById(id);
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        authorDetailResDto.setId(byId.getId());
        authorDetailResDto.setName(byId.getName());
        authorDetailResDto.setEmail(byId.getEmail());
        authorDetailResDto.setPassword(byId.getPassword());
        authorDetailResDto.setCreatedTime(byId.getCreatedTime());
        if (byId.getRole() == Role.ADMIN) {
            authorDetailResDto.setRole("관리자");
        } else {
            authorDetailResDto.setRole("일반유저");
        }
        authorDetailResDto.setPostCount(byId.getPostList().size());
        return authorDetailResDto;
    }

    @Transactional
    public Long authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = this.findByEmail(authorUpdateReqDto.getEmail());
        author.update(authorUpdateReqDto.getName(), authorUpdateReqDto.getPassword());
//        명시적으로 save를 하지 않더라도, jpa의 영속성 컨텍스트를 통해,
//        객체의 변경이 감지(더티 체킹)되면, 타랜잭션이 완료되는 시점에 save 동작
//        authorRepository.save(author);
        return author.getId();
    }


    public void authorDelete(Long id) {
        Author byId = this.findById(id);
        authorRepository.delete(byId);
    }

}
