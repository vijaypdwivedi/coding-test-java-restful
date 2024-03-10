package com.coding.service.impl;

import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import com.coding.exception.ServiceException;
import com.coding.repository.BookRepository;
import com.coding.service.BookService;
import com.coding.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
// Service implementation class for BookService
@Service
public class BookServiceImpl implements BookService {

    // Logger for logging messages
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Injecting BookRepository for database operations
    @Autowired
    private BookRepository bookRepository;

    // Method to add book stock
    @Override
    public String addBookStock(BookDetails bookDetails) {
        try {
            // Save book details into the database
            bookRepository.saveBookDetails(bookDetails);
        } catch (Exception exception) {
            // Log error message and throw ServiceException
            logger.error("Failed to save book details into database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Log success message
        logger.info("Book details saved into database: {}", bookDetails);
        return "Book details saved successfully";
    }

    // Method to update book stock
    @Override
    public String updateBookStock(BookDetails bookDetails) {
        try {
            // Update book details in the database
            bookRepository.updateBookDetails(bookDetails);
        } catch (Exception exception) {
            // Log error message and throw ServiceException
            logger.error("Failed to update book details into database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Log success message
        logger.info("Book details updated into database: {}", bookDetails);
        return "Book details updated successfully";
    }

    // Method to remove book from stock
    @Override
    public String removeBookFromStock(Long bookId) {
        try {
            // Remove book details from the database
            bookRepository.removeBookDetails(bookId);
        } catch (Exception exception) {
            // Log error message and throw ServiceException
            logger.error("Failed to remove book details from database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Log success message
        logger.info("Book details removed from database: {}", bookId);
        return "Book details removed successfully";
    }

    // Method to get book details by title
    @Override
    public List<Book> getBookDetailsByTitle(String title) {
        // Retrieve book details from the database by title
        List<Book> book = bookRepository.getBookDetailsByTitle(title);
        return book;
    }
}
