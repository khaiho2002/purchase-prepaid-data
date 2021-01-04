package com.nab.bank.prepaid.service.impl;

import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.dto.request.BuyVoucherRequestDTO;
import com.nab.bank.prepaid.dto.response.BuyVoucherResponseDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.service.TransactionService;
import com.nab.bank.prepaid.service.VoucherService;
import com.nab.bank.prepaid.type.TransactionStatus;
import java.util.concurrent.TimeoutException;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

  TransactionService transactionService;

  public VoucherServiceImpl(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Override
  public BuyVoucherResponseDTO buyVoucher(BuyVoucherRequestDTO dto) throws NotFoundException, InterruptedException, TimeoutException {
    TransactionEntity transaction = new TransactionEntity();
    transaction.setMobile(dto.getMobile());
    transaction.setCode("INIT");
    transaction.setStatus(TransactionStatus.INIT);

    transaction = transactionService.create(transaction);

    TransactionDTO transactionDTO = null;
    for (int i = 0; i < 30; i++) {
      transactionDTO = transactionService.findById(transaction.getId());
      if (!TransactionStatus.COMPLETED.equals(transactionDTO.getStatus())) {
        Thread.sleep(1000);
      } else {
        System.out.println("Wait in " + (i + 1) + " seconds");
        break;
      }
    }

    if (transactionDTO != null && TransactionStatus.COMPLETED.equals(transactionDTO.getStatus())) {
      BuyVoucherResponseDTO responseDTO = new BuyVoucherResponseDTO();
      responseDTO.setMobile(dto.getMobile());
      responseDTO.setCode(transactionDTO.getCode());

      return responseDTO;
    }

    throw new TimeoutException("Process timeout. You can receive it via SMS later");
  }


}
