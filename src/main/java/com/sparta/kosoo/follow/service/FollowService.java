package com.sparta.kosoo.follow.service;

import com.sparta.common.config.security.MemberDetailsImpl;
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
    private final JwtUtil jwtUtil;


    @Transactional
    public void follow(@AuthenticationPrincipal MemberDetailsImpl userDetails, Long id) {
        // 토큰 체크
        Member followerMember = userDetails.getUser();

        if (followerMember == null) {
            throw new IllegalArgumentException("로그인을 해주세요");
        }

        // 팔로우 할 유저 조회
        Member followingMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        // 본인을 팔로우 할 경우 예외 발생
        if (followerMember.getId().equals(followingMember.getId())) {
            throw new IllegalArgumentException("본인을 팔로우 할 수 없습니다.");
        }

        // 중복 팔로우 예외 발생
        // followRepository 에서 두 개의 Id 값이 존재하는지 확인
        if (followRepository.findByFollowerMemberAndFollowingMember(followerMember, followingMember).isPresent()) {
            throw new IllegalArgumentException("팔로우가 중복되었습니다.");
        }

        // followRepository DB 저장
        followRepository.save(new Follow(followingMember, followerMember));


    }


    @Transactional
    public void unfollow(@AuthenticationPrincipal MemberDetailsImpl userDetails, Long id) {
        // 토큰 체크
        Member followerMember = userDetails.getUser();

        if (followerMember == null) {
            throw new IllegalArgumentException("로그인을 해주세요");
        }

        // 언팔로우 할 유저 조회
        Member followingMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Follow follow = followRepository.findByFollowerMemberAndFollowingMember(followerMember, followingMember)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 아닙니다"));

        // followRepository DB 삭제
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
