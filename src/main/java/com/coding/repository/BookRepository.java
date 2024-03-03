package com.coding.repository;

import com.coding.dto.Book;
import com.coding.entity.BookDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookRepository {
    @Insert("INSERT INTO books(title,author,category_id,price,stock_quantity) VALUES(#{title},#{author},#{category},#{price},#{stockQuantity})")
    public void saveBookDetails(BookDetails bookDetails);

    @Select("SELECT id, title,author,price,stock_quantity as stockQuantity FROM books WHERE title = #{title}")
    public Book getBookDetailsByTitle(String title);

    @Select("SELECT id, title,author,price,stock_quantity as stockQuantity FROM books WHERE author = #{author}")
    public Book getBookDetailsByAuthor(String author);

}
