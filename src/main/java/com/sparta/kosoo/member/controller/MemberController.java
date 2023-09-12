package com.sparta.kosoo.member.controller;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.dto.ApiResult;
import com.sparta.kosoo.member.dto.*;
import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.member.entity.MemberRole;
import com.sparta.kosoo.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class MemberController {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size()> 0 ){
            for (FieldError fieldError : fieldErrors){
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 저장 실패");
        }

        memberService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    @ResponseBody
    @PostMapping("/email-auth")
    public String emailAuth(@RequestBody EmailAuthRequestDto requestDto) throws MessagingException {

        return  memberService.emailAuth(requestDto.getEmail());
    }

    @GetMapping("/profile/manage")
    @ResponseBody
    public MemberDto readMember(@AuthenticationPrincipal MemberDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        Long id = member.getId();
        String username = member.getUsername();
        String introduce = member.getIntroduce();
        MemberRole role = member.getRole();
        String imageUrl = member.getImageUrl();
        boolean isAdmin = (role == MemberRole.ADMIN);
        return new MemberDto(id, username, introduce, isAdmin, imageUrl);
    }

    @GetMapping("/members")
    @ResponseBody
    public List<MemberDto> readMembers(){
        return memberService.readMembers();
    }

    @GetMapping("/profile")
    @ResponseBody
    public ProfileResponseDto readProfile(@AuthenticationPrincipal MemberDetailsImpl userDetails) {
        return memberService.readProfile(userDetails);
    }

    @PutMapping(value = "/profile/manage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResult updateProfile(@AuthenticationPrincipal MemberDetailsImpl userDetails,
                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                   @RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "introduce") String introduce) throws IOException {
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        requestDto.setIntroduce(introduce);

        return memberService.updateProfile(userDetails, requestDto, image);
    }

    @PostMapping("/profile")
    @ResponseBody
    public PasswordResponseDto checkPassword(@AuthenticationPrincipal MemberDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto) {
        return new PasswordResponseDto(memberService.checkPassword(userDetails, requestDto));
    }

}
