package com.nab.data.prepaid.service.impl;

import com.nab.data.prepaid.dto.BuyVoucherRequestDTO;
import com.nab.data.prepaid.dto.BuyVoucherResponseDTO;
import com.nab.data.prepaid.service.VoucherService;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

  private static int LOW = 25;
  private static int HIGH = 50;
  private static final int VOUCHER_CODE_LENGTH = 10;

  @Override
  public BuyVoucherResponseDTO buyVoucherCode(BuyVoucherRequestDTO dto) throws InterruptedException {
    Random r = new Random();
    int result = r.nextInt(HIGH - LOW) + LOW;

    System.out.println("Sleep for " + result + " seconds.");
    Thread.sleep(result * 1000);
    String voucherCode = generateVoucherCode();

    return new BuyVoucherResponseDTO(dto.getMobile(), voucherCode);
  }

  private String generateVoucherCode() {
    String voucherCode = RandomStringUtils.random(VOUCHER_CODE_LENGTH, true, true);
    return voucherCode.toUpperCase();
  }
}
