package com.example.react_boards_backend.dto;

import com.example.react_boards_backend.entity.Board;
import com.example.react_boards_backend.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardDto {
    private Long id;

    private String title;
    private String content;

    private Long writer_id;
    private String nickname;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int cnt;
    private List<BoardFileDto> boardFileDtoList;

    private String searchKeyword;
    private String searchCondition;
    public Board toEntity(Member member) {
        return Board.builder().id(this.id).title(this.title).cnt(this.cnt).content(this.content)
                .moddate(this.moddate).regdate(this.regdate).searchCondition(this.searchCondition)
                .searchKeyword(this.searchKeyword).boardFileList(new ArrayList<>()).member(member).build();
    }
}
