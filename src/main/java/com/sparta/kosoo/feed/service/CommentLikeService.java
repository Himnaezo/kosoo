package com.sparta.kosoo.feed.service;

import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.common.result.ApiResult;
import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.kosoo.feed.dto.CommentResponseDto;
import com.sparta.kosoo.feed.entity.Comment;
import com.sparta.kosoo.feed.entity.CommentLike;
import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.feed.repository.CommentRepository;
import com.sparta.kosoo.feed.repository.CommentLikeRepository;
import com.sparta.kosoo.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto commentLike(MemberDetailsImpl userDetails, Long commentId) {
        // 토큰 체크
        Member member = userDetails.getUser();
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER, null);
        }
        // 좋아요 누른 댓글 find
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT, null)
        );
        // 본인 댓글이면 좋아요 불가
        if (member.getId().equals(comment.getMember().getId())) {
            throw new CustomException(ErrorCode.CAN_NOT_MINE, null);
        }
        // 중복 좋아요 방지
        CommentLike commentLike = commentLikeRepository.findByComment_IdAndMember_Id(comment.getId(), member.getId());
        if (commentLike != null){
            throw new CustomException(ErrorCode.OVERLAP_HEART, null);
        }
        // DB저장
        commentLikeRepository.save(new CommentLike(comment, member));
        return new CommentResponseDto(comment);
    }

    public ApiResult deleteCommentLike(MemberDetailsImpl userDetails, Long commentId) {
        // 토큰 체크
        Member member = userDetails.getUser();

        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER, null);
        }

        CommentLike commentLike = commentLikeRepository.findByComment_IdAndMember_Id(commentId, member.getId());
        if (commentLike == null){
            throw new CustomException(ErrorCode.NOT_FOUND_HEART, null);
        }

        if (this.checkValidMember(member, commentLike)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER, null);
        }

        commentLikeRepository.delete(commentLike);
        return new ApiResult("좋아요 취소 성공", HttpStatus.OK.value());
    }

    private boolean checkValidMember(Member member, CommentLike commentLike) {
        boolean result = !(member.getId().equals(commentLike.getMember().getId()))
                && !(member.getRole().equals(MemberRole.ADMIN));
        return result;
    }
}
