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

    public AuthorDetailResDto findauthorDetail(Long id){
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
        return authorDetailResDto;
    }

    public Long authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = this.findByEmail(authorUpdateReqDto.getEmail());
        author.update(authorUpdateReqDto.getName(), authorUpdateReqDto.getPassword());
        authorRepository.save(author);
        return author.getId();
    }

    public void authorDelete(Long id) {
        Author byId = this.findById(id);
        authorRepository.delete(byId);
    }

}
