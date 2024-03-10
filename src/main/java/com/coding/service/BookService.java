package com.coding.service;
import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import com.coding.entity.User;

import java.util.List;

public interface BookService {

    public String addUser(User user);
    public String addBookStock(BookDetails bookDetails);
    public String updateBookStock(BookDetails bookDetails);
    public String removeBookFromStock(Long bookId);
    public List<Book> getBookDetailsByTitle(String title);

}
