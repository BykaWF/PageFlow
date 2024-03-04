package com.project.pageflow.dto;

import com.project.pageflow.models.PaymentMethod;
import com.project.pageflow.models.PaymentType;
import com.project.pageflow.models.ShippingAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormRequestDto {
    @NotNull
    private PaymentType type;

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String cardHolderName;

    @NotNull
    private Integer expirationMonth;

    @NotNull
    private Integer expirationYear;

    @NotBlank
    String streetAddress;

    @NotBlank
    private String city;

    @NotNull
    private Integer postalCode;

    @NotBlank
    private String country;

    public PaymentMethod getPayment(){
        return PaymentMethod.builder()
                .type(this.type)
                .cardHolderName(this.cardHolderName)
                .cardNumber(this.cardNumber)
                .expirationYear(this.expirationYear)
                .expirationMonth(this.expirationMonth)
                .build();
    }

    public ShippingAddress getShippingAddress(){
        return ShippingAddress.builder()
                .streetAddress(this.streetAddress)
                .city(this.city)
                .country(this.country)
                .postalCode(this.postalCode)
                .build();
    }
}
