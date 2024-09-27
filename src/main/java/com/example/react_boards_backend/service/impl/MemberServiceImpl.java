package com.example.react_boards_backend.service.impl;

import com.example.react_boards_backend.dto.MemberDto;
import com.example.react_boards_backend.entity.Member;
import com.example.react_boards_backend.jwt.JwtProvider;
import com.example.react_boards_backend.repository.MemberRepository;
import com.example.react_boards_backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public Map<String, String> usernameCheck(String username) {
        Map<String, String> result = new HashMap<>();
        long usernameCheck=memberRepository.countByUsername(username);
        if(usernameCheck==0){
            result.put("usernameCheckMsg","available username");
        }
        else {
            result.put("usernameCheckMsg","invalid username");
        }
        return result;
    }

    @Override
    public Map<String, String> nicknameChk(String nickname) {
        Map<String, String> result = new HashMap<>();
        long nicknameCheck=memberRepository.countByNickname(nickname);
        if(nicknameCheck==0){
            result.put("nicknameCheckMsg","available username");
        }
        else {
            result.put("nicknameCheckMsg","invalid username");
        }
        return result;
    }

    @Override
    public MemberDto join(MemberDto memberDto) {
        memberDto.setRole("ROLE_USER");
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        MemberDto memberDto1=memberRepository.save(memberDto.toEntity()).toDto();
        memberDto1.setPassword("");
        return memberDto1;
    }

    @Override
    public MemberDto login(MemberDto memberDto) {
        Member member = memberRepository.findByUsername(memberDto.getUsername()).orElseThrow(
                () -> new RuntimeException("username not exist")
        );
        if (!passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("wrong password");
        }
        MemberDto memberDto1=member.toDto();
        memberDto1.setPassword("");
        memberDto1.setToken(jwtProvider.createJwt(member));
        return memberDto1;
    }
}
