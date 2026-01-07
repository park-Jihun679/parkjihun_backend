package org.wireBarley.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wireBarley.example.dto.ExampleDTO;
import org.wireBarley.example.dto.ExampleFilterDTO;
import org.wireBarley.transaction.dto.TransactionCreateDTO;
import org.wireBarley.transaction.dto.TransactionDTO;
import org.wireBarley.transaction.dto.TransactionFilterDTO;
import org.wireBarley.transaction.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public TransactionDTO createTransaction(@RequestBody TransactionCreateDTO createDTO) {
        return transactionService.createTransaction(createDTO);
    }

    @GetMapping("/transactions")
    public Page<TransactionDTO> getTransactionsList(
        @ModelAttribute TransactionFilterDTO filterDTO){

        return transactionService.getTransactionList(filterDTO);
    }
}
