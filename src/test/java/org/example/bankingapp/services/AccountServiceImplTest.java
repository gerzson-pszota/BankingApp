package org.example.bankingapp.services;

import org.example.bankingapp.dtos.AccountDto;
import org.example.bankingapp.exceptions.AlreadyExistsException;
import org.example.bankingapp.exceptions.DoesNotExistException;
import org.example.bankingapp.exceptions.FundsException;
import org.example.bankingapp.mappers.AccountMapper;
import org.example.bankingapp.models.Account;
import org.example.bankingapp.repositories.AccountRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

  @Mock private AccountRepository accountRepository;

  @InjectMocks private AccountServiceImpl accountService;

  private AccountDto accountDto;

  @BeforeEach
  void set_up() {
    MockitoAnnotations.openMocks(this);
    accountDto = new AccountDto(1L, "testAccount", 2000.0);
  }

  @Test
  void should_create_account() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    AccountDto createdAccount = accountService.createAccount(accountDto);

    assertEquals(accountDto, createdAccount);
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  void should_get_account() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    AccountDto retrievedAccount = accountService.getAccount(1L);

    assertEquals(accountDto, retrievedAccount);
    verify(accountRepository, times(1)).findById(1L);
  }

  @Test
  void should_throw_exception_when_get_account_not_found() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(DoesNotExistException.class, () -> accountService.getAccount(1L));
    verify(accountRepository, times(1)).findById(1L);
  }

  @Test
  void should_get_all_accounts() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findAll()).thenReturn(Collections.singletonList(account));

    assertEquals(Collections.singletonList(accountDto), accountService.getAllAccounts());
    verify(accountRepository, times(1)).findAll();
  }

  @Test
  void should_deposit() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    AccountDto updatedAccount = accountService.deposit(1L, 500.0);

    assertEquals(2500.0, updatedAccount.getBalance());
    verify(accountRepository, times(1)).findById(1L);
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  void should_withdraw() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    AccountDto updatedAccount = accountService.withdraw(1L, 1000.0);

    assertEquals(1000.0, updatedAccount.getBalance());
    verify(accountRepository, times(1)).findById(1L);
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  void should_throw_exception_when_withdraw_with_insufficient_funds() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    assertThrows(FundsException.class, () -> accountService.withdraw(1L, 3000.0));
    verify(accountRepository, times(1)).findById(1L);
  }

  @Test
  void should_delete_account() {
    Account account = AccountMapper.mapToAccount(accountDto);
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

    accountService.deleteAccount(1L);

    verify(accountRepository, times(1)).deleteById(1L);
  }
}
