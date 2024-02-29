package com.project.pageflow.service;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {

    private CartItemRepository cartItemRepository;
    private BookService bookService;
    private ShoppingSessionService shoppingSessionService;

    public void addOrUpdateCartItemForStudent(CartItemDto cartItemDto, Student student){

        Optional<Book> bookOptional = bookService.findById(cartItemDto.getBookId());
        Book book = bookOptional.orElseThrow(() -> new IllegalArgumentException("Book not found"));

        ShoppingSession currentShoppingSession = student.getShoppingSession();
        if (currentShoppingSession == null) {
            throw new IllegalStateException("Shopping session not found for student");
        }

        CartItem cartItem = cartItemDto.toItem();
        cartItem.setBook(book);
        cartItem.setShoppingSession(currentShoppingSession);
        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.delete(Objects.requireNonNull(cartItemRepository.findById(cartItemId).orElse(null)));
    }

    public List<CartItem> getCartItems(ShoppingSession currentShoppingSession) {
        shoppingSessionService.updateTotalOfCurrentStudent(currentShoppingSession);

        return currentShoppingSession.getCartItems();
    }
}
