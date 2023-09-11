package com.sparta.kosoo.feed.repository;

import com.sparta.kosoo.feed.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    CommentLike findByComment_IdAndMember_Id(Long commentId, Long memberId);
}
