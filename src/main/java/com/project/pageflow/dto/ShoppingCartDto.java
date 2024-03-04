package com.project.pageflow.dto;

import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDto {
    @NotNull
    private Student student;
    @Min(0)
    private List<CartItem> cartItems;

    public ShoppingSession toShoppingCart(){
        return ShoppingSession.builder()
                .student(this.student)
                .cartItems(this.cartItems)
                .build();
    }
}
