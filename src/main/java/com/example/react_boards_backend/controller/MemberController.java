package com.example.react_boards_backend.controller;


import com.example.react_boards_backend.dto.MemberDto;
import com.example.react_boards_backend.dto.ResponseDto;
import com.example.react_boards_backend.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/username-check")
    public ResponseEntity<?> usernameCheck(@RequestBody MemberDto memberDto) {
        ResponseDto<Map<String,String>> responseDto=new ResponseDto<>();
        try {
            log.info("username:{}",memberDto.getUsername());
            Map<String,String> map=memberService.usernameCheck(memberDto.getUsername());
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setItem(map);
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            log.error("username check error:{}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(@RequestBody MemberDto memberDto) {
        ResponseDto<Map<String,String>> responseDto=new ResponseDto<>();
        try {
            log.info("nickname:{}",memberDto.getNickname());
            Map<String,String> map=memberService.nicknameChk(memberDto.getNickname());
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setItem(map);
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            log.error("nickname check error:{}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
        ResponseDto<MemberDto> responseDto=new ResponseDto<>();
        try {
            log.info("join member dto :{}",memberDto);
            MemberDto member=memberService.join(memberDto);
            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("CREATED");
            responseDto.setItem(member);
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            log.error("join error :{}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
        ResponseDto<MemberDto> responseDto=new ResponseDto<>();
        try {
            MemberDto member=memberService.login(memberDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setItem(member);
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseDto<Map<String,String>> responseDto=new ResponseDto<>();
        try {
            Map<String,String> logoutMsgMap=new HashMap<>();
            SecurityContext securityContext= SecurityContextHolder.getContext();
            securityContext.setAuthentication(null);
            SecurityContextHolder.setContext(securityContext);
            logoutMsgMap.put("logoutMsg","logout success");
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setItem(logoutMsgMap);
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e){
            log.error("logout error:{}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}





















