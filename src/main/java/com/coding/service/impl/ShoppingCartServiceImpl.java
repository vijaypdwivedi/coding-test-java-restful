package com.coding.service.impl;

import com.coding.dto.Book;
import com.coding.dto.CartItem;
import com.coding.dto.ShoppingCart;
import com.coding.repository.BookRepository;
import com.coding.repository.CartItemRepository;
import com.coding.repository.ShoppingCartRepository;
import com.coding.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addBookToShoppingCart(Long userId, Long bookId, Long quantity) {
        // Check if the book exists and if it's in stock
        Book book = bookRepository.findBookDetailsById(bookId);
        if (Objects.isNull(book) || book.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Book is not available or insufficient stock.");
        }

        // Check if the user has an existing shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (Objects.isNull(cart)) {
            // If no cart exists for the user, create a new one
            cart = new ShoppingCart();
            cart.setUserId(userId);
            shoppingCartRepository.createCart(cart);
        }

        // Add the book to the cart or update the quantity if it's already in the cart
        CartItem cartItem = cartItemRepository.findCartItemByCartAndBook(cart.getId(), bookId);
        if (Objects.isNull(cartItem)) {
            // If the book is not in the cart, create a new cart item
            cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setBookId(bookId);
            cartItem.setQuantity(quantity);
            cartItemRepository.addCartItem(cartItem);
        } else {
            // If the book is already in the cart, update the quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.updateCartItem(cartItem);
        }
    }

    @Transactional
    public void removeBookFromShoppingCart(Long userId, Long bookId) {
        // Check if the user has an existing shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (Objects.isNull(cart)) {
            throw new IllegalStateException("Shopping cart not found for user.");
        }
        // Check if the book exists in the user's cart
        CartItem cartItem = cartItemRepository.findCartItemByCartAndBook(cart.getId(), bookId);
        if (Objects.isNull(cartItem)) {
            throw new IllegalArgumentException("Book not found in the shopping cart.");
        }
        // Remove the book from the cart
        shoppingCartRepository.removeCartItem(cartItem.getId());
    }

    @Transactional
    public void checkoutAndBuyBooks(Long userId) {
        // Get the user's shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalStateException("Shopping cart not found for user.");
        }

        // Retrieve the cart items
        List<CartItem> cartItems = shoppingCartRepository.findCartItemsByCartId(cart.getId());

        // Perform the purchase for each book in the cart
        for (CartItem cartItem : cartItems) {
            Book book = bookRepository.findBookDetailsById(cartItem.getBookId());
            if (Objects.isNull(book) || book.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Book is not available or insufficient stock.");
            }

            // Update the book's stock quantity
            book.setStockQuantity(book.getStockQuantity() - cartItem.getQuantity());
            shoppingCartRepository.updateShoppingBookDetails(book);

            // Remove the purchased book from the cart
            shoppingCartRepository.removeCartItem(cartItem.getId());
        }

        // We can create an order record here for the purchased books.
    }

}
