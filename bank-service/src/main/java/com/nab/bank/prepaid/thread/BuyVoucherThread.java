package com.nab.bank.prepaid.thread;

import com.nab.bank.prepaid.dto.request.BuyVoucherRequestDTO;
import com.nab.bank.prepaid.dto.response.BuyVoucherResponseDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.repository.TransactionRepository;
import com.nab.bank.prepaid.type.TransactionStatus;
import com.nab.bank.prepaid.util.ExternalRequestExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class BuyVoucherThread implements Runnable {

  private static final Logger logger = LogManager.getLogger(BuyVoucherThread.class);

  final ExternalRequestExecutor requestExecutor;
  final String url;
  final String username;
  final String password;
  private TransactionEntity transaction;
  private TransactionRepository repository;

  public BuyVoucherThread(ExternalRequestExecutor requestExecutor, String url, String username, String password) {
    this.requestExecutor = requestExecutor;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public BuyVoucherThread setTransaction(TransactionEntity transaction) {
    this.transaction = transaction;
    return this;
  }

  public BuyVoucherThread setRepository(TransactionRepository repository) {
    this.repository = repository;
    return this;
  }

  @Override
  public void run() {
    try {
      BuyVoucherRequestDTO requestDTO = new BuyVoucherRequestDTO(transaction.getMobile());

      long startTime = System.currentTimeMillis();
      ResponseEntity<BuyVoucherResponseDTO> responseEntity = requestExecutor
          .execute(url, HttpMethod.POST, username, password, requestDTO, BuyVoucherResponseDTO.class);

      long endTime = System.currentTimeMillis();

      long duration = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
      BuyVoucherResponseDTO response = responseEntity.getBody();

      if (response != null) {
        transaction.setCode(response.getCode());
      }

      if (duration < 30) {
        transaction.setStatus(TransactionStatus.COMPLETED);
      } else {
        transaction.setStatus(TransactionStatus.NOT_RESPONSE);
      }

      logger.info(String.format("Buy voucher for mobile %s with code %s successfully in %d seconds",
          transaction.getMobile(), transaction.getCode(), duration));
    } catch (Exception ex) {
      transaction.setStatus(TransactionStatus.FAILED);
      logger.error(String.format("Error white get voucher code for mobile %s", transaction.getMobile()), ex);
    } finally {
      repository.save(transaction);
    }
  }
}
