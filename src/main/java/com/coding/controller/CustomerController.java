package com.coding.controller;


import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import com.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer/")
public class CustomerController {

    @Autowired
    private BookService bookService;

    @GetMapping("/get/book/title")
    public ResponseEntity getBooks(@RequestHeader String title) {
        List<Book> book = bookService.getBookDetailsByTitle(title);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested book details not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

}
