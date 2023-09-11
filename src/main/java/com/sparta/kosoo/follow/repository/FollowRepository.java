package com.sparta.kosoo.follow.repository;

import com.sparta.kosoo.follow.entity.Follow;
import com.sparta.kosoo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerMemberAndFollowingMember(Member followMember, Member followingMember);
    List<Follow> findAllByFollowerMember(Member member);
}
