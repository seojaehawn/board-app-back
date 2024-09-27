package com.example.react_boards_backend.controller;


import com.example.react_boards_backend.dto.BoardDto;
import com.example.react_boards_backend.dto.ResponseDto;
import com.example.react_boards_backend.entity.Board;
import com.example.react_boards_backend.entity.CustomUserDetails;
import com.example.react_boards_backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    /**
     *  JSON형태로 보낼 떄 @RequsetBody를 사용하여 데이터를 받음
     *  mutipartfile이 추가되서 전송되면 @RequestBody가 아닌 @RequestPart로 받아야 함
     */
    public ResponseEntity<?> post(@RequestPart("boardDto") BoardDto boardDto,
                                  @RequestPart(value = "uploadFiles",required = false)MultipartFile[] uploadFiles,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @PageableDefault(page = 0,size = 10) Pageable pageable
    ) {
        ResponseDto<BoardDto> responseDto=new ResponseDto<>();
        try {
            log.info("post board dto: {}",boardDto);
            Page<BoardDto> boardDtoList=boardService.post(boardDto,uploadFiles,customUserDetails.getMember(),pageable);
            log.info("post board dto list: {}",boardDtoList);
            responseDto.setPageItems(boardDtoList);
            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("CREATED");
            return ResponseEntity.created(new URI("/boards")).body(responseDto);
        }
        catch (Exception e) {
            log.error("post error: {}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBoards(@RequestParam("searchCondition") String searchCondition, @RequestParam("searchKeyword") String searchKeyword,
                                       @PageableDefault(page = 0,size = 10) Pageable pageable) {
        ResponseDto<BoardDto> responseDto=new ResponseDto<>();
        try {
            Page<BoardDto> boardDtoList=boardService.findAll(searchCondition,searchKeyword,pageable);
            responseDto.setPageItems(boardDtoList);
            responseDto.setItem(BoardDto.builder().searchCondition(searchCondition).searchKeyword(searchKeyword).build());
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            log.error("post error: {}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardDetail(@PathVariable("id")Long id) {
        ResponseDto<BoardDto> responseDto=new ResponseDto<>();
        try {
            BoardDto boardDto=boardService.findById(id);
            responseDto.setItem(boardDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            log.error("post error: {}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id,@AuthenticationPrincipal CustomUserDetails customUserDetails
    ,@PageableDefault(page = 0,size = 10) Pageable pageable) {
        ResponseDto<BoardDto> responseDto=new ResponseDto<>();
        try {
            Page<BoardDto> boardDto=boardService.deleteById(id,customUserDetails.getMember(),pageable);
            responseDto.setPageItems(boardDto);
            responseDto.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDto.setStatusMessage("no content");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            log.error("delete error: {}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PatchMapping
    public ResponseEntity<?> modifyBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestPart("boardDto") BoardDto boardDto,
                                         @RequestPart(value = "uploadFiles",required = false)MultipartFile[] uploadFiles,
                                         @RequestPart(value = "changeFiles",required = false)MultipartFile[] changeFiles,
                                         @RequestPart(value = "originFiles",required = false)String originFiles) {
        ResponseDto<BoardDto> responseDto=new ResponseDto<>();
        try {
            if (uploadFiles!=null && uploadFiles.length>0) {
                Arrays.stream(uploadFiles).forEach(file->{
                    log.info("modify uploadFiles: {}",file);
                });
            }
            if (changeFiles!=null && changeFiles.length>0) {
                Arrays.stream(changeFiles).forEach(file->{
                    log.info("modify changeFiles: {}",file);
                });
            }
            log.info("modify originfiles: {}",originFiles);
            BoardDto modifiedBoardDto=boardService.modify(boardDto,uploadFiles,changeFiles,originFiles,customUserDetails.getMember());
            responseDto.setItem(modifiedBoardDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            return ResponseEntity.ok(responseDto);
        }
        catch (Exception e) {
            log.error("modify error: {}",e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }
}
