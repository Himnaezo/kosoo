package com.sparta.kosoo.follow.controller;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows/{id}")
    @ResponseBody
    public void follow(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long id) {
        followService.follow(userDetails, id);
    }

    @DeleteMapping("/follows/{id}")
    @ResponseBody
    public void unfollow(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long id) {
        followService.unfollow(userDetails, id);
    }

    @GetMapping("/following/posts")
    @ResponseBody
    public List<PostResponseDto> followingPosts(@AuthenticationPrincipal MemberDetailsImpl userDetail){
        return followService.followingPosts(userDetail.getUser());
    }
}
