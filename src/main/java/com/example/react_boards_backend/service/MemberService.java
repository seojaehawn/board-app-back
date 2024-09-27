package com.example.react_boards_backend.service;

import com.example.react_boards_backend.dto.MemberDto;

import java.util.Map;

public interface MemberService {
    Map<String, String> usernameCheck(String username);

    Map<String, String> nicknameChk(String username);

    MemberDto join(MemberDto memberDto);

    MemberDto login(MemberDto memberDto);
}
