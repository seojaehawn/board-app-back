package com.example.react_boards_backend.entity;

import com.example.react_boards_backend.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "memberSeqGenerator",sequenceName = "MEMBER_SEQ",initialValue = 1,allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "memberSeqGenerator")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String nickname;
    private String email;
    private String tel;
    private String role;

    public MemberDto toDto(){
        return MemberDto.builder().email(this.email)
                .id(this.id).nickname(this.nickname).password(this.password)
                .role(this.role).tel(this.tel).username(this.username).build();
    }
}
