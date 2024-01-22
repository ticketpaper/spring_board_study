package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.request.AuthorSaveReqDto;
import com.example.board.author.dto.response.AuthorDetailResDto;
import com.example.board.author.dto.response.AuthorListResDto;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Author author = new Author(authorSaveReqDto.getName(), authorSaveReqDto.getEmail(), authorSaveReqDto.getPassword());
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

    public AuthorDetailResDto authorDetail(Long id) {
        Author byId = authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        authorDetailResDto.setId(byId.getId());
        authorDetailResDto.setName(byId.getName());
        authorDetailResDto.setEmail(byId.getEmail());
        authorDetailResDto.setPassword(byId.getPassword());
        authorDetailResDto.setCreatedTime(byId.getCreatedTime());

        return authorDetailResDto;
    }

}
