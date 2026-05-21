package com.cheatsheet.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // မြန်မာစာတွေ မပျက်အောင် characterEncoding ထည့်ထားပါ
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cheatsheet_db?useSSL=true&characterEncoding=utf-8", "root", "669972855");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}