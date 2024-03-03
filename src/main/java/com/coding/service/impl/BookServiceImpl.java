package com.coding.service.impl;

import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import com.coding.repository.BookRepository;
import com.coding.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBookStock(BookDetails bookDetails) {
        bookRepository.saveBookDetails(bookDetails);
        return "Details saved to database";
    }

    @Override
    public void updateBookStock() {

    }

    @Override
    public void removeBookFromStock() {

    }

    @Override
    public Book getBookDetailsByTitle(String title) {
        Book book = bookRepository.getBookDetailsByTitle(title);
        return book;
    }
}
