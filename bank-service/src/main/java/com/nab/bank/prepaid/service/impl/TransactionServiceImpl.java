package com.nab.bank.prepaid.service.impl;

import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.repository.TransactionRepository;
import com.nab.bank.prepaid.service.TransactionService;
import com.nab.bank.prepaid.specs.TransactionSpecs;
import com.nab.bank.prepaid.thread.BuyVoucherThread;
import com.nab.bank.prepaid.type.TransactionStatus;
import com.nab.bank.prepaid.util.ExternalRequestExecutor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

  ExternalRequestExecutor requestExecutor;
  TransactionRepository repository;
  ModelMapper mapper;

  @Value("${3rd.authentication.username}")
  String partyUsername;

  @Value("${3rd.authentication.password}")
  String partyPassword;

  @Value("${3rd.buy-voucher.url}")
  String urlBuyVoucher;

  public TransactionServiceImpl(TransactionRepository repository,
      ExternalRequestExecutor requestExecutor) {
    this.repository = repository;
    this.mapper = new ModelMapper();
    this.requestExecutor = requestExecutor;
  }

  @Transactional
  @Override
  public TransactionDTO findById(UUID id) throws NotFoundException {
    Optional<TransactionEntity> transaction = repository.findById(id);

    if (transaction.isPresent()) {
      return mapper.map(transaction.get(), TransactionDTO.class);
    }

    throw new NotFoundException("Can't found transaction with id " + id);
  }

  @Override
  public Page<TransactionDTO> search(TransactionSpecs specs, Pageable pageable) {
    Page<TransactionEntity> transactions = repository.findAll(specs, pageable);
    return transactions.map(item -> mapper.map(item, TransactionDTO.class));
  }

  @Override
  public TransactionEntity create(TransactionEntity transaction) {
    return repository.save(transaction);
  }

  @Scheduled(fixedDelay = 500)
  public void processGetVoucherCode() {
    List<TransactionEntity> entityList = repository.findAllByStatus(TransactionStatus.INIT);

    if (entityList != null && !entityList.isEmpty()) {
      entityList.forEach(item -> item.setStatus(TransactionStatus.PROCESSING));
      repository.saveAll(entityList);

      ExecutorService executor = Executors.newFixedThreadPool(5);

      for (TransactionEntity transaction : entityList) {
        BuyVoucherThread buyVoucherThread = new BuyVoucherThread(requestExecutor, urlBuyVoucher, partyUsername, partyPassword)
            .setTransaction(transaction)
            .setRepository(repository);
        executor.submit(buyVoucherThread);
      }

      executor.shutdown();

      try {
        executor.awaitTermination(300, TimeUnit.SECONDS);
      } catch (InterruptedException ex) {
        logger.error("Interrupted Exception occurred while get voucher code", ex);
      }
    }
  }

  @Scheduled(fixedDelay = 3000)
  public void processSendSMSForLongTimeTransaction() {
    List<TransactionEntity> entityList = repository.findAllByStatus(TransactionStatus.NOT_RESPONSE);

    if (entityList != null && !entityList.isEmpty()) {
      for (TransactionEntity transaction : entityList) {
        logger.info(String.format("Send sms to mobile %s with code %s successfully", transaction.getMobile(), transaction.getCode()));
        transaction.setStatus(TransactionStatus.SMS_OK);
      }

      repository.saveAll(entityList);
    }
  }
}
