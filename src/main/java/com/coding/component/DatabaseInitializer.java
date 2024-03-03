package com.coding.component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createUsersTable();
        //createCategoriesTable();
        //createBooksTable();
        //createShoppingCartsTable();
       // createCartItemsTable();
    }

    private void createUsersTable() {
        String username = "admin";
        String password = "admin123"; // Plain-text password
        String role = "ADMIN";

        String encodedPassword = passwordEncoder.encode(password);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) UNIQUE NOT NULL)");
        jdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
        jdbcTemplate.update("INSERT INTO users (username, password, role) VALUES (?, ?, ?)", username, encodedPassword, role);
    }

    private void createBooksTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL, category_id INT, price DECIMAL(10, 2) NOT NULL, stock_quantity INT NOT NULL, FOREIGN KEY (category_id) REFERENCES categories(id))");
    }

    private void createCategoriesTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name VARCHAR(100) UNIQUE NOT NULL)");
    }

    private void createShoppingCartsTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS shopping_carts (id SERIAL PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(id))");
    }

    private void createCartItemsTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart_items (id SERIAL PRIMARY KEY, cart_id INT, book_id INT, quantity INT NOT NULL, FOREIGN KEY (cart_id) REFERENCES shopping_carts(id), FOREIGN KEY (book_id) REFERENCES books(id))");
    }

}
