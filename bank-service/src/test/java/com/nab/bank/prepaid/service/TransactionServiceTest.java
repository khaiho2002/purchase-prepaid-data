package com.nab.bank.prepaid.service;

import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.repository.TransactionRepository;
import com.nab.bank.prepaid.service.impl.TransactionServiceImpl;
import com.nab.bank.prepaid.type.TransactionStatus;
import com.nab.bank.prepaid.util.ExternalRequestExecutor;
import java.util.Optional;
import java.util.UUID;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

  @TestConfiguration
  public static class TransactionServiceTestConfiguration {

    @MockBean
    ExternalRequestExecutor requestExecutor;

    @MockBean
    TransactionRepository transactionRepository;

    /*
    Tạo ra trong Context một Bean TodoService
     */
    @Bean
    TransactionService transactionService() {
      return new TransactionServiceImpl(transactionRepository, requestExecutor);
    }
  }

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  TransactionService transactionService;

  final UUID transactionID = UUID.randomUUID();
  final String transactionCode = "TEST_TRAN_CODE";
  final String mobile = "0977849283";
  TransactionEntity transaction;

  @Before
  public void setUp() {
    transaction = new TransactionEntity();
    transaction.setId(transactionID);
    transaction.setCode(transactionCode);
    transaction.setMobile(mobile);
    transaction.setStatus(TransactionStatus.INIT);
  }

  @Test
  public void testFindById() throws NotFoundException {
    Mockito.when(transactionRepository.findById(Mockito.any())).thenReturn(Optional.of(transaction));

    // test service
    TransactionDTO transactionDTO = transactionService.findById(transactionID);

    // assert result
    Assert.assertEquals(transactionID, transactionDTO.getId());
    Assert.assertEquals(transactionCode, transactionDTO.getCode());
    Assert.assertEquals(mobile, transactionDTO.getMobile());
    Assert.assertEquals(TransactionStatus.INIT, transactionDTO.getStatus());
  }

  @Test(expected = NotFoundException.class)
  public void testFindByIdNotFound() throws NotFoundException {
    Mockito.when(transactionRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

    // test service
    TransactionDTO transactionDTO = transactionService.findById(transactionID);
  }
}
