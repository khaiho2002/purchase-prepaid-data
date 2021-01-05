package com.nab.bank.prepaid.controller;

import com.nab.bank.prepaid.constant.AppConstant;
import com.nab.bank.prepaid.dto.ResultObjectV2;
import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.service.TransactionService;
import com.nab.bank.prepaid.specs.TransactionSpecs;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.API_VERSION_1 + "/transactions")
public class TransactionController extends BaseController {

  final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @ApiOperation(value = "searchTransaction", notes = "Search transaction of user", response = ResultObjectV2.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "mobile", dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "purchaseDate,desc", dataType = "java.lang.String", paramType = "query",
          defaultValue = "purchaseDate,desc")
  })
  @GetMapping()
  public @ResponseBody
  ResultObjectV2<List<TransactionDTO>> getTransactions(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size,
      TransactionSpecs specs, Sort sort) {
    Pageable pageable = getPageable(page, size, sort);
    Page<TransactionDTO> transactionDTOS = transactionService.search(specs, pageable);

    return successList(transactionDTOS);
  }
}
