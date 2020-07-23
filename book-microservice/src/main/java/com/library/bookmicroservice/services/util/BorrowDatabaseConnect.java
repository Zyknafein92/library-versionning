package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.Borrow;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class BorrowDatabaseConnect {

    // Database Config
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_BORROW_URL = "jdbc:postgresql://localhost:5432/bibliotheque-borrow";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "admin";


    public static Borrow getBorrowFromDBByBookID(String bookID) {

        Borrow borrow = new Borrow();

        try (Connection connection = DriverManager.getConnection(DB_BORROW_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select t.borrow_id, t.book_id, t.user_id, t.date_end, t.date_extend, t.is_extend from public.borrow t where t.book_id = '" + bookID + "' ");
            while (rs.next()) {
                borrow.setId(rs.getLong("borrow_id"));
                borrow.setBookID(rs.getString("book_id"));
                borrow.setUserID(rs.getString("user_id"));
                borrow.setDateEnd(rs.getDate("date_end"));
                borrow.setDateExtend(rs.getDate("date_extend"));
                borrow.setIsExtend(rs.getBoolean("is_extend"));
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Fail to load driver");
            e.printStackTrace();
        }
        return borrow;
    }

    public static Borrow getBorrowFromDBByUserID(Long userID) {

        Borrow borrow = new Borrow();

        try (Connection connection = DriverManager.getConnection(DB_BORROW_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select t.borrow_id, t.book_id, t.user_id, t.date_end, t.date_extend, t.is_extend from public.borrow t where t.user_id =  :userID ");
            while (rs.next()) {
                borrow.setId(rs.getLong("borrow_id"));
                borrow.setBookID(rs.getString("book_id"));
                borrow.setUserID(rs.getString("user_id"));
                borrow.setDateEnd(rs.getDate("date_end"));
                borrow.setDateExtend(rs.getDate("date_extend"));
                borrow.setIsExtend(rs.getBoolean("is_extend"));
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Fail to load driver");
            e.printStackTrace();
        }
        return borrow;
    }
}

