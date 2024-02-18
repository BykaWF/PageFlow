package com.project.pageflow.controller;

import com.project.pageflow.dto.InitiateOrderRequest;
import com.project.pageflow.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public String initiateTransaction(@RequestBody @Valid InitiateOrderRequest initiateOrderRequest, Model model) throws Exception {

        return transactionService.initiateTransaction(initiateOrderRequest);
    }


}
