package com.example.react_boards_backend.entity;

import com.example.react_boards_backend.dto.BoardFileDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "boardFileSeqGenerator",
        sequenceName = "BOARD_FILE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Builder
public class BoardFile {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "boardFileSeqGenerator"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "board_id",referencedColumnName = "id")
    @JsonBackReference
    private Board board;

    private String filename;
    private String filepath;
    private String fileoriginname;
    private String filetype;
    @Transient
    private String filestatus;
    @Transient
    private String newfilename;
    public BoardFileDto toDto(){
        return BoardFileDto.builder().board_id(this.board.getId()).filetype(this.filetype)
                .filename(this.filename).fileoriginname(this.fileoriginname).filestatus(this.filestatus)
                .newfilename(this.newfilename).filepath(this.filepath).id(this.id).build();
    }
}






























