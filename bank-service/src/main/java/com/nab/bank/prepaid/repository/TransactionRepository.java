package com.nab.bank.prepaid.repository;

import com.nab.bank.prepaid.entity.TransactionEntity;
import com.nab.bank.prepaid.type.TransactionStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID>, JpaSpecificationExecutor<TransactionEntity> {

  List<TransactionEntity> findAllByStatus(TransactionStatus status);
}
