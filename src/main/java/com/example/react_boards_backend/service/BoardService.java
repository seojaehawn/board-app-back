package com.example.react_boards_backend.service;

import com.example.react_boards_backend.dto.BoardDto;
import com.example.react_boards_backend.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    Page<BoardDto> post(BoardDto boardDto, MultipartFile[] uploadFiles, Member member, Pageable pageable);

    Page<BoardDto> findAll(String searchCondition, String searchKeyword, Pageable pageable);

    BoardDto findById(long id);

    Page<BoardDto> deleteById(Long id, Member member, Pageable pageable);

    BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles, Member member);
}
