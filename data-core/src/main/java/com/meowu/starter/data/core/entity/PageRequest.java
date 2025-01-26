package com.meowu.starter.data.core.entity;

import com.meowu.starter.data.core.commons.constants.PageConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PageRequest{

    private Integer pageNumber;
    private Integer pageSize;

    public PageRequest(Integer pageNumber, Integer pageSize){
        this.pageNumber = pageNumber;
        this.pageSize   = pageSize;
    }

    public Integer getPageNumber(){
        return (this.pageNumber == null || this.pageNumber < 1) ? PageConstants.PAGE_NUMBER : this.pageSize;
    }

    public void setPageNumber(Integer pageNumber){
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize(){
        return (this.pageSize == null || this.pageSize < 0) ? PageConstants.PAGE_SIZE : this.pageSize;
    }

    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
    }

    abstract Integer getOffset();
}
