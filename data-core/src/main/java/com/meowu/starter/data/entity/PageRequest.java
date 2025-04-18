package com.meowu.starter.data.entity;

import com.meowu.starter.data.commons.constants.PageConstants;

public abstract class PageRequest{

    private Integer pageNumber;
    private Integer pageSize;

    public PageRequest(){
        this.pageNumber = PageConstants.PAGE_NUMBER;
        this.pageSize   = PageConstants.PAGE_SIZE;
    }

    public PageRequest(Integer pageNumber, Integer pageSize){
        this.pageNumber = pageNumber;
        this.pageSize   = pageSize;
    }

    public Integer getPageNumber(){
        return (this.pageNumber == null || this.pageNumber < 1) ? PageConstants.PAGE_NUMBER : this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber){
        this.pageNumber = (pageNumber == null || pageNumber < 1) ? PageConstants.PAGE_NUMBER : pageNumber;
    }

    public Integer getPageSize(){
        return (this.pageSize == null || this.pageSize < 0) ? PageConstants.PAGE_SIZE : this.pageSize;
    }

    public void setPageSize(Integer pageSize){
        this.pageSize = (pageSize == null || pageSize < 0) ? PageConstants.PAGE_SIZE : pageSize;
    }

    abstract Integer getOffset();
}
