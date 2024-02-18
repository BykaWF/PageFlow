package com.project.pageflow.service;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {

    private CartItemRepository cartItemRepository;
    private BookService bookService;

    public void createOrUpdateCartItemForStudent(CartItemDto cartItemDto, Student student){

        CartItem cartItem = cartItemDto.toItem();
        Optional<Book> bookOptional = bookService.findById(cartItemDto.getBookId());

        Book book = bookOptional.orElse(null);
        if(book != null) {
            cartItem.setBook(book);

            cartItem.setShoppingSession(student.getShoppingSession());

            cartItemRepository.save(cartItem);
        }else {
            throw new RuntimeException("Book is null");
        }
    }
}
