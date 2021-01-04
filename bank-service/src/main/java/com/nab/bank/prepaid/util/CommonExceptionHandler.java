package com.nab.bank.prepaid.util;

import com.nab.bank.prepaid.controller.BaseController;
import com.nab.bank.prepaid.dto.ResultObjectV2;
import java.util.concurrent.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler extends BaseController {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResultObjectV2<String> exception(Exception ex) {
    return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
  }

  @ExceptionHandler({TimeoutException.class})
  public ResultObjectV2<String> handleTimeOutException(Exception ex) {
    return failed(HttpStatus.OK.value(), ex.getMessage());
  }
}
