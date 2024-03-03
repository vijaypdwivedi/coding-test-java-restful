package com.coding.component;
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
        createUsersTable();
        createCategoriesTable();
        createBooksTable();
        createShoppingCartsTable();
        createCartItemsTable();
    }

    private void createUsersTable() {
        String username = "admin";
        String password = "admin123"; // Plain-text password
        String role = "ADMIN";

        String encodedPassword = passwordEncoder.encode(password);
        //jdbcTemplate.execute("DROP TABLE users");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL)");
        jdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
        jdbcTemplate.update("INSERT INTO users(username, password, role) values(?,?,?)", username,encodedPassword, role);

        logger.info("Admin User Details: {}", userRepository.findByUsername(username).toString());
    }

   private void createCategoriesTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name VARCHAR(100) UNIQUE NOT NULL)");
        jdbcTemplate.execute("DELETE FROM categories");
        jdbcTemplate.execute("INSERT INTO categories (id, name) VALUES (1, 'biography')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Thriller')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('CookBook')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Adventure')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('History')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Horror')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Science Fiction')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Classic')");
        jdbcTemplate.execute("INSERT INTO categories (name) VALUES ('Fantasy')");
    }

    private void createBooksTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL, category_id INT, price DECIMAL(10, 2) NOT NULL, stock_quantity INT NOT NULL, FOREIGN KEY (category_id) REFERENCES categories(id))");
        jdbcTemplate.update("DELETE from books");
        //jdbcTemplate.update("INSERT INTO books(title, author, category_id, price, stock_quantity) values('test','test',1,10.20,20)");
        //logger.info("Admin User Details: {}", bookRepository.getBookDetailsByTitle("test").toString());
    }
    private void createShoppingCartsTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS shopping_carts (id SERIAL PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(id))");
        //jdbcTemplate.update("DELETE from shopping_carts");
        //jdbcTemplate.update("INSERT INTO shopping_carts(id, user_id) values(1,1)");
    }

    private void createCartItemsTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart_items (id SERIAL PRIMARY KEY, cart_id INT, book_id INT, quantity INT NOT NULL, FOREIGN KEY (cart_id) REFERENCES shopping_carts(id), FOREIGN KEY (book_id) REFERENCES books(id))");
        //jdbcTemplate.update("DELETE from cart_items");
        // jdbcTemplate.update("INSERT INTO cart_items(cart_id, book_id, quantity) values(1,1,2)");


    }
}
