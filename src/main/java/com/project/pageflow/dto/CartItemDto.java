package com.project.pageflow.dto;

import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.service.BookService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    @NotNull
    private Integer bookId;
    @Min(1)
    private Integer quantity;


    public CartItem toItem(){
        return CartItem.builder()
                .quantity(this.quantity)
                .build();
    }

}
