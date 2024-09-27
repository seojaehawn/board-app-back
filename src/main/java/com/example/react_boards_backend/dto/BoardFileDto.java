package com.example.react_boards_backend.dto;


import com.example.react_boards_backend.entity.Board;
import com.example.react_boards_backend.entity.BoardFile;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardFileDto  {
    private Long id;
    private String filename;
    private String filepath;
    private String fileoriginname;
    private String filetype;

    private String filestatus;
    private String newfilename;
    private Long board_id;
    public BoardFile toEntity(Board board) {
        return BoardFile.builder().board(board).filetype(this.filetype)
                .filename(this.filename).fileoriginname(this.fileoriginname).filestatus(this.filestatus)
                .newfilename(this.newfilename).filepath(this.filepath).id(this.id).build();
    }
}