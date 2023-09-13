package com.sparta.kosoo.feed.controller;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.dto.ApiResult;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exceptionn.CommonException;
import com.sparta.kosoo.feed.dto.PostRequestDto;
import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.feed.service.PostLikeService;
import com.sparta.kosoo.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<?> createPost(@AuthenticationPrincipal MemberDetailsImpl userDetails,
                                  @RequestParam(value = "image", required = false) MultipartFile image,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam(value = "content") String content) throws IOException {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setTitle(title);
        requestDto.setContent(content);

        checkToken(userDetails);
        return ResponseEntity.ok(postService.createPost(userDetails, requestDto, image));
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<PostResponseDto> readPosts() {
        return postService.readPosts();
    }

    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable Long postId, Model model){
        PostResponseDto responseDto = postService.readPost(postId);
        model.addAttribute("post", responseDto);
        return "detail-post";
    }

    @GetMapping("/posts/manage/{postId}")
    public String updatePost(@PathVariable Long postId, Model model){
        PostResponseDto responseDto = postService.readPost(postId);
        model.addAttribute("post", responseDto);
        return "manage-post";
    }

    @PutMapping("/posts/{postId}")
    @ResponseBody
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long postId,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    @RequestParam(value = "title") String title,
                                    @RequestParam(value = "content") String content) throws IOException {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setTitle(title);
        requestDto.setContent(content);

        checkToken(userDetails);
        return ResponseEntity.ok(postService.updatePost(userDetails, postId, requestDto, image));
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseBody
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long postId) {
        checkToken(userDetails);
        postService.deletePost(userDetails, postId);
        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
    }

    @PostMapping("/posts/likes/{postId}")
    @ResponseBody // JSON 응답을 반환합니다.
    public ResponseEntity<?> postLike(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long postId) {
        try {
            PostResponseDto responseDto = postLikeService.postLike(userDetails, postId);
            return ResponseEntity.ok(responseDto);
        } catch (CommonException e) {
            // CustomException 발생 시, 적절한 에러 응답 반환
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ApiResult(e.getMessage(), e.getErrorCode().getHttpStatus()));
        }
    }

    @DeleteMapping("/posts/likes/{postId}")
    @ResponseBody // JSON 응답을 반환합니다.
    public ResponseEntity<ApiResult> deletePostLike(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long postId) {
        try {
            ApiResult result = postLikeService.deletePostLike(userDetails, postId);
            return ResponseEntity.ok(result);
        } catch (CommonException e) {
            // CustomException 발생 시, 적절한 에러 응답 반환
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ApiResult(e.getMessage(), e.getErrorCode().getHttpStatus()));
        }
    }

    private void checkToken(MemberDetailsImpl userDetails) {
        if (userDetails == null) throw new CommonException(ErrorCode.UNAUTHORIZED_MEMBER, null);
    }
}
