package com.nab.data.prepaid.controller;

import com.nab.data.prepaid.constant.AppConstant;
import com.nab.data.prepaid.dto.BuyVoucherRequestDTO;
import com.nab.data.prepaid.dto.BuyVoucherResponseDTO;
import com.nab.data.prepaid.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public String hello() {
    return "Hello world";
  }

  @PostMapping
  public BuyVoucherResponseDTO buyVoucher(@RequestBody BuyVoucherRequestDTO dto) throws InterruptedException {
      return voucherService.buyVoucherCode(dto);
  }
}
