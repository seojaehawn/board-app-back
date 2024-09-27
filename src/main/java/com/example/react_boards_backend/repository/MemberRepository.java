package com.example.react_boards_backend.repository;

import com.example.react_boards_backend.dto.MemberDto;
import com.example.react_boards_backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    long countByUsername(String nickname);

    long countByNickname(String nickname);

    Optional<Member> findByUsernameAndPassword(String username, String password);
}
