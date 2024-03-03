package com.coding.service;
import com.coding.dto.Book;
import com.coding.entity.BookDetails;

public interface BookService {
    public String addBookStock(BookDetails bookDetails);
    public void updateBookStock();
    public void removeBookFromStock();
    public Book getBookDetailsByTitle(String title);

}
