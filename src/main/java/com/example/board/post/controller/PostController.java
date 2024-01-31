package com.example.board.post.controller;

import com.example.board.post.dto.request.PostingReqDto;
import com.example.board.post.dto.response.PostDetailResDto;
import com.example.board.post.dto.response.PostListResDto;
import com.example.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/post/list")
    public String postList(Model model, @PageableDefault(size = 10, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
        Page<PostListResDto> postListResDtos = postService.findByAppointmentIsNull(pageable);
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
    }



////    localhost:8080/post/list?size=xx&page=xx&sort=xx,desc
//    @GetMapping("/json/post/list")
//    @ResponseBody
//    public Page<PostListResDto> postListJson(Pageable pageable) {
//        Page<PostListResDto> postListResDtos = postService.postListPasing(pageable);
//        return postListResDtos;
//    }

    @GetMapping("/post/posting")
    public String postingGet() {
        return "post/posting";
    }

    @PostMapping("/post/posting")
    public String posting(Model model, PostingReqDto postingReqDto, HttpSession httpSession) {
        try {
//            HttpServletRequest req를 매개변수에 주입한 뒤에
//            HttpSession session = req.getSession(); 세션값을 꺼내어 getAttribute("email")

            postService.posting(postingReqDto, httpSession.getAttribute("email").toString());
            return "redirect:/post/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "/post/posting";
        }

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
