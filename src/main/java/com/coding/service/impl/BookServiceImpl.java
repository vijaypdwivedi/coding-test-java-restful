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

@Service
public class BookServiceImpl implements BookService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBookStock(BookDetails bookDetails) {
        try{
            bookRepository.saveBookDetails(bookDetails);
        }catch(Exception exception){
            logger.error("BookServiceImpl::addBookStock -> Failed to saved book details into database: ErrorMessage{}",exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode,exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Book details saved into database:{}",bookDetails);
        return "Book details saved successfully";
    }

    @Override
    public String updateBookStock(BookDetails bookDetails) {
        try{
            bookRepository.updateBookDetails(bookDetails);
        }catch(Exception exception){
            logger.error("BookServiceImpl::updateBookStock -> Failed to updated book details into database: ErrorMessage{}",exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode,exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Book details updated into database:{}",bookDetails);
        return "Book details updated successfully";
    }

    @Override
    public String removeBookFromStock(Long bookId) {
        try{
            bookRepository.removeBookDetails(bookId);
        }catch(Exception exception){
            logger.error("BookServiceImpl::updateBookStock -> Failed to remove book details from database: ErrorMessage{}",exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode,exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Book details removed from database:{}",bookId);
        return "Book details removed successfully";
    }

    @Override
    public List<Book> getBookDetailsByTitle(String title) {
        List<Book> book = bookRepository.getBookDetailsByTitle(title);
        return book;
    }
}
