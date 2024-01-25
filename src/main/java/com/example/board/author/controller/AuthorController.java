package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.request.AuthorSaveReqDto;
import com.example.board.author.dto.request.AuthorUpdateReqDto;
import com.example.board.author.dto.response.AuthorDetailResDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/author/create")
    public String authorCreateGet() {
        return "/author/author-create";
    }

    @PostMapping("/author/create")
    public String authorCreatePost(AuthorSaveReqDto authorSaveReqDto) {
        authorService.save(authorSaveReqDto);
        return "redirect:/author/list";
    }


    @GetMapping("/author/list")
    public String authorList(Model model) {
        model.addAttribute("authorList", authorService.authorList());
        return "author/author-list";
    }

    @GetMapping("author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        model.addAttribute("authorList", authorService.findAuthorDetail(id));
        return "author/author-detail";
    }

    @PostMapping("author/update")
    public String authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) {
        Long l = authorService.authorUpdate(authorUpdateReqDto);
        return "redirect:/author/detail/" + l;
    }

    @GetMapping("author/delete/{id}")
    private String authorDelete(@PathVariable Long id) {
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

//    엔티티 순환참조 이슈 테스트
    @GetMapping("author/{id}/circle/entity")
    @ResponseBody
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될 경우
//    순환참조 이슈 발생하므로, dto 사용 필요
    private Author circleIssueTest1(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("author/{id}/circle/dto")
    @ResponseBody
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될 경우
//    순환참조 이슈 발생하므로, dto 사용 필요
    private AuthorDetailResDto circleIssueTest2(@PathVariable Long id) {
        return authorService.findAuthorDetail(id);
    }

}
