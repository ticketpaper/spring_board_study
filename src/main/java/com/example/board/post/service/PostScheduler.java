package com.example.board.post.service;


import com.example.board.post.domain.Post;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class PostScheduler {

    private final PostRepository postRepository;
    @Autowired
    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    초 분 시 일 월 요일 형태 스케줄링 설정
//    * : 매초(분/시 등)을 의미
//    특정숫자 : 특정숫자의 초(분/시 등)을 의미
//    ex) 0 0 * * * * : 매일 0분 0초에 스케줄링 시작
//    ex) 0 0/1 * * * * : 매일 1분마다 0초에 스케줄링 시작
//    ex) 0/1 * * * * * : 매초마다
//    ex) 0 0 11 * * * : 매일 11시에 스케줄링

    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void postSchedule() {
        System.out.println("---스케줄러 시작---");
        Page<Post> post = postRepository.findByAppointment("Y", Pageable.unpaged());
        for (Post post1 : post.getContent()) {
            if (post1.getAppointmentTime().isBefore(LocalDateTime.now())) {
                post1.UpdateAppointment(null);
            }
        }
        System.out.println("---스케줄러 끝---");

    }
}
