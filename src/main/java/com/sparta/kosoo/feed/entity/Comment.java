package com.sparta.kosoo.feed.entity;

import com.sparta.common.entity.TimeStamped;
import com.sparta.kosoo.feed.dto.CommentUpdateRequestDto;
import com.sparta.kosoo.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "comments")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, name = "comment_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Column(name = "comment_like_list")
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @Builder
    public Comment(String content, Post post, Member member){
        this.content = content;
        this.post = post;
        this.member = member;
    }

    public void update(CommentUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

}
