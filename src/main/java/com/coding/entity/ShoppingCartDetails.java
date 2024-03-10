package com.coding.entity;

public class ShoppingCartDetails {
    private Long userId;
    private Long bookId;
    private Long quantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingDetails{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
