package com.project.pageflow.models;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartInfo {

    private List<CartItem> cartItems;
    private ShoppingSession shoppingSession;
}
