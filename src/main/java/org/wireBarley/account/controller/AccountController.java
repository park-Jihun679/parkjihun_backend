package org.wireBarley.account.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wireBarley.account.dto.AccountCreateDTO;
import org.wireBarley.account.dto.AccountDTO;
import org.wireBarley.account.service.AccountService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public AccountDTO createAccount(@RequestBody AccountCreateDTO createDTO) {
        return accountService.createAccount(createDTO);
    }

    @DeleteMapping("/accounts/{accountId}")
    public AccountDTO deleteAccount(@PathVariable("accountId") UUID accountId) {
        return accountService.deleteAccount(accountId);
    }
}
