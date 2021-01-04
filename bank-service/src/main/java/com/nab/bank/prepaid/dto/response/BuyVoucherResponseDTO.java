package com.nab.bank.prepaid.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyVoucherResponseDTO {

  String mobile;
  String code;
}
