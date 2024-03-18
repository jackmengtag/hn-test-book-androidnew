package com.yangna.lbdsp.home.bean;

import java.util.ArrayList;
import java.util.List;

public class ResultPageContent<T> {

   private List<T> content=new ArrayList<>();

    private ResultPageParam pageable=new ResultPageParam();

    private Integer totalElements;

    private Integer totalPages;

    private Boolean last;

    private Integer number;

    private Integer size;

    private SortParam sort;

    private Integer numberOfElements;

    private Boolean first;

    private Boolean empty;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public ResultPageParam getPageable() {
        return pageable;
    }

    public void setPageable(ResultPageParam pageable) {
        this.pageable = pageable;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public SortParam getSort() {
        return sort;
    }

    public void setSort(SortParam sort) {
        this.sort = sort;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}
