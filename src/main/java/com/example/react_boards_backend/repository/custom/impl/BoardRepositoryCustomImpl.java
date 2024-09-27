package com.example.react_boards_backend.repository.custom.impl;

import com.example.react_boards_backend.dto.BoardDto;
import com.example.react_boards_backend.entity.Board;
import com.example.react_boards_backend.repository.custom.BoardRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.react_boards_backend.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable) {
        List<Board> boardList=jpaQueryFactory.selectFrom(board).where(getSearch(searchCondition, searchKeyword))
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        long totalCount=jpaQueryFactory.selectFrom(board).where(getSearch(searchCondition,searchKeyword)).fetchCount();
        return new PageImpl<>(boardList,pageable,totalCount);
    }
    public BooleanBuilder getSearch(String searchCondition, String searchKeyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return null;
        }
        if (searchCondition.equalsIgnoreCase("all")){
            builder.or(board.title.containsIgnoreCase(searchKeyword));
            builder.or(board.content.containsIgnoreCase(searchKeyword));
            builder.or(board.member.nickname.containsIgnoreCase(searchKeyword));
        }
        else if (searchCondition.equalsIgnoreCase("title")){
            builder.or(board.title.containsIgnoreCase(searchKeyword));
        }
        else if (searchCondition.equalsIgnoreCase("content")){
            builder.or(board.content.containsIgnoreCase(searchKeyword));
        }
        else if (searchCondition.equalsIgnoreCase("member")){
            builder.or(board.member.nickname.containsIgnoreCase(searchKeyword));
        }
        return builder;
    }
}
