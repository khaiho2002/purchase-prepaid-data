package com.nab.data.prepaid.service;

import com.nab.data.prepaid.dto.BuyVoucherRequestDTO;
import com.nab.data.prepaid.dto.BuyVoucherResponseDTO;

public interface VoucherService {

  BuyVoucherResponseDTO buyVoucherCode(BuyVoucherRequestDTO dto) throws InterruptedException;
}
