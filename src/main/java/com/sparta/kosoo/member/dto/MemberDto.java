package com.sparta.kosoo.member.dto;

import com.sparta.kosoo.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    Long id;
    String username;
    String introduce;
    boolean isAdmin;
    String imageUrl;

    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.introduce = member.getIntroduce();
        this.isAdmin = false;
        this.imageUrl = member.getImageUrl();
    }
}