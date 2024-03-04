package org.example.bankingapp.mappers;

import org.example.bankingapp.dtos.AccountDto;
import org.example.bankingapp.models.Account;

public class AccountMapper {

  public static Account mapToAccount(AccountDto accountDto) {
    return new Account(accountDto.getId(), accountDto.getName(), accountDto.getBalance());
  }

  public static AccountDto mapToAccountDto(Account account) {
    return new AccountDto(account.getId(), account.getName(), account.getBalance());
  }
}
