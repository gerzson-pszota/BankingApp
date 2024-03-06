package org.example.bankingapp.controllers;

import org.example.bankingapp.dtos.AccountDto;
import org.example.bankingapp.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

  @Mock private AccountService accountService;

  @InjectMocks private AccountController accountController;

  private AccountDto accountDto;

  @BeforeEach
  void set_up() {
    MockitoAnnotations.openMocks(this);
    accountDto = new AccountDto(1L, "Test Account", 2000.0);
  }

  @Test
  void test_add_account() {
    when(accountService.createAccount(any())).thenReturn(accountDto);

    ResponseEntity<AccountDto> response = accountController.addAccount(accountDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(accountDto, response.getBody());
    verify(accountService, times(1)).createAccount(accountDto);
  }

  @Test
  void test_get_account() {
    long accountId = 1L;
    when(accountService.getAccount(accountId)).thenReturn(accountDto);

    ResponseEntity<AccountDto> response = accountController.getAccount(accountId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(accountDto, response.getBody());
    verify(accountService, times(1)).getAccount(accountId);
  }

  @Test
  void test_get_all_accounts() {
    List<AccountDto> accounts = Collections.singletonList(accountDto);
    when(accountService.getAllAccounts()).thenReturn(accounts);

    ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(accounts, response.getBody());
    verify(accountService, times(1)).getAllAccounts();
  }

  @Test
  void test_deposit() {
    long accountId = 1L;
    double amount = 100.0;
    Map<String, Double> request = new HashMap<>();
    request.put("amount", amount);

    when(accountService.deposit(accountId, amount)).thenReturn(accountDto);

    ResponseEntity<AccountDto> response = accountController.deposit(accountId, request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(accountDto, response.getBody());
    verify(accountService, times(1)).deposit(accountId, amount);
  }

  @Test
  void test_withdraw() {
    long accountId = 1L;
    double amount = 100.0;
    Map<String, Double> request = new HashMap<>();
    request.put("amount", amount);

    when(accountService.withdraw(accountId, amount)).thenReturn(accountDto);

    ResponseEntity<AccountDto> response = accountController.withdraw(accountId, request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(accountDto, response.getBody());
    verify(accountService, times(1)).withdraw(accountId, amount);
  }

  @Test
  void test_delete() {
    long accountId = 1L;

    ResponseEntity<String> response = accountController.delete(accountId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(
        "Your account was successfully deleted. We hope to see you again soon!",
        response.getBody());
    verify(accountService, times(1)).deleteAccount(accountId);
  }
}
