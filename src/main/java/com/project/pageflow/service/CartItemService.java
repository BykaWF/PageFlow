package com.project.pageflow.service;

import com.project.pageflow.dto.CartItemDto;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.CartItem;
import com.project.pageflow.models.ShoppingSession;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CartItemService {

    private CartItemRepository cartItemRepository;
    private BookService bookService;
    @Transactional
    public void addOrUpdateCartItemForStudent(CartItemDto cartItemDto, Student student){
        // get a book based on id
        Book book = getBookById(cartItemDto.getBookId());
        // get a session based on student
        ShoppingSession currentShoppingSession = student.getShoppingSession();
        CartItem cartItem = cartItemDto.toItem();

        if (currentShoppingSession == null) {
            throw new IllegalStateException("Shopping session not found for student");
        }else {
            // add moment to check is it book exist with currentShoppingSession for current student Cart ?

            if(cartItemRepository.isBookInCartForSession(currentShoppingSession,book)){

                updateQuantityOfBook(book, cartItemDto.getQuantity());

            }else {
                cartItem.setBook(book);
                cartItem.setShoppingSession(currentShoppingSession);
                cartItemRepository.save(cartItem);
            }
        }

    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.delete(Objects.requireNonNull(cartItemRepository.findById(cartItemId).orElse(null)));
    }

    public List<CartItem> getCartItems(Long sessionId){
        return cartItemRepository.findCartItemByShoppingSessionId(sessionId);
    }

    private Book getBookById(Integer bookId){
       return bookService.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    private void updateQuantityOfBook(Book book, Integer quantity) {
        cartItemRepository.findCartItemByBook(book).setQuantity(quantity);
    }

}
