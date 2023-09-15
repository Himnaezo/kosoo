# Himnaezo 💪
고수인척 즐겁게 살고싶은 커뮤니티 웹 서비스입니다.\
[힘내조 Notion 바로가기](https://www.notion.so/HimNaeZo-6fb4564cce6445fdb065b04a16ba535a)

<!-- TABLE OF CONTENTS -->
<details id="top">
  <summary>Index</summary>
  <ol>
    <li>
      <a href="#실행">실행</a>
      <a href="#built-with">Built with</a>
    </li>
    <li>
      <a href="#기획">기획</a>
      <ul>
        <li><a href="#api-명세">Api 명세</a></li>
        <li><a href="#erd">ERD</a></li>
        <li><a href="#erd">ER-Diagram</a></li>
        <li><a href="#package-구조">Package 구조</a></li>
      </ul>
    </li>
    <li><a href="#contributors">Contributors</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

## Built with

<b>OS</b>

<a href="https://www.microsoft.com/ko-kr/software-download/windows10%5C"><img src ="https://img.shields.io/badge/windows_10-0078D6?&style=flat&logo=windows&logoColor=white"></a>
<a href="https://support.apple.com/ko_KR/downloads/macos"><img src ="https://img.shields.io/badge/macOS_Ventura-d9d9d9?&style=flat&logo=apple&logoColor=white"></a>

<b>Tools</b>

<a href="https://www.jetbrains.com/ko-kr/idea/download/?section=windows"><img src="https://img.shields.io/badge/IDEA 2023.1.2-000000?style=flat&logo=intellijidea&logoColor=white"></a>
<a href="https://docs.gradle.org/current/userguide/what_is_gradle.html"><img src="https://img.shields.io/badge/gradle-02303A?style=flat&logo=gradle&logoColor=white"></a>
<br/>
<a href="https://git-scm.com/"><img src="https://img.shields.io/badge/git-F05032?style=flat&logo=git&logoColor=white">
<a href="https://github.com/"><img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white"></a>

<b>Backend</b>

<a href="https://adoptium.net/temurin/releases/"><img src ="https://img.shields.io/badge/Java_17-007396?&style=flat&logo=java&logoColor=white"></a>
<br/>
<a href="https://spring.io/projects/spring-boot"><img src ="https://img.shields.io/badge/SpringBoot-6DB33F?&style=flat&logo=SpringBoot&logoColor=white"></a>
<a href="https://spring.io/projects/spring-data-jpa"><img src ="https://img.shields.io/badge/SpringDataJPA-6DB33F?&style=flat&logo=Spring&logoColor=white"></a>
<a href="https://spring.io/projects/spring-security"><img src="https://img.shields.io/badge/springsecurity-6DB33F? style=flat&logo=springsecurity&logoColor=white"></a>
<br/>
<a href="https://spring.io/guides/gs/validating-form-input/"><img src="https://img.shields.io/badge/validation-6DB33F?style=flat&logo=Spring&logoColor=white"></a>
<a href="https://projectlombok.org/setup/gradle"><img src="https://img.shields.io/badge/lombok-ad0707?style=flat&logo=lombok&logoColor=white"></a>
<!-- <img src="https://img.shields.io/badge/thymeleaf-6DB33F?style=flat&logo=thymeleaf&logoColor=white"> -->
<a href="https://jwt.io/"><img src="https://img.shields.io/badge/JsonWebTokens-000000?style=flat&logo=JsonWebTokens&logoColor=white" href="https://jwt.io/"></a>
<br/>
<a href="https://www.mysql.com/"><img src ="https://img.shields.io/badge/postgres-4479A1?&style=flat&logo=postgres&logoColor=white"></a>

<b>Frontend</b>

<a href="https://ko.wikipedia.org/wiki/HTML"><img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"></a>
<a href="https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8"><img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=javascript&logoColor=white"></a>
<a href="https://ko.wikipedia.org/wiki/CSS"><img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white"></a>
<br/>
<a href="https://getbootstrap.com/"><img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat&logo=bootstrap&logoColor=white"></a>
<a href="https://fontawesome.com/"><img src="https://img.shields.io/badge/fontawesome-528DD7?style=flat&logo=fontawesome&logoColor=white"></a>

---

## 실행
1. `application.properties` 또는 `application.yml`
   - DB 설정
   - smtp 설정
   - aws S3 설정
2. `build.gradle`
   - 코끼리 때리기
3. 실행
   - 브라우저에 서버주소:포트번호

## 기획

### 기능
1. 뉴스 피드(메인) 기능
2. 사용자 인증
   - 회원가입
   - 로그인 및 로그아웃
   - 프로필 수정
   - 이메일 인증
3. 게시물 CRUD 기능
4. 댓글 CRUD 기능
5. 화면 구현

## 와이어프레임
[wireFrame 바로가기](https://www.figma.com/file/DSJgHbUITp4Bl4C4SsfGKx?type=design)
<!--
<img src="res/login.PNG" width="400" align="top" float="left" />
<img src="res/signup.PNG" width="400" align="top"  float="right"/>
<img src="res/main.PNG" width="800" align="top"  float="right"/>
-->
## ERD
[ERD 바로가기](https://www.notion.so/HimNaeZo-6fb4564cce6445fdb065b04a16ba535a?pvs=4#d3f21826a55e4968aa4d9e77e7148ced)

## API 명세서
[API 바로가기](https://www.notion.so/HimNaeZo-6fb4564cce6445fdb065b04a16ba535a?pvs=4#b4b7d368b67b4b0486e804dab341c402)

## Package 구조

```txt
└── src
    ├── main
    │   ├── java
    │   │   └── com.sparta
    │   │       ├── common
    │   │       |   ├── config
    │   │       │   |    ├── security
    │   │       │   |    |   ├── JwtAuthenticationFilter.java
    │   │       │   |    |   ├── JwtAuthorizationFilter.java
    │   │       │   |    |   ├── MemberDetailsImpl.java
    │   │       │   |    |   └── MemberDetailsService.java
    │   │       │   |    ├── ImageConfig.java
    │   │       │   |    ├── MailConfig.java
    │   │       │   |    └── WebSecurityConfig.java
    │   │       │   ├── controller
    │   │       │   |   └── ViewController.java
    │   │       │   ├── dto
    │   │       │   |   └── ApiResult.java
    │   │       │   ├── entity
    │   │       │   |   └── TimeStaped.java
    │   │       │   ├── erorr
    │   │       │   |   ├── exception
    │   │       │   |   |   ├── CommonException.java
    │   │       │   |   |   ├── DuplicateUsernameException.java
    │   │       │   |   |   ├── PasswordMismatchException.java
    |   |       |   |   |   └── UserNotFoundException.java
    │   │       │   |   ├── ErrorCode.java
    │   │       │   |   └── GlobalExceptionHandler.java
    │   │       │   └── util
    │   │       │       ├── JwtUtil.java
    │   │       │       └── ImageUploader.java
    │   │       ├── kosoo
    │   │       │   ├── feed
    |   |       │   |   ├── controller
    │   │       │   |   |   ├── CommentController.java
    │   │       │   |   |   ├── PostController.java
    │   │       │   |   |   ├── PasswordMismatchException.java
    |   |       |   |   |   └── UserNotFoundException.java
    |   |       │   |   ├── dto
    │   │       │   |   |   ├── CommentRequestDto.java
    │   │       │   |   |   ├── CommentResponseDto.java
    │   │       │   |   |   ├── CommentRequestDto.java
    │   │       │   |   |   ├── CommentUpdateRequestDto.java
    │   │       │   |   |   ├── PostResponseDto.java
    |   |       |   |   |   └── PostRequestDto.java
    │   │       │   |   ├── entity
    │   │       │   |   |   ├── Post.java
    │   │       │   |   |   ├── Comment.java
    │   │       │   |   |   ├── PostLike.java
    |   |       |   |   |   └── CommentLike.java
    │   │       │   |   ├── repository
    │   │       │   |   |   ├── PostRepository.java
    │   │       │   |   |   ├── PostLikeRepository.java
    │   │       │   |   |   ├── CommentRepository.java
    |   |       |   |   |   └── CommentLikeRepository.java
    │   │       │   |   └── sevice
    │   │       │   |       ├── PostService.java
    │   │       │   |       ├── PostLikeService.java
    │   │       │   |       ├── CommentService.java
    |   |       |   |       └── CommentLikeService.java
    │   │       │   ├── follow
    |   |       │   |   ├── controller
    |   |       |   |   |   └── FollowController.java
    │   │       │   |   ├── entity
    |   |       |   |   |   └── Follow.java
    │   │       │   |   ├── repository
    |   |       |   |   |   └── FollowRepository.java
    │   │       │   |   └── sevice
    |   |       |   |       └── FollowService.java
    │   │       │   └── member
    |   |       │       ├── controller
    |   |       |       |   └── MemberController.java
    |   |       │       ├── dto
    │   │       │       |   ├── EmailAuthRequestDto.java
    │   │       │       |   ├── LoginRequestDto.java
    │   │       │       |   ├── LoginResponseDto.java
    │   │       │       |   ├── MemberDto.java
    │   │       │       |   ├── PasswordRequestDto.java
    │   │       │       |   ├── PasswordResponseDto.java
    │   │       │       |   ├── ProfileRequestDto.java
    │   │       │       |   ├── ProfileResponseDto.java
    │   │       │       |   ├── SignupRequestDto.java
    |   |       |       |   └── SignupResponseDto.java
    │   │       │       ├── entity
    │   │       │       |   ├── Member.java
    |   |       |       |   └── MemberRole.java
    │   │       │       ├── repository
    |   |       |       |   └── MemberRepository.java
    │   │       │       └── sevice
    |   |       |           └── MemberService.java
    │   │       └── WishApplication.java
    │   └── resources
    │       ├── static
    │       │   ├── css
    │       │   │   └── style.css
    │       │   └── js
    │       │       └── script.js
    │       └── templates
    │           ├── fragment
    │           |   ├── head.html
    │           |   └── nav.html
    │           ├── create-post.html
    │           ├── detail-post.html
    │           ├── follow.html
    │           ├── index.html
    │           ├── login.html
    │           ├── manage-post.html
    │           ├── manage-profile.html
    │           ├── profile.html
    │           └── signup.html
    └── test
        └── java
            └── com.sparta.wish
                   └── JpaSaveTest.java

```

## Contributors
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/ohoh2302">
        <img src="https://avatars.githubusercontent.com/u/133727074?v=4" width="110px;" alt=""/><br />
        <sub><b>Oh Soo-jung</b></sub></a><br />
        <a href="https://github.com/ohoh2302" title="Code">💻</a>
    </td>
    <td align="center">
      <a href="https://github.com/verdureKo">
        <img src="https://avatars.githubusercontent.com/u/133493520?v=4" width="110px;" alt=""/><br />
        <sub><b>Pu-reum Ko</b></sub></a><br />
        <a href="https://github.com/verdureKo">💻</a>
    </td>
  </tr>
</table>  

## Contributing
**Contributions** 은 오픈 소스 커뮤니티를 배우고, 영감을 주고, 창조할 수 있는 놀라운 장소로 만드는 것입니다.

프로젝트를 개선하기 위한 제안이 있는 경우 저장소를 포크하고 풀 요청을 해주세요! "enhancement" 태그를 사용하여 이슈를 열 수도 있습니다.

1. Fork the Project
2. Create your Feature Branch
3. Commit your Changes
4. Push to the Branch
5. Open a Pull Request

## Acknowledgments
이 공간에 유용하다고 생각하는 리소스를 나열해주세요.
우리는 미니 프로젝트를 시작하기 위해 유용한 몇 가지를 포함했습니다!

- [토스트 UI 코딩 컨벤션](https://ui.toast.com/fe-guide/ko_CODING-CONVENTION)
- [MySQL 예약어](https://zetawiki.com/wiki/MySQL_%EC%98%88%EC%95%BD%EC%96%B4)
- [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/)
- [Image Shields](https://shields.io)
- [Simple Icon](https://simpleicons.org/)
- [Font Awesome](https://fontawesome.com)
- [Boot Strap](https://getbootstrap.com/)

<p align="right">(<a href="#top">back to top</a>)</p>
