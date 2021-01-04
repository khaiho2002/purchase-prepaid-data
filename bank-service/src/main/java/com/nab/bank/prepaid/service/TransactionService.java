package com.nab.bank.prepaid.service;

import com.nab.bank.prepaid.dto.TransactionDTO;
import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.specs.TransactionSpecs;
import java.util.UUID;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

  TransactionDTO findById(UUID id) throws NotFoundException;

  Page<TransactionDTO> search(TransactionSpecs specs, Pageable pageable);

  TransactionEntity create(TransactionEntity transaction);
}
