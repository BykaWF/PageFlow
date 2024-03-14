package com.project.pageflow.controller;

import com.project.pageflow.models.Transaction;
import com.project.pageflow.service.StudentService;
import com.project.pageflow.service.TransactionService;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private final TransactionService transactionService;
    private final StudentService studentService;

    @GetMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    public String getOrderPage(Model model, Authentication authentication){
        List<Transaction> ordersOfCurrentStudent = transactionService.getOrdersOfCurrentStudent(studentService.getCurrentStudent(authentication));

        model.addAttribute("orders", ordersOfCurrentStudent);

        return "orders";
    }

}
