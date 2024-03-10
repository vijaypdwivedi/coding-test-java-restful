package com.coding.component;

import com.coding.entity.BookDetails;
import com.coding.repository.BookRepository;
import com.coding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Boolean flags to control default database operations when application starts for temporary data
    private static Boolean resetData = false; // set to true if you want to reset existing data in the database
    private static Boolean defaultDBOperations = true; //set false if do want to do any default DB operations
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if(defaultDBOperations){
            if(resetData){
                createCartItemsTable();
                createShoppingCartsTable();
                createBooksTable();
                createUsersTable();
                createCategoriesTable();
            } else {
                createCategoriesTable();
                createUsersTable();
                createBooksTable();
                createShoppingCartsTable();
                createCartItemsTable();
            }
        }
    }

    // Method to create users table and insert default admin and customer users
    private void createUsersTable() {
        // Default admin user
        String adminUser = "admin";
        String adminPassword = "admin123"; // Plain-text password
        String role = "ADMIN";

        // Encoding password
        String encodedPassword = passwordEncoder.encode(adminPassword);

        // Create users table if not exists
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL)");

        // Insert admin user if not reset data
        if (!resetData) {
            jdbcTemplate.update("INSERT INTO users(username, password, role) values(?,?,?)", adminUser,encodedPassword, role);

            // Adding default customer user
            String normalUser = "vijay";
            String password = "vijay123";
            String userRole = "USER";
            String userEncodedPassword = passwordEncoder.encode(password);
            jdbcTemplate.update("INSERT INTO users(username, password, role) values(?,?,?)", normalUser,userEncodedPassword, userRole);
        }
    }

    // Method to create categories table and insert default categories
    private void createCategoriesTable() {
        // Create categories table if not exists
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name VARCHAR(100) UNIQUE NOT NULL)");

        // Insert default categories if not reset data
        if (!resetData) {
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('biography')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Thriller')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('CookBook')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Adventure')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('History')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Horror')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Science Fiction')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Classic')");
            jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Fantasy')");
        }
    }

    // Method to create books table and insert default book
    private void createBooksTable() {
        // Create books table if not exists
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL, category_id INT, price DECIMAL(10, 2) NOT NULL, stock_quantity INT NOT NULL, FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE)");

        // Insert default book if not reset data
        if (!resetData) {
            jdbcTemplate.execute("INSERT INTO books(title, author, category_id, price, stock_quantity) values('test','test',1,10.20,20)");
            logger.info("Admin User Details: {}", bookRepository.getBookDetailsByTitle("test").toString());
        }
    }

    // Method to create shopping_carts table and insert default shopping cart
    private void createShoppingCartsTable() {
        // Create shopping_carts table if not exists
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS shopping_carts (id SERIAL PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");

        // Insert default shopping cart if not reset data
        if (!resetData) {
            jdbcTemplate.execute("INSERT INTO shopping_carts(id, user_id) values(1,1)");
        }
    }

    // Method to create cart_items table and insert default cart item
    private void createCartItemsTable() {
        // Create cart_items table if not exists
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart_items (id SERIAL PRIMARY KEY, cart_id INT, book_id INT, quantity INT NOT NULL, FOREIGN KEY (cart_id) REFERENCES shopping_carts(id) ON DELETE CASCADE, FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE)");

        // Insert default cart item if not reset data
        if (!resetData) {
            jdbcTemplate.execute("INSERT INTO cart_items(cart_id, book_id, quantity) values(1,1,2)");
        }
    }
}
