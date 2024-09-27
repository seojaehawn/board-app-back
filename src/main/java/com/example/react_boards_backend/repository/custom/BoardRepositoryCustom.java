package com.example.react_boards_backend.repository.custom;

import com.example.react_boards_backend.dto.BoardDto;
import com.example.react_boards_backend.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable);
}
