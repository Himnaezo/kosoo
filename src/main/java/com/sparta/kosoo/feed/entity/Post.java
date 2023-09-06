package com.sparta.kosoo.feed.entity;

import com.sparta.common.entity.TimeStamped;
import com.sparta.kosoo.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    @Column(name = "post_content")
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    @Column(name = "post_comment_flag")
    private boolean commentFlag;

    @Column(name = "post_like_flag")
    private boolean likeFlag;

    @Builder
    public Post(Member member, String content, boolean commentFlag, boolean likeFlag) {
        this.member = member;
        this.content = content;
        this.commentFlag = commentFlag;
        this.likeFlag = likeFlag;
    }

}
