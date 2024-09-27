package com.example.react_boards_backend.service.impl;

import com.example.react_boards_backend.entity.CustomUserDetails;
import com.example.react_boards_backend.entity.Member;
import com.example.react_boards_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberRepository.findByUsername(username).orElseThrow(()->new RuntimeException("member not exist"));
        return CustomUserDetails.builder().member(member).build();
    }
}
