package com.library.borrowmicroservice.batch;

import com.library.borrowmicroservice.model.Book;
import com.library.borrowmicroservice.model.User;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class DatabaseConnect {

    // Database Config
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_USER_URL = "jdbc:postgresql://localhost:5432/bibliotheque-user";
    static final String DB_BOOK_URL = "jdbc:postgresql://localhost:5432/bibliotheque-book";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "admin";


    public static Book getBookFromDB(String bookID) {

        Book book = new Book();

        try (Connection connection = DriverManager.getConnection(DB_BOOK_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select b.book_id, b.title from public.book b where b.book_id in (" + bookID + ")");
            while (rs.next()) {
                book.setId(rs.getLong("book_id"));
                book.setTitle(rs.getString("title"));
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Fail to load driver");
            e.printStackTrace();
        }
        return book;
    }


    public static User getUserFromDB(String userID) {

        User user = new User();
        try (Connection connection = DriverManager.getConnection(DB_USER_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select u.user_id, u.first_name, u.last_name, u.email from public.user u where u.user_id in (" + userID + ")");
            while (rs.next()){
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Fail to load driver");
            e.printStackTrace();
        }
        return user;
    }
}

