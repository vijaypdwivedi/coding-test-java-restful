package com.coding.service;
public interface ShoppingCartService {
    void addBookToShoppingCart(Long userId, Long bookId, Long quantity);
    void removeBookFromShoppingCart(Long userId, Long bookId);
    void checkoutAndBuyBooks(Long userId);
}
