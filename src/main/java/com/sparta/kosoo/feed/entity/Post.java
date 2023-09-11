package com.sparta.kosoo.feed.entity;

import com.sparta.common.entity.TimeStamped;
import com.sparta.kosoo.feed.dto.PostRequestDto;
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
@Table(name = "posts")
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "post_imageUrl")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")    // '@ManyToOne' association and may not use '@Column' to specify column mappings (use '@JoinColumn' instead)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Column(name = "comment_list")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Column(name = "post_like_list")
    private List<PostLike> postLikeList = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member member, String imageUrl){
        this.title = title;
        this.content = content;
        this.member = member;
        this.imageUrl = imageUrl;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = requestDto.getImageUrl();
    }
}
