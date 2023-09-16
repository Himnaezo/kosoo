package com.sparta.common.controller;

import com.sparta.kosoo.feed.dto.PostResponseDto;
import com.sparta.kosoo.feed.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/signup")
    public String viewSignup(){
        return "signup";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @GetMapping("/profile")
    public ModelAndView viewProfile(){
        return new ModelAndView("profile");
    }

    @GetMapping("/profile/manage")
    public ModelAndView manageProfile(){
        return new ModelAndView("manage-profile");
    }

    @GetMapping("/follows")
    public String viewFollow(){
        return "follow";
    }

    @GetMapping("/posts")
    public String viewPost(){
        return "create-post";
    }

    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable Long postId, Model model){
        PostResponseDto responseDto = postService.readPost(postId);
        model.addAttribute("post", responseDto);
        return "detail-post";
    }

    @GetMapping("/posts/manage/{postId}")
    public String updatePost(@PathVariable Long postId, Model model){
        PostResponseDto responseDto = postService.readPost(postId);
        model.addAttribute("post", responseDto);
        return "manage-post";
    }

}
