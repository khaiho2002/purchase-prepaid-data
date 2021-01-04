package com.nab.bank.prepaid.service;

import com.nab.bank.prepaid.dto.request.BuyVoucherRequestDTO;
import com.nab.bank.prepaid.dto.response.BuyVoucherResponseDTO;
import java.util.concurrent.TimeoutException;
import javassist.NotFoundException;

public interface VoucherService  {
  BuyVoucherResponseDTO buyVoucher(BuyVoucherRequestDTO dto) throws NotFoundException, InterruptedException, TimeoutException;
}
