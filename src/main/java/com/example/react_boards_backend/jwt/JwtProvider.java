package com.example.react_boards_backend.jwt;


import com.example.react_boards_backend.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//jwt를 발행하고 받아온 jwt가 유효한지 검사하는 클래스
@Component
public class JwtProvider{
    //jwt의 signature부분이 될 서명 키 선언
    //bitcampdevops12todobootapp502reactspringboot를 base64인코딩 한 것
    private static final String SECRET_KEY="Yml0Y2FtcGRldm9wczEydG9kb2Jvb3RhcHA1MDJyZWFjdHNwcmluZ2Jvb3Q=";

    //SECRET_KEY를 KEY로 변환
    SecretKey key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    //사용자 정보를 받아서 jwt를 발행하는 메소드
    public String createJwt(Member member){
        //토큰 만료일 설정
        Date expireDate=Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        //jwt생성하여 리턴
        return Jwts.builder()
                //jwt헤더의 알고리즘과 signature지정
                .signWith(key, SignatureAlgorithm.HS256)
                //payload부분
                //sub(subject) 토큰 주인
                .subject(member.getUsername())
                //iss(issuer) 발행 주체
                .issuer("todo app backend")
                //isa issued at  토큰 발행 일자'
                .issuedAt(new Date())
                //토큰 만료일
                .setExpiration(expireDate)
        .compact();
    }

    //받아온 jwt토큰의 유효성 검사하고  유효한 jwt인 경우 토큰의 주인(subject(username))를 리턴
    public String validateAndGetSubject(String token){
        //토큰에 있는 signature하고 내가 가진 signature하고 비교하고 payload부분만 꺼냄
        Claims claims=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}
