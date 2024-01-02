package com.project.pageflow.controller;

import com.project.pageflow.dto.CreateBookRequest;
import com.project.pageflow.dto.SearchRequest;
import com.project.pageflow.models.Book;
import com.project.pageflow.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")

public class BookController {

    // Adding a book

    /**
     * Book ---> Author -> map book to the author
     */

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {
        bookService.createOrUpdateBook(createBookRequest.toBook());
    }

    //Getting a list of Books
    @GetMapping("/getBooks")
    public List<Book> getBooks(@RequestBody @Valid SearchRequest searchRequest) throws Exception {
        List<Book> list = bookService.findBook(searchRequest.getSearchKey(), searchRequest.getSearchValue());
        return list;
    }
}
