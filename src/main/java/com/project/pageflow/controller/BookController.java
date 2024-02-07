package com.project.pageflow.controller;

import com.project.pageflow.models.Book;
import com.project.pageflow.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/library")
    @PreAuthorize("isAuthenticated()")
    public String getLibraryPage(Model model){

        List<Book> bookList = bookService.getAll();
        model.addAttribute("books", bookList);

        return "library";
    }

    @GetMapping("/book/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getBookItem(@PathVariable("id") Integer id, Model model){
        Optional<Book> bookOptional = bookService.getById(id);

        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            model.addAttribute("book",book);
            model.addAttribute("message", "book fetched");
            return "book";
        }else{
            model.addAttribute("error", "book error");
            return "library";
        }
    }

}
