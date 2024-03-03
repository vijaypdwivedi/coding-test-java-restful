package com.coding.controller;


import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import com.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer/")
public class CustomerController {

    @Autowired
    private BookService bookService;

    @GetMapping("/get/book/title")
    public String getBooks(@RequestHeader String title) {

        Book book = bookService.getBookDetailsByTitle(title);

        return book.toString();
    }

}
