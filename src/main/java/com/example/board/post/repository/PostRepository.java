package com.example.board.post.repository;

import com.example.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //    List<Post> findAllByOrderByCreated_timeDesc();

//    Pageable 객체 : pageNumber(page=1), page마다 개수(size=10), 정렬(sort=created_time.desc)
    Page<Post> findAll(Pageable pageable);
    /**
     *     select p.*
     *     from post p left join author a
     *     on p.author_id = a.id;
     */
//    아래 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합하다.
    @Query("select p from Post p left join p.author") // jpql문
    List<Post> findAllJoin();

    /**
     *     select p.*, a.*
     *     from post p left join author a
     *     on p.author_id = a.id;
     */
    @Query("select p from Post p left join fetch p.author") // jpql문
    List<Post> findAllFetchJoin();
}
