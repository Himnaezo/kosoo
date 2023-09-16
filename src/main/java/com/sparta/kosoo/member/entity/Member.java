package com.sparta.kosoo.member.entity;

import com.sparta.kosoo.member.dto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_username", unique = true, nullable = false)
    private String username;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_imageUrl")
    private String imageUrl;

    @Column(name = "member_introduce")
    private String introduce;

    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Builder
    public Member(String username, String password, String imageUrl, String email, MemberRole role) {
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
        this.email = email;
        this.role = role;
    }

    @Builder
    public Member(String username, String password, String email, MemberRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void update(ProfileRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.imageUrl = requestDto.getImageUrl();
        this.introduce = requestDto.getIntroduce();
    }
}
