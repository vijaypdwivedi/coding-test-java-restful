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

    private static Boolean resetData = false; // set to true if you want to rest existing data in database
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
//        if(resetData){
//            createCartItemsTable();
//            createShoppingCartsTable();
//            createBooksTable();
//            createUsersTable();
//            createCategoriesTable();
//        }else{
//            createCategoriesTable();
//            createUsersTable();
//            createBooksTable();
//            createShoppingCartsTable();
//            createCartItemsTable();
//        }

    }

    private void createUsersTable() {
        String username = "admin";
        String password = "admin123"; // Plain-text password
        String role = "ADMIN";

        String encodedPassword = passwordEncoder.encode(password);
        if(resetData){
            jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        }else{
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL)");
            jdbcTemplate.update("INSERT INTO users(username, password, role) values(?,?,?)", username,encodedPassword, role);
            logger.info("Admin User Details: {}", userRepository.findByUsername(username).toString());
        }

    }

   private void createCategoriesTable() {
       if(resetData){
           jdbcTemplate.execute("DROP TABLE IF EXISTS categories");
        }else {
           jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name VARCHAR(100) UNIQUE NOT NULL)");
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

    private void createBooksTable() {
        if(resetData){
            jdbcTemplate.execute("DROP TABLE IF EXISTS books");
        }else {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL, category_id INT, price DECIMAL(10, 2) NOT NULL, stock_quantity INT NOT NULL, FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE)");
            jdbcTemplate.update("INSERT INTO books(title, author, category_id, price, stock_quantity) values('test','test',1,10.20,20)");
            logger.info("Admin User Details: {}", bookRepository.getBookDetailsByTitle("test").toString());
        }

    }
    private void createShoppingCartsTable() {
        if(resetData){
            jdbcTemplate.execute("DROP TABLE IF EXISTS shopping_carts");
        }else {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS shopping_carts (id SERIAL PRIMARY KEY, user_id INT, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");
            jdbcTemplate.update("INSERT INTO shopping_carts(id, user_id) values(1,1)");
        }

    }

    private void createCartItemsTable() {
        if(resetData){
            jdbcTemplate.execute("DROP TABLE IF EXISTS cart_items");
        }else {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart_items (id SERIAL PRIMARY KEY, cart_id INT, book_id INT, quantity INT NOT NULL, FOREIGN KEY (cart_id) REFERENCES shopping_carts(id) ON DELETE CASCADE, FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE)");
            jdbcTemplate.update("INSERT INTO cart_items(cart_id, book_id, quantity) values(1,1,2)");
        }
    }
}
