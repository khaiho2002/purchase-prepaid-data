package com.nab.bank.prepaid.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResultObjectV2<T> {

  private Integer status;
  private T data;
  private String message;
  private ResultObjectV2.Meta meta;

  public ResultObjectV2() {
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ResultObjectV2.Meta getMeta() {
    return this.meta;
  }

  public void setMeta(ResultObjectV2.Meta meta) {
    this.meta = meta;
  }

  public static class Meta {

    private Integer totalPages;
    private Integer pageSize;
    private Long totalRows;
    private Integer currentPage;

    public Meta() {
    }

    public Integer getTotalPages() {
      return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
      this.totalPages = totalPages;
    }

    public Integer getPageSize() {
      return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
      this.pageSize = pageSize;
    }

    public Long getTotalRows() {
      return this.totalRows;
    }

    public void setTotalRows(Long totalRows) {
      this.totalRows = totalRows;
    }

    public Integer getCurrentPage() {
      return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
      this.currentPage = currentPage;
    }
  }
}
