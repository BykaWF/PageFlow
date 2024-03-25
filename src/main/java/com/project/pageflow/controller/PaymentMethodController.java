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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
    public String createPaymentMethod(@Valid PaymentMethodDto paymentMethodDto, Authentication authentication){
        PaymentMethod paymentMethod = paymentMethodDto.toPaymentMethod();
        paymentMethodService.createOrUpdateEntity(paymentMethod,studentService.getCurrentStudent(authentication));

        return "redirect:/payment-info";
    }

    @PostMapping("/updatePaymentMethod")
    @PreAuthorize("isAuthenticated()")
    public String updatePaymentMethod(@ModelAttribute("paymentMethod") PaymentMethod paymentMethod, Authentication authentication){
        Optional<PaymentMethod> oldPaymentMethod = paymentMethodService.findById(paymentMethod.getPaymentId());

        if(oldPaymentMethod.isPresent()){
            PaymentMethod updatedPaymentMethod = oldPaymentMethod.get();
            updatedPaymentMethod.setCardHolderName(paymentMethod.getCardHolderName());
            updatedPaymentMethod.setExpirationMonth(paymentMethod.getExpirationMonth());
            updatedPaymentMethod.setCardNumber(paymentMethod.getCardNumber());
            updatedPaymentMethod.setExpirationYear(paymentMethod.getExpirationYear());
            updatedPaymentMethod.setType(paymentMethod.getType());
            paymentMethodService.updatePaymentMethod(updatedPaymentMethod);
        }
        return "redirect:/payment-info";
    }
}
