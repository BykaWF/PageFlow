package com.project.pageflow.controller;

import com.project.pageflow.dto.FormRequestDto;
import com.project.pageflow.dto.InitiateOrderRequest;
import com.project.pageflow.models.*;
import com.project.pageflow.repository.PaymentMethodRepository;
import com.project.pageflow.repository.ShippingAddressRepository;
import com.project.pageflow.service.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CheckoutController {
    private final TransactionService transactionService;
    private final ShoppingSessionService shoppingSessionService;
    private final StudentService studentService;


    public CheckoutController(TransactionService transactionService,
                              ShoppingSessionService shoppingSessionService,
                              StudentService studentService
    ) {
        this.transactionService = transactionService;
        this.shoppingSessionService = shoppingSessionService;
        this.studentService = studentService;


    }


    @PostMapping("/submitCheckout")
    @PreAuthorize("isAuthenticated()")
    public String initiateTransaction(@Valid FormRequestDto formRequestDto, Authentication authentication) throws Exception {

        InitiateOrderRequest initiateOrderRequest = transactionService.convertFormRequestToOrderRequest(formRequestDto,authentication);
        OrderStatus orderStatus = transactionService.initiateTransaction(initiateOrderRequest, authentication);

        if(orderStatus == OrderStatus.SUCCESS){
            return "redirect:/checkout-success";
        }else {
            return "checkout";
        }


    }


    @GetMapping("/checkout-success")
    @PreAuthorize("isAuthenticated()")
    public String getCheckoutSuccessPage(Model model) {
        Transaction transaction = transactionService.getLastTransaction();
        model.addAttribute("transaction", transaction);

        return "checkout-success";
    }

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String getCheckoutPage(Model model, Authentication authentication) {
        ShoppingCartInfo shoppingCartInfo = shoppingSessionService.getShoppingCartInfo(studentService.getCurrentStudent(authentication));

        model.addAttribute("cartItems", shoppingCartInfo.getCartItems());
        model.addAttribute("shoppingSession", shoppingCartInfo.getShoppingSession());

        return "checkout";
    }


}
