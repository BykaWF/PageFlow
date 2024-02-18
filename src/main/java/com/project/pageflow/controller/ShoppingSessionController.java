package com.project.pageflow.controller;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.*;
import com.project.pageflow.service.CartItemService;
import com.project.pageflow.service.ShoppingSessionService;
import com.project.pageflow.service.StudentService;
import com.project.pageflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/shop")
@AllArgsConstructor
public class ShoppingSessionController {

    private ShoppingSessionService shoppingSessionService;
    private UserService userService;
    private CartItemService cartItemService;
    private StudentService studentService;


//    @PostMapping("/new-item")
//    public ResponseEntity<?> createItem(Authentication authentication, @RequestBody CartItemDto cartItemDto) {
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof SecuredUser) {
//            // Get User From Security Context
//            UserDetails userDetails = (UserDetails) principal;
//            String username = userDetails.getUsername();
//            SecuredUser user = (SecuredUser) userService.loadUserByUsername(username);
//            Student student = user.getStudent();
//            //Check does he have a shopping cart
//            if (student.getShoppingCart() == null) {
//
//                ShoppingCart shoppingCart = new ShoppingCart();
//                shoppingCart.setStudent(student);
//                student.setShoppingCart(shoppingCart);
//
//                shoppingCartRepository.save(shoppingCart);
//                studentRepository.save(student);
//
//            }else{
//
//                CartItem cartItem = cartItemDto.toItem();
//
//                cartItem.setShoppingCart(student.getShoppingCart());
//
//                cartItemRepository.save(cartItem);
//
//                return ResponseEntity.ok("Done");
//            }
//
//            // if student has already a shopping cart, add to exist, if not create new, add to it item;
//
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please authorize");
//        }
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hey, we have a problem ");
//    }

    @PostMapping("/new-item")
    public ResponseEntity<?> createItem(@RequestBody CartItemDto cartItemDto) {


        Optional<Student> studentOptional = studentService.find(1);
        Student student = studentOptional.orElse(null);


        cartItemService.createOrUpdateCartItemForStudent(cartItemDto, student);



        return ResponseEntity.ok("Done");

    }
}
