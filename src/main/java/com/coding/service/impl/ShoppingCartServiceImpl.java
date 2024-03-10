package com.coding.service.impl;

import com.coding.dto.Book;
import com.coding.dto.CartItem;
import com.coding.dto.ShoppingCart;
import com.coding.exception.ServiceException;
import com.coding.repository.BookRepository;
import com.coding.repository.CartItemRepository;
import com.coding.repository.ShoppingCartRepository;
import com.coding.service.ShoppingCartService;
import com.coding.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addBookToShoppingCart(Long userId, Long bookId, Long quantity) {
        logger.error("ShoppingCartServiceImpl::addBookToShoppingCart -> userId: {}, bookId:{}, quantity:",userId,bookId,quantity);
        try {
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
        }catch (Exception exception){
            logger.error("ShoppingCartServiceImpl::addBookToShoppingCart -> Error Failed to fetch details from database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void removeBookFromShoppingCart(Long userId, Long bookId) {
        logger.error("ShoppingCartServiceImpl::removeBookFromShoppingCart -> userId: {}, bookId:{}",userId,bookId);
        try {
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
        }catch (Exception exception){
            logger.error("ShoppingCartServiceImpl::removeBookFromShoppingCart -> Error Failed to fetch details from database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public void checkoutAndBuyBooks(Long userId) {
        logger.error("ShoppingCartServiceImpl::checkoutAndBuyBooks -> userId: {}:",userId);
        try{
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
        }catch (Exception exception){
            logger.error("ShoppingCartServiceImpl::checkoutAndBuyBooks -> Error Failed to fetch details from database: ErrorMessage{}", exception.getMessage());
            throw new ServiceException(Constant.dbErrorCode, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // We can create an order record here for the purchased books.
    }

}
