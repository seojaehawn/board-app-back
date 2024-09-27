package com.example.react_boards_backend.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResponseDto<T> {
    private T item;
    private List<T> items;
    private Page<T> pageItems;
    private int StatusCode;
    private String StatusMessage;
}
