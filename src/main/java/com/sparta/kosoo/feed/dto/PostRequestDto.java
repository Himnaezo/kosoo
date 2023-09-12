package com.sparta.kosoo.feed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private String imageUrl;

//    @Builder
//    public FeedRequestDto(String title, String content){
//        this.title = title;
//        this.content = content;
//    }

}
