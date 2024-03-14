package com.project.pageflow.controller;

import com.project.pageflow.dto.PaymentMethodDto;

import com.project.pageflow.models.PaymentMethod;

import com.project.pageflow.service.PaymentMethodService;
import com.project.pageflow.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;
    private final StudentService studentService;
    @GetMapping("/payment-info")
    @PreAuthorize("isAuthenticated()")
    public String getAddressPage(Model model, Authentication authentication){
        PaymentMethod paymentAddressOfCurrentStudent = paymentMethodService.getPaymentMethodOfCurrentStudent(
                studentService.getCurrentStudent(authentication)
        );

        model.addAttribute("paymentMethod", paymentAddressOfCurrentStudent);

        return "payment-info";
    }

    @PostMapping("/savePaymentMethod")
    @PreAuthorize("isAuthenticated()")
    public String updateOrCreateAddress(@Valid PaymentMethodDto paymentMethodDto, Authentication authentication){
        PaymentMethod paymentMethod = paymentMethodDto.toPaymentMethod();
        paymentMethodService.createOrUpdateEntity(paymentMethod,studentService.getCurrentStudent(authentication));

        return "redirect:/payment-info";
    }
}
