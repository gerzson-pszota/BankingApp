package org.example.bankingapp.services;

import org.example.bankingapp.dtos.AccountDto;
import org.example.bankingapp.exceptions.DoesNotExistException;
import org.example.bankingapp.exceptions.FundsException;
import org.example.bankingapp.mappers.AccountMapper;
import org.example.bankingapp.models.Account;
import org.example.bankingapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public AccountDto createAccount(AccountDto accountDto) {
    Account account = AccountMapper.mapToAccount(accountDto);
    Account savedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(savedAccount);
  }

  @Override
  public AccountDto getAccount(Long id) {
    Account account =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new DoesNotExistException("Account does not exist."));
    return AccountMapper.mapToAccountDto(account);
  }

  @Override
  public List<AccountDto> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
  }

  @Override
  public AccountDto deposit(Long id, Double amount) {
    Account account =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new DoesNotExistException("Account does not exist."));

    account.setBalance(account.getBalance() + amount);
    return AccountMapper.mapToAccountDto(accountRepository.save(account));
  }

  @Override
  public AccountDto withdraw(Long id, Double amount) {
    Account account =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new DoesNotExistException("Account does not exist."));

    if (amount <= account.getBalance()) {
      account.setBalance(account.getBalance() - amount);
      return AccountMapper.mapToAccountDto(accountRepository.save(account));
    } else {
      throw new FundsException("Not enough funds.");
    }
  }

  @Override
  public void deleteAccount(Long id) {
    Account account =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new DoesNotExistException("Account does not exist."));

    accountRepository.deleteById(id);
  }
}
