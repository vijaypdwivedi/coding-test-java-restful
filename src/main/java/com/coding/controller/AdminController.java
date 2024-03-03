package com.coding.controller;

import com.coding.entity.BookDetails;
import com.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/")
public class AdminController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add/book")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBookDetails(@RequestBody BookDetails bookDetails) {
        String status = bookService.addBookStock(bookDetails);
        return status;
    }

}
