package com.project.pageflow.controller;

import com.project.pageflow.models.Book;
import com.project.pageflow.models.Genre;
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

    @GetMapping("/library/{genreId}")
    @PreAuthorize("isAuthenticated()")
    public String getBookPageByGenre(Model model, @PathVariable("genreId") Integer genreId){
        Genre genre = Genre.getGenreById(genreId);
        List<Book> bookListByGenre = bookService.findByGenre(genre);

        model.addAttribute("books", bookListByGenre);

        return "genre-page";
    }


    @GetMapping("/book/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getBookItem(@PathVariable("id") Integer id, Model model){
        Optional<Book> bookOptional = bookService.findById(id);

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

    @GetMapping("/book/search")
    @PreAuthorize("isAuthenticated()")
    public String getBooksFromSearchBar(@RequestParam("query") String query, Model model){

        List<Book> fromSearchBar = bookService.getFromSearchBar(query);
        model.addAttribute("searchBar", fromSearchBar);

        if(fromSearchBar.isEmpty()){
            model.addAttribute("message","Book is not found");
        }

        return "search-book";
    }

}
