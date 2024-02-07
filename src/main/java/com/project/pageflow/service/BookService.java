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

    public List<Book> findBook(String searchKey, String searchValue) throws Exception {
        switch (searchKey) {
            case "name":
                return bookRepository.findByTitle(searchValue);
            case "author_name":
                return bookRepository.findByAuthor_Name(searchValue);
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchValue));
            case "id": {
                Optional<Book> book = bookRepository.findById(Integer.parseInt(searchValue));
                if(book.isPresent()) {
                    return Arrays.asList(book.get());
                } else {
                    return new ArrayList<>();
                }
            } default:
                throw new Exception("Search key is not valid " + searchKey);
        }
    }

    public Optional<Book> getById(Integer id) {
        return bookRepository.findById(id);
    }
}
