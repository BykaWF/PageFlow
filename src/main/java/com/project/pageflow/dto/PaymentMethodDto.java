package com.project.pageflow.dto;

import com.project.pageflow.models.PaymentMethod;
import com.project.pageflow.models.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodDto {

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


    public PaymentMethod toPaymentMethod(){

        return PaymentMethod.builder()
                .type(this.type)
                .cardHolderName(this.cardHolderName)
                .cardNumber(this.cardNumber)
                .expirationYear(this.expirationYear)
                .expirationMonth(this.expirationMonth)
                .isDefaultPaymentMethod(true)
                .build();
    }
}
