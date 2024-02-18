package com.project.pageflow.service;

import com.project.pageflow.models.Author;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.Genre;
import com.project.pageflow.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public BookService(AuthorService authorService, BookRepository bookRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    public void createOrUpdateBook(Book book) {

        Author bookAuthor = book.getBook_author();

        // Create an author if it doesn't exist (getOrCreate)
        Author existingAuthor = authorService.getOrCreate(bookAuthor);

        // Book.setAuthor() -> map this author to the book
        book.setBook_author(existingAuthor);

        //Save book
        bookRepository.save(book);
    }

    public List<Book> getAll(){
        return bookRepository.findAll();
    }


    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> getFromSearchBar(String query) {
        return bookRepository.searchFromBar(query);
    }
}
