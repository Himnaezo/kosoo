package com.sparta.kosoo.follow.entity;

import com.sparta.kosoo.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Member followingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    Member followerMember;

    @Builder
    public Follow(Member followingMember, Member followerMember){
        this.followingMember = followingMember;
        this.followerMember = followerMember;
    }
}
