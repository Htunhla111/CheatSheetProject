package com.cheatsheet.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.cheatsheet.model.UserNote;

public class NoteRepository {
    
    // 🛠️ အရေးကြီး: မင်းတို့ Project ရဲ့ database နာမည်၊ username နဲ့ password တို့ကို မှန်အောင် ပြင်ပေးပါ
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/CheatSheet_db?useSSL=false&allowPublicKeyRetrieval=true", "root", "669972855");
    }

    // Note အသစ်ထည့်သွင်းခြင်း
    public boolean saveNote(UserNote note) {
        String query = "INSERT INTO user_notes (user_id, title, content) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, note.getUserId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getContent());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // သက်ဆိုင်ရာ User အလိုက် ရေးသားထားသော Note စာရင်း ပြန်ထုတ်ခြင်း
    public List<UserNote> getNotesByUserId(int userId) {
        List<UserNote> notes = new ArrayList<>();
        String query = "SELECT * FROM user_notes WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserNote note = new UserNote();
                    note.setId(rs.getInt("id"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setCreatedAt(rs.getTimestamp("created_at"));
                    notes.add(note);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
}