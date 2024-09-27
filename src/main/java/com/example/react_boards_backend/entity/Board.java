package com.example.react_boards_backend.entity;

import com.example.react_boards_backend.dto.BoardDto;
import com.example.react_boards_backend.dto.BoardFileDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "boardSeqGenerator",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "boardSeqGenerator")
    private Long id;

    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "writer_id",referencedColumnName = "id")
    private Member member;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int cnt;

    @Transient
    private String searchKeyword;
    @Transient
    private String searchCondition;
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BoardFile> boardFileList;
    public BoardDto toDto(){
        return BoardDto.builder().boardFileDtoList(
                    boardFileList !=null && !boardFileList.isEmpty()
                            ? boardFileList.stream().map(BoardFile::toDto).toList()
                            :new ArrayList<>()
                ).id(this.id).title(this.title).cnt(this.cnt).content(this.content)
                .moddate(this.moddate).regdate(this.regdate).searchCondition(this.searchCondition)
                .searchKeyword(this.searchKeyword).writer_id(member.getId()).nickname(this.member.getNickname()).build();
    }
}
















