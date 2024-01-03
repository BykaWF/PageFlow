package com.project.pageflow.controller;

import com.project.pageflow.dto.InitiateTransactionRequest;
import com.project.pageflow.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String initiateTransaction(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) throws Exception {
        return transactionService.initiateTransaction(initiateTransactionRequest);
    }


}
