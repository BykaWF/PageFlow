package com.project.pageflow.controller;

import com.project.pageflow.dto.FormRequestDto;
import com.project.pageflow.dto.InitiateOrderRequest;
import com.project.pageflow.dto.PaymentMethodDto;
import com.project.pageflow.dto.ShippingAddressDto;
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

    private final PaymentMethodRepository paymentMethodRepository;
    private final ShippingAddressRepository shippingAddressRepository;


    public CheckoutController(TransactionService transactionService, ShoppingSessionService shoppingSessionService, StudentService studentService, PaymentMethodRepository paymentMethodRepository, ShippingAddressRepository shippingAddressRepository) {
        this.transactionService = transactionService;
        this.shoppingSessionService = shoppingSessionService;

        this.studentService = studentService;
        this.paymentMethodRepository = paymentMethodRepository;
        this.shippingAddressRepository = shippingAddressRepository;
    }


    @PostMapping("/submitCheckout")
    @PreAuthorize("isAuthenticated()")
    public String initiateTransaction(@Valid FormRequestDto formRequestDto, Authentication authentication) throws Exception {

        ShoppingCartInfo shoppingCartInfo = shoppingSessionService.getShoppingCartInfo(studentService.getCurrentStudent(authentication));
        ShippingAddress shippingAddress = formRequestDto.getShippingAddress();
        PaymentMethod payment = formRequestDto.getPayment();

        shippingAddressRepository.save(shippingAddress);
        paymentMethodRepository.save(payment);
        InitiateOrderRequest initiateOrderRequest = initiateOrderRequest(payment,
                shippingAddress,
                shoppingCartInfo.getCartItems(),
                shoppingCartInfo.getShoppingSession());

        transactionService.initiateTransaction(initiateOrderRequest, authentication);


       return "redirect:/checkout-success";
    }

    @GetMapping("/checkout-success")
    @PreAuthorize("isAuthenticated()")
    public String getCheckoutSuccessPage(Model model){
        Transaction transaction = transactionService.getLastTransaction();
        model.addAttribute("transaction", transaction);

        return "checkout-success";
    }

    private InitiateOrderRequest initiateOrderRequest(PaymentMethod paymentMethod,
                                      ShippingAddress shippingAddress,
                                      List<CartItem> cartItems,
                                      ShoppingSession shoppingSession) {


        InitiateOrderRequest initiateOrderRequest = new InitiateOrderRequest();
        initiateOrderRequest.setPaymentMethod(paymentMethod);
        initiateOrderRequest.setShippingAddress(shippingAddress);
        initiateOrderRequest.setCartItemList(cartItems);
        initiateOrderRequest.setTotal(shoppingSession.getTotal());

        return initiateOrderRequest;
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
