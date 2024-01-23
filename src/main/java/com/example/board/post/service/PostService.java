package com.example.board.post.service;

import com.example.board.post.domain.Post;
import com.example.board.post.dto.request.PostingReqDto;
import com.example.board.post.dto.response.PostDetailResDto;
import com.example.board.post.dto.response.PostListResDto;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostListResDto> postList() {
        List<PostListResDto> postListResDtos = new ArrayList<>();
        List<Post> all = postRepository.findAll();
        for (Post post : all) {
            PostListResDto postListResDto1 = new PostListResDto();
            postListResDto1.setId(post.getId());
            postListResDto1.setTitle(post.getTitle());
            postListResDtos.add(postListResDto1);
        }
        return postListResDtos;
    }

    public void posting(PostingReqDto postingReqDto) {
        Post post = new Post(postingReqDto.getTitle(), postingReqDto.getContents());
        postRepository.save(post);
    }

    public Post findById(Long id) {
        Post byId = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return byId;
    }

    public PostDetailResDto postDetail(Long id) {
        Post byId = this.findById(id);
        PostDetailResDto postDetailResDto = new PostDetailResDto();
        postDetailResDto.setId(byId.getId());
        postDetailResDto.setTitle(byId.getTitle());
        postDetailResDto.setContents(byId.getContents());
        postDetailResDto.setCreated_time(byId.getCreated_time());

        return postDetailResDto;
    }

    public Long postUpdate(PostDetailResDto postDetailResDto) {
        Post byId = this.findById(postDetailResDto.getId());
        byId.Update(postDetailResDto.getTitle(), postDetailResDto.getContents());
        postRepository.save(byId);
        return byId.getId();
    }

    public void postDelete(Long id) {
        Post byId = this.findById(id);
        postRepository.delete(byId);
    }
}
