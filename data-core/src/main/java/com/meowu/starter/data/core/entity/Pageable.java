package com.meowu.starter.data.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pageable<T>{

    private List<T> content;
    private Integer pageNumber;
    private Integer total;

    public Pageable(List<T> content, Integer pageNumber, Integer total){
        this.content = content;
        this.pageNumber = pageNumber;
        this.total = total;
    }
}
