package org.example.bankingapp.services;

import org.example.bankingapp.dtos.AccountDto;

import java.util.List;

public interface AccountService {

  AccountDto createAccount(AccountDto accountDto);

  AccountDto getAccount(Long id);

  List<AccountDto> getAllAccounts();

  AccountDto deposit(Long id, Double amount);

  AccountDto withdraw(Long id, Double amount);

  void deleteAccount(Long id);
}
