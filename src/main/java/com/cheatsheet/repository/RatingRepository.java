package com.cheatsheet.repository;

import java.sql.*;

public class RatingRepository {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/cheatsheet_db", "root", "669972855");
    }

    // ၁။ Rating အသစ် သိမ်းဆည်းရန်
    public boolean saveRating(int sheetId, int userId, int value) {
        String sql = "INSERT INTO ratings (cheatsheet_id, user_id, rating_value) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sheetId);
            ps.setInt(2, userId);
            ps.setInt(3, value);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false; // Unique Key ကြောင့် ဒုတိယအကြိမ် ထပ်ပေးရင် Insert မဝင်ဘဲ False ဖြစ်သွားမည်
        }
    }

    // ၂။ User က Rating ပေးပြီးပြီလား စစ်ဆေးရန်
    public boolean hasUserRated(int sheetId, int userId) {
        String sql = "SELECT id FROM ratings WHERE cheatsheet_id = ? AND user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sheetId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // ရှိရင် true, မရှိရင် false
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ၃။ Cheat Sheet တစ်ခုချင်းစီရဲ့ ပျမ်းမျှ Rating အမှတ်ကို တွက်ထုတ်ရန်
    public double getAverageRating(int sheetId) {
        String sql = "SELECT AVG(rating_value) as avg_rating FROM ratings WHERE cheatsheet_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sheetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating"); // ဥပမာ - 4.5
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}