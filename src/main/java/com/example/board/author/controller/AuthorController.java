package com.example.board.author.controller;

import com.example.board.author.dto.request.AuthorSaveReqDto;
import com.example.board.author.dto.request.AuthorUpdateReqDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("authorList", authorService.findauthorDetail(id));
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
}
