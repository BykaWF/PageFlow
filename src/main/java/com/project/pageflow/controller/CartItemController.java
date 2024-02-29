package com.project.pageflow.controller;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.service.CartItemService;
import com.project.pageflow.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartItemController {

    private CartItemService cartItemService;
    private StudentService studentService;

    @GetMapping("/cart")
    public String getCartItems(Model model,Authentication authentication){
        ShoppingSession currentShoppingSession = studentService.getCurrentStudent(authentication).getShoppingSession();

        List<CartItem> cartItems = cartItemService.getCartItems(currentShoppingSession);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("shoppingSession", currentShoppingSession);

        return "cart";
    }

    @PostMapping("/new-item")
    public ResponseEntity<?> addItem(@RequestBody CartItemDto cartItemDto, Authentication authentication) {

        cartItemService.addOrUpdateCartItemForStudent(cartItemDto, studentService.getCurrentStudent(authentication));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item added successfully");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestParam("cartItemId")Long cartItemId){
        cartItemService.deleteCartItem(cartItemId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item deleted successfully");
    }
}
