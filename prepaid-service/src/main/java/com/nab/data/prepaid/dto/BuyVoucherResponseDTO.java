package com.nab.data.prepaid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BuyVoucherResponseDTO {
  String mobile;
  String code;
}
