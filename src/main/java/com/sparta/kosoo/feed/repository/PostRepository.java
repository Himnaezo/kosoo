package com.sparta.kosoo.feed.repository;

import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.feed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByMember(Member member);

}
