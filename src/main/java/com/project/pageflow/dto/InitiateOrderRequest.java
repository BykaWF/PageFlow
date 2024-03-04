package com.project.pageflow.dto;

import com.project.pageflow.models.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateOrderRequest {

    @NotNull
    private List<CartItem> cartItemList; // take from cart

    @NotNull
    private PaymentMethod paymentMethod; // html form

    @NotNull
    private ShippingAddress shippingAddress; // html form

    @NotNull
    private BigDecimal total;

    @NotNull
    private OrderType orderType;

}
