package com.library.bookmicroservice.services.util;

import com.library.bookmicroservice.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDabataseConnect {

    // Database Config
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_USER_URL = "jdbc:postgresql://localhost:5432/bibliotheque-user";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "admin";

    public User getUserFromDB(String userID) {

        User user = new User();

        try (Connection connection = DriverManager.getConnection(DB_USER_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select u.email from public.user u where u.user_id in (" + userID + ")");
            while (rs.next()){
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

    public User getUserFromDBByEmail(String email) {

        User user = new User();

        try (Connection connection = DriverManager.getConnection(DB_USER_URL, USER, PASS)) {
            Class.forName(JDBC_DRIVER);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select u.user_id from public.user u where u.email in ('" + email + "')");
            while (rs.next()){
                user.setId(rs.getLong("user_id"));
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
