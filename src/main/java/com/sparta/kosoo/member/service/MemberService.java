package com.sparta.kosoo.member.service;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.dto.ApiResult;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.common.util.ImageUploader;
import com.sparta.kosoo.member.dto.*;
import com.sparta.kosoo.member.entity.Member;
import com.sparta.kosoo.member.entity.MemberRole;
import com.sparta.kosoo.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final ImageUploader imageUploader;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        MemberRole role = requestDto.getRole();

        if (memberRepository.findByUsername(username).isPresent()) {
            throw new CustomException(ErrorCode.IN_USED_ID, null);
        }

        Member member = new Member(username, password, email, role); // 이메일 추가
        memberRepository.save(member);
    }


    @Transactional(readOnly = true)
    public ProfileResponseDto readProfile(MemberDetailsImpl userDetails) {
        Member member = userDetails.getUser(); // 로그인 된 유저에 맞는 정보 담기

        return new ProfileResponseDto(member.getUsername(), member.getIntroduce());
    }

    @Transactional
    public boolean checkPassword(MemberDetailsImpl userDetails, PasswordRequestDto requestDto) {
        Member member = userDetails.getUser();
        // 비밀번호 확인
        System.out.println(requestDto.getPassword());
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD, null);
        }
        return true; // 수정 페이지로 넘어가기 전 비밀번호 확인
    }

    @Transactional
    public ApiResult updateProfile(MemberDetailsImpl userDetails, ProfileRequestDto requestDto, MultipartFile image) throws IOException {
        Member member = userDetails.getUser(); // 로그인 된 유저에 맞는 정보 담기
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member targetMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_USER, null));

        if (image != null) {
            String imageUrl = imageUploader.upload(image, "image");
            System.out.println("imageUrl = " + imageUrl);
            requestDto.setImageUrl(imageUrl);
        }

        // 사용자 정보를 업데이트
        targetMember.update(requestDto);

        return new ApiResult("정보 수정 완료", HttpStatus.OK.value());
    }


    public String emailAuth(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String authCode = createCode();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("이메일 인증을 위한 인증 코드 발송"); // 메일 제목
            mimeMessageHelper.setText(authCode); // 인증 코드
            javaMailSender.send(mimeMessage);
            return authCode;
    }

    private String createCode() {    // 이메일 인증번호 생성 메서드
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow();
    }

    public List<MemberDto> readMembers() {
        return memberRepository.findAll().stream().map(MemberDto::new).toList();

    }
}
