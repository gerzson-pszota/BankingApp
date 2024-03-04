package org.example.bankingapp.controllers;

import org.example.bankingapp.dtos.AccountDto;
import org.example.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/add")
  public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
    return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
    AccountDto accountDto = accountService.getAccount(id);
    return ResponseEntity.ok(accountDto);
  }
}