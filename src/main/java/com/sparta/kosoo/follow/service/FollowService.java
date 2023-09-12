package com.sparta.kosoo.follow.service;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.common.util.JwtUtil;
import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.feed.entity.Post;
import com.sparta.kosoo.feed.repository.PostRepository;
import com.sparta.kosoo.follow.entity.Follow;
import com.sparta.kosoo.follow.repository.FollowRepository;
import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    @Transactional
    public void follow(@AuthenticationPrincipal MemberDetailsImpl userDetails, Long id) {
        Member followerMember = userDetails.getUser();
        if (followerMember == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER, null);
        }

        Member followingMember = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER, null));

        if (followerMember.getId().equals(followingMember.getId())) {
            throw new CustomException(ErrorCode.CAN_NOT_MINE, null);
        }

        if (followRepository.findByFollowerMemberAndFollowingMember(followerMember, followingMember).isPresent()) {
            throw new CustomException(ErrorCode.FOLLOW_AGAIN, null);
        }

        followRepository.save(new Follow(followingMember, followerMember));
    }

    @Transactional
    public void unfollow(@AuthenticationPrincipal MemberDetailsImpl userDetails, Long id) {
        Member followerMember = userDetails.getUser();
        if (followerMember == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER, null);
        }

        Member followingMember = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER, null));

        Follow follow = followRepository.findByFollowerMemberAndFollowingMember(followerMember, followingMember)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_FOLLOW, null));

        followRepository.delete(follow);
    }

    public List<PostResponseDto> followingPosts(Member member) {
        List<PostResponseDto> feedList = new ArrayList<>();
        List<Follow> follows = followRepository.findAllByFollowerMember(member);
        List<Member> memberList = new ArrayList<>();
        for (Follow follow : follows) {
            memberList.add(follow.getFollowingMember());
        }
        for (Member foundMember : memberList){
            List<Post> foundPostList =  postRepository.findAllByMember(foundMember);
            feedList.addAll(foundPostList.stream().map(PostResponseDto::new).toList());
        }
        return feedList;
    }
}