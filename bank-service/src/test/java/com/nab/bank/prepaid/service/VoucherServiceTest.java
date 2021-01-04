package com.nab.bank.prepaid.service;

import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.dto.request.BuyVoucherRequestDTO;
import com.nab.bank.prepaid.dto.response.BuyVoucherResponseDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.service.impl.VoucherServiceImpl;
import com.nab.bank.prepaid.type.TransactionStatus;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VoucherServiceTest {

  @TestConfiguration
  public static class TransactionServiceTestConfiguration {

    @MockBean
    TransactionService transactionService;

    /*
    Tạo ra trong Context một Bean TodoService
     */
    @Bean
    VoucherService voucherService() {
      return new VoucherServiceImpl(transactionService) {
      };
    }
  }

  @Autowired
  TransactionService transactionService;

  @Autowired
  VoucherService voucherService;

  final UUID transactionID = UUID.randomUUID();
  final String transactionCode = "TEST_TRAN_CODE";
  final String mobile = "0977849283";
  TransactionEntity transaction;

  @Before
  public void setUp() throws NotFoundException {
    transaction = new TransactionEntity();
    transaction.setId(transactionID);
    transaction.setCode(transactionCode);
    transaction.setMobile(mobile);
    transaction.setStatus(TransactionStatus.INIT);

    Mockito.when(transactionService.create(Mockito.any())).thenReturn(transaction);

  }

  @Test
  public void testBuyVoucher() throws InterruptedException, TimeoutException, NotFoundException {
    // mock data
    ModelMapper modelMapper = new ModelMapper();
    TransactionDTO transactionDTO1 = modelMapper.map(transaction, TransactionDTO.class);
    transactionDTO1.setStatus(TransactionStatus.INIT);

    TransactionDTO transactionDTO2 = modelMapper.map(transaction, TransactionDTO.class);
    transactionDTO2.setStatus(TransactionStatus.PROCESSING);

    TransactionDTO transactionDTO3 = modelMapper.map(transaction, TransactionDTO.class);
    transactionDTO2.setStatus(TransactionStatus.COMPLETED);

    Mockito.when(transactionService.findById(transactionID)).thenReturn(transactionDTO1, transactionDTO2, transactionDTO3);

    BuyVoucherRequestDTO requestDTO = new BuyVoucherRequestDTO(mobile);

    // test service
    BuyVoucherResponseDTO responseDTO = voucherService.buyVoucher(requestDTO);

    // assert result
    Assert.assertEquals(transactionCode, responseDTO.getCode());
    Assert.assertEquals(mobile, responseDTO.getMobile());
  }

  @Test(expected = TimeoutException.class)
  public void testBuyVoucherTimeOut() throws NotFoundException, TimeoutException, InterruptedException {
    // mock data
    ModelMapper modelMapper = new ModelMapper();
    TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

    Mockito.when(transactionService.findById(transactionID)).thenReturn(transactionDTO);

    BuyVoucherRequestDTO requestDTO = new BuyVoucherRequestDTO(mobile);

    // test service
    BuyVoucherResponseDTO responseDTO = voucherService.buyVoucher(requestDTO);
  }
}
