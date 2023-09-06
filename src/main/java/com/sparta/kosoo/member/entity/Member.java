package com.sparta.kosoo.member.entity;

import com.sparta.common.vo.Image;
import com.sparta.common.vo.ImageType;
import com.sparta.kosoo.follow.entity.Follow;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_username", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_website")
    private String website;

    @Lob
    @Column(name = "member_introduce")
    private String introduce;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_phone")
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Follow> followings;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "member_image_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "member_image_type")),
            @AttributeOverride(name = "imageName", column = @Column(name = "member_image_name")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "member_image_uuid"))
    })
    private Image image;

    @Builder
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        // 자동 초기화
        this.role = MemberRole.ROLE_USER;
        this.image = Image.builder()
                .imageName("base")
                .imageType(ImageType.PNG)
                .imageUrl("https://cdn0.iconfinder.com/data/icons/narcissism/500/yul1533_11_narcissism_thumb_up-512.png")
                .imageUUID("base-UUID")
                .build();
    }
}
