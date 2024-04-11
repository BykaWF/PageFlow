package com.project.pageflow.controller;

import com.project.pageflow.dto.ShippingAddressDto;
import com.project.pageflow.models.PaymentMethod;
import com.project.pageflow.models.ShippingAddress;
import com.project.pageflow.service.ShippingAddressService;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;
    private final StudentService studentService;
    @GetMapping("/address")
    @PreAuthorize("isAuthenticated()")
    public String getAddressPage(Model model, Authentication authentication){
        ShippingAddress shippingAddressOfCurrentStudent = shippingAddressService.getShippingAddressOfCurrentStudent(
                studentService.getCurrentStudent(authentication)
        );

        model.addAttribute("shippingAddress", shippingAddressOfCurrentStudent);

        return "address";
    }

    @PostMapping("/saveAddress")
    @PreAuthorize("isAuthenticated()")
    public String updateOrCreateAddress(@Valid ShippingAddressDto shippingAddressDto, Authentication authentication){
        ShippingAddress shippingAddress = shippingAddressDto.toShippingAddress();
        shippingAddressService.createOrUpdateEntity(shippingAddress,studentService.getCurrentStudent(authentication));

        return "redirect:/address";
    }

    @PostMapping("/updateAddress")
    @PreAuthorize("isAuthenticated()")
    public String updatePaymentMethod(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress, Authentication authentication){
        Optional<ShippingAddress> oldShippingAddress = shippingAddressService.findById(shippingAddress.getAddressId());

        if(oldShippingAddress.isPresent()){
            ShippingAddress updatedShippingAddress = oldShippingAddress.get();
            updatedShippingAddress.setStreetAddress(shippingAddress.getStreetAddress());
            updatedShippingAddress.setCity(shippingAddress.getCity());
            updatedShippingAddress.setCountry(shippingAddress.getCountry());
            updatedShippingAddress.setPostalCode(shippingAddress.getPostalCode());
            shippingAddressService.updateShippingAddress(updatedShippingAddress);
        }
        return "redirect:/address";
    }




}
