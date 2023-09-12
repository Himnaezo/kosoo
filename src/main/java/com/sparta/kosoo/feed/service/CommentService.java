package com.sparta.kosoo.feed.service;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.kosoo.feed.dto.CommentRequestDto;
import com.sparta.kosoo.feed.dto.CommentResponseDto;
import com.sparta.kosoo.feed.dto.CommentUpdateRequestDto;
import com.sparta.kosoo.feed.entity.Comment;
import com.sparta.kosoo.feed.entity.Post;
import com.sparta.kosoo.feed.repository.CommentRepository;
import com.sparta.kosoo.feed.repository.PostRepository;
import com.sparta.kosoo.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(MemberDetailsImpl userDetails, CommentRequestDto requestDto) {
        Post post = postRepository.findById(requestDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST, null));
        Comment comment = new Comment(requestDto.getContent(), post, userDetails.getUser());
        commentRepository.save(comment);
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public CommentResponseDto updateComment(Long id, MemberDetailsImpl userDetails, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT, null));
        if (userDetails.getUser().getId().equals(comment.getMember().getId()) || userDetails.getRole().equals(MemberRole.ADMIN.toString())) {
            comment.update(requestDto);
        } else throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER, null);
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public void deleteComment(Long id, MemberDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT, null));
        if (userDetails.getUser().getId().equals(comment.getMember().getId()) || userDetails.getRole().equals(MemberRole.ADMIN.toString())) {
            commentRepository.delete(comment);
        } else throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER, null);
    }
}
