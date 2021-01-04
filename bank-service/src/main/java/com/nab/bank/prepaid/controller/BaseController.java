package com.nab.bank.prepaid.controller;

import com.nab.bank.prepaid.dto.ResultObjectV2;
import com.nab.bank.prepaid.dto.ResultObjectV2.Meta;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

public class BaseController {

  protected <T> ResultObjectV2<List<T>> successList(Page<T> data) {
    Meta metaData = new Meta();
    metaData.setTotalPages(data.getTotalPages());
    metaData.setTotalRows(data.getTotalElements());
    metaData.setPageSize(data.getSize());
    metaData.setCurrentPage(data.getNumber() + 1);
    ResultObjectV2<List<T>> result = new ResultObjectV2();
    result.setStatus(HttpStatus.OK.value());
    result.setMessage("Success");
    result.setData(data.getContent());
    result.setMeta(metaData);
    return result;
  }

  protected <T> ResultObjectV2<T> failed(int status, String description) {
    return initResponseFailed(status, description, null);
  }

  protected <T> ResultObjectV2<T> failed(int status, String description, T data) {
    return initResponseFailed(status, description, data);
  }

  private <T> ResultObjectV2<T> initResponseFailed(int status, String description, T data) {
    ResultObjectV2<T> result = new ResultObjectV2<>();
    if (data != null) {
      result.setData(data);
    }
    result.setStatus(status);
    result.setMessage(description);
    return result;
  }

  protected Pageable getPageable(int page, int size, Sort sort) {
    if (page > 0) {
      page = page - 1;
    }

    if (size == 0 || size == -1) {
      size = Integer.MAX_VALUE;
    }

    return PageRequest.of(page, size, sort);
  }
}
