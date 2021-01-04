package com.nab.bank.prepaid.dto;

import com.nab.bank.prepaid.type.TransactionStatus;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
  UUID id;

  String mobile;

  String code;

  TransactionStatus status;

  Timestamp purchaseDate;
}
