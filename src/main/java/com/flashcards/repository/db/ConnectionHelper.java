package com.flashcards.repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class ConnectionHelper {

    private static final String DB_URL = "jdbc:sqlite:flashcards.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);    
    }
    
}
