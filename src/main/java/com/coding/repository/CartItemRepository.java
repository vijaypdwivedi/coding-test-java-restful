package com.coding.repository;

import com.coding.dto.CartItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartItemRepository {
    @Select("SELECT * FROM cart_items WHERE cart_id = #{cardId} AND book_id = #{bookId}")
    CartItem findCartItemByCartAndBook(Long cardId, Long bookId);

    @Insert("INSERT INTO cart_items (cart_id, book_id, quantity) VALUES (#{cartId}, #{bookId}, #{quantity})")
    void addCartItem(CartItem cartItem);

    @Update("UPDATE cart_items SET quantity = #{quantity} WHERE id = #{id}")
    void updateCartItem(CartItem cartItem);
}
