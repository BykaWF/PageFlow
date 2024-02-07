package com.project.pageflow.controller;

import com.project.pageflow.dto.CreateAdminRequest;
import com.project.pageflow.dto.CreateBookRequest;
import com.project.pageflow.dto.SearchRequest;
import com.project.pageflow.models.Book;
import com.project.pageflow.service.AdminService;
import com.project.pageflow.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final BookService bookService;

    public AdminController(AdminService adminService, BookService bookService) {
        this.adminService = adminService;
        this.bookService = bookService;
    }

    @PostMapping("/admin")
    public void createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {
        adminService.createAdmin(createAdminRequest.toAdmin());
    }

    @PostMapping("/add")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {
        bookService.createOrUpdateBook(createBookRequest.toBook());
    }

    @GetMapping("/all")
    public List<Book> getBooks(@RequestBody @Valid SearchRequest searchRequest) throws Exception {
        return bookService.findBook(searchRequest.getSearchKey(), searchRequest.getSearchValue());
    }


}
