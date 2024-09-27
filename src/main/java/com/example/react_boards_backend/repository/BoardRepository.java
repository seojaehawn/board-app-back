package com.example.react_boards_backend.repository;


import com.example.react_boards_backend.entity.Board;
import com.example.react_boards_backend.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> , BoardRepositoryCustom {
}
