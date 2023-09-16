package com.sparta.kosoo.member.service;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.dto.ApiResult;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.DuplicateUsernameException;
import com.sparta.common.error.exception.PasswordMismatchException;
import com.sparta.common.error.exception.UserNotFoundException;
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

    private static final int AUTH_CODE_LENGTH = 6; // 이메일 인증 코드 상수로 정의

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        validateUniqueUsername(requestDto.getUsername());

        String username = requestDto.getUsername();
        String password = encodePassword(requestDto.getPassword());
        String email = requestDto.getEmail();
        MemberRole role = requestDto.getRole();

        Member member = new Member(username, password, email, role);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto readProfile(MemberDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        return new ProfileResponseDto(member.getUsername(), member.getIntroduce());
    }

    @Transactional
    public boolean checkPassword(MemberDetailsImpl userDetails, PasswordRequestDto requestDto) {
        Member member = userDetails.getUser();
        validatePassword(requestDto.getPassword(), member.getPassword());
        return true;
    }

    @Transactional
    public ApiResult updateProfile(MemberDetailsImpl userDetails, ProfileRequestDto requestDto, MultipartFile image) throws IOException {
        Member member = userDetails.getUser();
        updateMemberProfile(member, requestDto, image);
        return new ApiResult("정보 수정 완료", HttpStatus.OK.value());
    }

    public String emailAuth(String email) throws MessagingException {
        String authCode = generateAuthCode();
        sendAuthCodeEmail(email, authCode);
        return authCode;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_MEMBER, null));
    }

    public List<MemberDto> readMembers() {
        return memberRepository.findAll().stream().map(MemberDto::new).toList();
    }

    private void validateUniqueUsername(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUsernameException(ErrorCode.IN_USED_ID, null);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new PasswordMismatchException(ErrorCode.WRONG_PASSWORD, null);
        }
    }

    private void updateMemberProfile(Member member, ProfileRequestDto requestDto, MultipartFile image) throws IOException {
        requestDto.setPassword(encodePassword(requestDto.getPassword()));

        if (image != null) {
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }

        member.update(requestDto);
    }

    private String generateAuthCode() {
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0 -> authCode.append((char) (random.nextInt(26) + 97)); // 소문자 알파벳
                case 1 -> authCode.append((char) (random.nextInt(26) + 65)); // 대문자 알파벳
                default -> authCode.append(random.nextInt(10)); // 숫자
            }
        }

        return authCode.toString();
    }

    private void sendAuthCodeEmail(String email, String authCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("이메일 인증을 위한 인증 코드 발송");
        mimeMessageHelper.setText(authCode);
        javaMailSender.send(mimeMessage);
    }
}
