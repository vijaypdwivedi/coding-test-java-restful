package com.coding.repository;

import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookRepository {
    @Insert("INSERT INTO books(title,author,category_id,price,stock_quantity) VALUES(#{title},#{author},#{category},#{price},#{stockQuantity})")
    public void saveBookDetails(BookDetails bookDetails);
    @Update("UPDATE books set title = #{title}, author = #{author},category_id = #{category},price =#{price},stock_quantity = #{stockQuantity} WHERE id = #{id}")
    public void updateBookDetails(BookDetails bookDetails);
    @Delete("DELETE FROM books  WHERE id = #{bookId}")
    public void removeBookDetails(Long bookId);
    @Select("SELECT id, title,author,price,stock_quantity as stockQuantity FROM books WHERE title = #{title}")
    public List<Book> getBookDetailsByTitle(String title);
    @Select("SELECT id, title,author,price, category_id as category,stock_quantity as stockQuantity FROM books WHERE id = #{bookId}")
    public Book findBookDetailsById(Long bookId);

}
