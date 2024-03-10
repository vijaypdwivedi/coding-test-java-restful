package com.coding.controller;

import com.coding.dto.UserInfo;
import com.coding.entity.BookDetails;
import com.coding.entity.User;
import com.coding.exception.ServiceException;
import com.coding.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/v1/admin/")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookService bookService;

    // Endpoint to add book details
    @PostMapping("/add/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addBookDetails(@RequestBody BookDetails bookDetails) {
        logger.info("Process to save bookDetails: {}", bookDetails);
        String response = null;
        try {
            // Call service method to add book details
            response = bookService.addBookStock(bookDetails);
        } catch (ServiceException serviceException) {
            // Handle service exception and return error response
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to update book details
    @PutMapping("/update/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateBookDetails(@RequestBody BookDetails bookDetails) {
        logger.info("Process to update bookDetails: {}", bookDetails);
        String response = null;
        try {
            // Call service method to update book details
            response = bookService.updateBookStock(bookDetails);
        } catch (ServiceException serviceException) {
            // Handle service exception and return error response
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to remove book details
    @DeleteMapping("/remove/book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity removeBookDetails(@PathVariable("id") Long bookId) {
        logger.info("Process to remove bookId: {}", bookId);
        String response = null;
        try {
            // Call service method to remove book details
            response = bookService.removeBookFromStock(bookId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (ServiceException serviceException) {
            // Handle service exception and return error response
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
    }

    // Endpoint to add book details
    @PostMapping("/add/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity registerUser(@RequestBody User user) {
        logger.info("Process to register user: {}", user);
        String response = null;
        try {
            // Call service method to add book details
            response = bookService.addUser(user);
        } catch (ServiceException serviceException) {
            // Handle service exception and return error response
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
