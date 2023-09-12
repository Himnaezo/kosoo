package com.sparta.kosoo.feed.service;


import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.common.util.ImageUtil;
import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.kosoo.feed.dto.PostRequestDto;
import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.feed.entity.Post;
import com.sparta.kosoo.member.entity.MemberRole;
import com.sparta.kosoo.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageUtil imageUtil;

    public PostResponseDto createPost(MemberDetailsImpl userDetails, PostRequestDto requestDto, MultipartFile image) throws IOException {
        if (image != null) {
            String imageUrl = imageUtil.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imageUrl(requestDto.getImageUrl())
                .member(userDetails.getUser())
                .build();
        return new PostResponseDto(postRepository.save(post));
    }

    public List<PostResponseDto> readPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostResponseDto readPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_POST, null)));
    }

    @Transactional
    public PostResponseDto updatePost(MemberDetailsImpl userDetails, Long id, PostRequestDto requestDto, MultipartFile image) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_POST, null));
        if (post.getMember().getId().equals(userDetails.getUser().getId()) ||
                userDetails.getRole().equals(MemberRole.ADMIN.toString())) {
            if (image != null) {
                    String imageUrl = imageUtil.upload(image, "image");
                    requestDto.setImageUrl(imageUrl);
            }
            post.update(requestDto);
            return new PostResponseDto(post);
        } else throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER, null);
    }

    @Transactional
    public void deletePost(MemberDetailsImpl userDetails, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시물은 존재하지 않습니다."));
        if (post.getMember().getId().equals(userDetails.getUser().getId()) ||
                userDetails.getRole().equals(MemberRole.ADMIN.toString())) {
            postRepository.delete(post);
        } else throw new IllegalArgumentException("삭제 권한이 없습니다.");
    }
}
