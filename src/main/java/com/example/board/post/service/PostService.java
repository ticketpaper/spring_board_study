package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dto.request.PostingReqDto;
import com.example.board.post.dto.response.PostDetailResDto;
import com.example.board.post.dto.response.PostListResDto;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }


    public List<PostListResDto> postList() {
        List<PostListResDto> postListResDtos = new ArrayList<>();
        List<Post> all = postRepository.findAllFetchJoin();
        for (Post post : all) {
            PostListResDto postListResDto1 = new PostListResDto();
            postListResDto1.setId(post.getId());
            postListResDto1.setTitle(post.getTitle());
            postListResDto1.setAuthor_name(post.getAuthor()==null?"ㅇㅇ" : post.getAuthor().getName());
            postListResDtos.add(postListResDto1);
        }
        return postListResDtos;
    }


    public Page<PostListResDto> postListPaging(Pageable pageable) {
        Page<Post> post = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = post.map(
                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "ㅇㅇ" : p.getAuthor().getName())
        );
        return postListResDtos;
    }

    public Page<PostListResDto> findByAppointment(Pageable pageable) {
        Page<Post> post = postRepository.findByAppointment("Y", pageable);
        Page<PostListResDto> postListResDtos = post.map(
                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "ㅇㅇ" : p.getAuthor().getName())
        );
        return postListResDtos;
    }
    public Page<PostListResDto> findByAppointmentIsNull(Pageable pageable) {
        Page<Post> post = postRepository.findByAppointmentIsNull(pageable);
        Page<PostListResDto> postListResDtos = post.map(
                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "ㅇㅇ" : p.getAuthor().getName())
        );
        return postListResDtos;
    }


//    @Transactional
    public void posting(PostingReqDto postingReqDto) throws IllegalArgumentException{
        Author author = authorRepository.findByEmail(postingReqDto.getEmail()).orElse(null);
        LocalDateTime localDateTime = null;
        String appointment = null;
        if (postingReqDto.getAppointment().equals("Y") && !(postingReqDto.getAppointmentTime().isEmpty())) {
            appointment = "Y";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(postingReqDto.getAppointmentTime(), dateTimeFormatter);
            if (localDateTime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("시간정보 잘못 입력");
            }
        }
        Post post = Post.builder()
                .title(postingReqDto.getTitle())
                .contents(postingReqDto.getContents())
                .author(author)
                .appointment(appointment)
                .appointmentTime(localDateTime)
                .build();
//        더티 체킹 테스트 save안해도 변경사항이 저장된다.
//        author.update("진짜진짜 바꿨음"," 바꾼다?");

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
