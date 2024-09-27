package com.example.react_boards_backend.repository;

import com.example.react_boards_backend.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    void deleteByBoardId(Long id);
}
