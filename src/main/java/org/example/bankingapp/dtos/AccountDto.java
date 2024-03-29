package org.example.bankingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
  private Long id;
  private String name;
  private Double balance;
}
