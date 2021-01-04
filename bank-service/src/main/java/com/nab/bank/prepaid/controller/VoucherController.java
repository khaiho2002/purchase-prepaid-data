package com.nab.bank.prepaid.controller;

import com.nab.bank.prepaid.constant.AppConstant;
import com.nab.bank.prepaid.dto.request.BuyVoucherRequestDTO;
import com.nab.bank.prepaid.dto.response.BuyVoucherResponseDTO;
import com.nab.bank.prepaid.service.VoucherService;
import java.util.concurrent.TimeoutException;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.API_VERSION_1 + "/vouchers")
public class VoucherController {

  final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @PostMapping
  public BuyVoucherResponseDTO buyVoucher(@RequestBody BuyVoucherRequestDTO dto) throws InterruptedException, TimeoutException, NotFoundException {
    return voucherService.buyVoucher(dto);
  }
}
