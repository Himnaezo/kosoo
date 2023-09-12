package com.sparta.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

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

//    @GetMapping("/profile")
//    public String viewProfile(){
//        return "profile";
//    }
    @GetMapping("/follows")
    public String viewFollow(){
        return "follow";
    }

    @GetMapping("/posts")
    public String viewPost(){
        return "create-post";
    }

}
