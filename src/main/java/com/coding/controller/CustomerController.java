package com.coding.controller;


import com.coding.dto.Book;
import com.coding.entity.ShoppingCartDetails;
import com.coding.exception.ServiceException;
import com.coding.service.BookService;
import com.coding.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/v1/customer/")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    // Endpoint to get book details by title
    @GetMapping("/get/book/title/{title}")
    @PreAuthorize("hasRole('USER''ADMIN')")
    public ResponseEntity getBooks(@PathVariable String title) {
        logger.info("Process to get book details by title: {}", title);
        // Retrieve book details by title from the service
        List<Book> book = bookService.getBookDetailsByTitle(title);
        if (book.isEmpty()) {
            // Return 404 status code if no book is found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested book details not found");
        }
        // Return 200 status code with book details if found
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }


    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add/shopping/cart")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> addBookToCart(@RequestBody ShoppingCartDetails shoppingCartDetails) {
        try {
            shoppingCartService.addBookToShoppingCart(shoppingCartDetails.getUserId(), shoppingCartDetails.getBookId(), shoppingCartDetails.getQuantity());
            return ResponseEntity.ok("Book added to the shopping cart successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException serviceException) {
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
    }

    @DeleteMapping("/remove/shopping/cart")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> removeBookFromCart(@RequestBody ShoppingCartDetails shoppingCartDetails) {
        try {
            shoppingCartService.removeBookFromShoppingCart(shoppingCartDetails.getUserId(), shoppingCartDetails.getBookId());
            return ResponseEntity.ok("Book removed from the shopping cart successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException serviceException) {
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
    }

    @PostMapping("/checkout/shopping/cart/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> checkoutAndBuyBooks(@PathVariable Long userId) {
        try {
            shoppingCartService.checkoutAndBuyBooks(userId);
            return ResponseEntity.ok("Checkout successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ServiceException serviceException) {
            return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
    }
}
