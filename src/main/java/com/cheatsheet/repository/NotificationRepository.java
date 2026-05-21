package com.cheatsheet.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 💡 ဤနေရာတွင် မင်းရဲ့ MySQL Password အမှန်ကို ပြောင်းရန်မမေ့ပါနှင့်
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/cheatsheet_db", "root", "669972855");
    }

    // ၁။ Noti အသစ် လှမ်းထည့်ရန် မက်သဒ်
    public void addNotification(int sheetId, String message) {
        String sql = "INSERT INTO notifications (cheatsheet_id, message) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sheetId);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ၂။ Noti အားလုံးကို နောက်ဆုံးတင်တာကနေ စပြီး ဆွဲထုတ်ရန်
    public List<Map<String, Object>> getAllNotifications() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC LIMIT 10"; // နောက်ဆုံး ၁၀ ခုပဲပြမည်
        try (Connection conn = getConnection(); 
             Statement smt = conn.createStatement(); 
             ResultSet rs = smt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> noti = new HashMap<>();
                noti.put("id", rs.getInt("id"));
                noti.put("sheetId", rs.getInt("cheatsheet_id"));
                noti.put("message", rs.getString("message"));
                noti.put("isRead", rs.getInt("is_read"));
                noti.put("createdAt", rs.getTimestamp("created_at"));
                list.add(noti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ၃။ မဖတ်ရသေးသော Noti အရေအတွက်ကို တွက်ရန် (Bell Icon ပေါ်မှာ ဂဏန်းအနီလေးပြဖို့)
    public int getUnreadCount() {
        String sql = "SELECT COUNT(*) FROM notifications WHERE is_read = 0";
        try (Connection conn = getConnection(); Statement smt = conn.createStatement(); ResultSet rs = smt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}