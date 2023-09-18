package com.sparta.kosoo.member.dto;

import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String introduce;
    private boolean isAdmin;
    private String imageUrl;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.introduce = member.getIntroduce();
        this.isAdmin = member.getRole() == MemberRole.ADMIN;
        this.imageUrl = member.getImageUrl();
    }

}
