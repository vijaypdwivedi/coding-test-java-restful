package com.coding.service;
import com.coding.dto.Book;
import com.coding.entity.BookDetails;

import java.util.List;

public interface BookService {
    public String addBookStock(BookDetails bookDetails);
    public String updateBookStock(BookDetails bookDetails);
    public String removeBookFromStock(Long bookId);
    public List<Book> getBookDetailsByTitle(String title);

}
