package com.coding.controller;

import com.coding.entity.BookDetails;
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

    @PostMapping("/add/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addBookDetails(@RequestBody BookDetails bookDetails) {
        logger.info("AdminController::removeBookDetails Process to save bookDetails:{} ",bookDetails);
        String response = null;
        try{
            response = bookService.addBookStock(bookDetails);
        }catch (ServiceException serviceException){
            ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateBookDetails(@RequestBody BookDetails bookDetails) {
        logger.info("AdminController::removeBookDetails Process to update bookDetails:{} ",bookDetails);
        String response = null;
        try{
            response = bookService.updateBookStock(bookDetails);
        }catch (ServiceException serviceException){
            ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/remove/book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity removeBookDetails(@PathVariable("id") Long bookId) {
        logger.info("AdminController::removeBookDetails Process to remove bookId:{} ",bookId);
        String response = null;
        try{
            response=  bookService.removeBookFromStock(bookId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch (ServiceException serviceException){
           return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException.getErrorMessage());
        }
    }

}
