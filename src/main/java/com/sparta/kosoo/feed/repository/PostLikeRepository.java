package com.sparta.kosoo.feed.repository;

import com.sparta.kosoo.feed.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByPost_IdAndMember_Id(Long postId, Long memberId);
}
