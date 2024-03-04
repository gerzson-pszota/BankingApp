package org.example.bankingapp.services;

import org.example.bankingapp.dtos.AccountDto;

public interface AccountService {

  AccountDto createAccount(AccountDto accountDto);

  AccountDto getAccount(Long id);
}
