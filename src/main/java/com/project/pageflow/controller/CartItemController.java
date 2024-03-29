package com.project.pageflow.controller;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingCartInfo;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.service.BookService;
import com.project.pageflow.service.CartItemService;
import com.project.pageflow.service.ShoppingSessionService;
import com.project.pageflow.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ShoppingSessionService shoppingSessionService;
    private BookService bookService;

    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    public String getCartItems(Model model,Authentication authentication){

        ShoppingCartInfo shoppingCartInfo = shoppingSessionService.getShoppingCartInfo(studentService.getCurrentStudent(authentication));
        List<CartItem> pendingCartItems = cartItemService.getPendingCartItems(shoppingCartInfo.getCartItems());
        ShoppingSession shoppingSession = studentService.getCurrentStudent(authentication).getShoppingSession();
        model.addAttribute("cartItems", pendingCartItems);
        model.addAttribute("shoppingSession",shoppingSession);

        return "cart";
    }

    @PostMapping("/new-item")
    @PreAuthorize("isAuthenticated()")
    public String addItem(@ModelAttribute("cartItemDto") CartItemDto cartItemDto, Authentication authentication) {

        cartItemService.addOrUpdateCartItemForStudent(cartItemDto, studentService.getCurrentStudent(authentication));
        return "redirect:/library";

    }

    @PostMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteItem(@RequestParam("cartItemId")Long cartItemId){
        cartItemService.deleteCartItem(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/updateQuantity")
    @PreAuthorize("isAuthenticated()")
    public String updateQuantity(@RequestParam("bookId") Integer bookId, @RequestParam("quantity") Integer quantity){
        cartItemService.updateExistingCartItem(
                bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book is not found"))
                ,quantity);
        return "redirect:/cart";
    }

}
