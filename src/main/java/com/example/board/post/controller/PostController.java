package com.example.board.post.controller;

import com.example.board.post.dto.request.PostingReqDto;
import com.example.board.post.dto.response.PostDetailResDto;
import com.example.board.post.dto.response.PostListResDto;
import com.example.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/list")
    public String postList(Model model) {
        List<PostListResDto> postListResDtos = postService.postList();
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
    }

    @GetMapping("/post/posting")
    public String postingGet() {
        return "post/posting";
    }
    @PostMapping("/post/posting")
    public String posting(PostingReqDto postingReqDto) {
        postService.posting(postingReqDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        PostDetailResDto postDetailResDto = postService.postDetail(id);
        model.addAttribute("postList", postDetailResDto);
        return "post/post-detail";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(PostDetailResDto postDetailResDto) {
        Long l = postService.postUpdate(postDetailResDto);
        return "redirect:/post/detail/"+l;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.postDelete(id);
        return "redirect:/post/list";
    }
}
