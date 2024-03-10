package com.coding.repository;

import com.coding.dto.Book;
import com.coding.dto.CartItem;
import com.coding.dto.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartRepository {
    @Insert("INSERT INTO shopping_carts (user_id) VALUES (#{userId})")
    void createCart(ShoppingCart shoppingCart);

    @Select("SELECT id, user_id as userId FROM shopping_carts WHERE user_id = #{userId}")
    ShoppingCart findByUserId(Long userId);

    @Select("SELECT id, card_id as cartId, book_id as bookId, quantity  FROM cart_items WHERE cart_id = #{id}")
    List<CartItem> findCartItemsByCartId(Long id);

    @Update("UPDATE books SET title = #{title}, author = #{author}, category_id = #{categoryId}, price = #{price}, stock_quantity = #{stockQuantity} WHERE id = #{id}")
    void updateShoppingBookDetails(Book book);

    @Update("DELETE FROM cart_items WHERE id = #{id}")
    void removeCartItem(Long id);


}
