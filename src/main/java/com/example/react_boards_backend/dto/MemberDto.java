package com.example.react_boards_backend.dto;


import com.example.react_boards_backend.entity.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDto {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String tel;
    private String role;
    private String token;
    public Member toEntity(){
        return Member.builder().email(this.email).id(this.id).nickname(this.nickname)
                .password(this.password).role(this.role).tel(this.tel).username(this.username).build();
    }
}
