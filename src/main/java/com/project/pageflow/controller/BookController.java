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

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("/book")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {
        bookService.createOrUpdateBook(createBookRequest.toBook());
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(@RequestBody @Valid SearchRequest searchRequest) throws Exception {
        List<Book> list = bookService.findBook(searchRequest.getSearchKey(), searchRequest.getSearchValue());
        return list;
    }
}
