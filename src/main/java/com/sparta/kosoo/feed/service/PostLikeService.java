package com.sparta.kosoo.feed.service;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.dto.ApiResult;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CommonException;
import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.feed.entity.Post;
import com.sparta.kosoo.feed.entity.PostLike;
import com.sparta.kosoo.feed.repository.PostLikeRepository;
import com.sparta.kosoo.feed.repository.PostRepository;
import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostResponseDto postLike(MemberDetailsImpl userDetails, Long postId) {
        // 토큰 체크
        Member member = userDetails.getUser();

        if (member == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_MEMBER, null);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST, null));

        // 본인게시글이면 좋아요 불가
        if (member.getId().equals(post.getMember().getId())) {
            throw new CommonException(ErrorCode.CAN_NOT_YOURSELF, null);
        }

        // 중복 방지
        PostLike postLike = postLikeRepository.findByPost_IdAndMember_Id(postId, member.getId());
        if (postLike != null){
            throw new CommonException(ErrorCode.OVERLAP_LIKE, null);
        }

        // DB저장
        postLikeRepository.save(new PostLike(post, member));

        return new PostResponseDto(post);
    }

    @Transactional
    public ApiResult deletePostLike(MemberDetailsImpl userDetails, Long postId) {
        // 토큰 체크
        Member member = userDetails.getUser();

        if (member == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_MEMBER, null);
        }

        PostLike postLike = postLikeRepository.findByPost_IdAndMember_Id(postId, member.getId());
        if (postLike == null){
            throw new CommonException(ErrorCode.NOT_FOUND_LIKE, null);
        }


        if (this.checkValidMember(member, postLike)) {
            throw new CommonException(ErrorCode.NOT_YOUR_POST, null);
        }

        postLikeRepository.delete(postLike);

        return new ApiResult("좋아요 취소 성공", HttpStatus.OK.value());
    }

    private boolean checkValidMember(Member member, PostLike postLike) {
        boolean result = !(member.getId().equals(postLike.getMember().getId()))
                && !(member.getRole().equals(MemberRole.ADMIN));
        return result;
    }
}
