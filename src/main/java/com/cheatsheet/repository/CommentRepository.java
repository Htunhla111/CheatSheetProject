package com.cheatsheet.repository;

import com.cheatsheet.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    // Database Connection ရယူရန် (မင်းရဲ့ စက်ထဲက MySQL Password အမှန်ကို ဤနေရာတွင် အစားထိုးပါ)
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // တကယ်လို့ password မပေးထားရင် "" ဟု ထားခဲ့ပါ
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/cheatsheet_db", "root", "669972855");
    }

    // ၁။ Comment သို့မဟုတ် Reply အသစ်တစ်ခု သိမ်းဆည်းရန်
    public boolean saveComment(Comment comment) {
        String sql = "INSERT INTO comments (cheatsheet_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, comment.getCheatSheetId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());
            
            // Parent Comment Id က Null ဖြစ်နိုင်ခြေ ရှိ၍ စနစ်တကျ စစ်ဆေးပြီး ထည့်ရပါမည်
            if (comment.getParentCommentId() != null) {
                ps.setInt(4, comment.getParentCommentId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ၂။ သက်ဆိုင်ရာ Cheat Sheet အလိုက် Comments & Replies အားလုံးကို ဆွဲထုတ်ရန် (Status နှင့် Ban Reason ပါဝင်သည်)
    public List<Comment> getCommentsBySheetId(int sheetId) {
        List<Comment> list = new ArrayList<>();
        
        // Users Table နှင့် JOIN တွဲပြီး ရေးသားသူ နာမည်၊ Role၊ ကွန်မန့် Status နှင့် Ban Reason များကိုပါ တစ်ခါတည်း ယူထားပါသည်
        String sql = "SELECT c.*, u.username, u.role FROM comments c " +
                     "JOIN users u ON c.user_id = u.id " +
                     "WHERE c.cheatsheet_id = ? " +
                     "ORDER BY c.created_at ASC";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, sheetId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setCheatSheetId(rs.getInt("cheatsheet_id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setContent(rs.getString("content"));
                    
                    // parent_comment_id က NULL ဖြစ်ပါက java ထဲတွင် 0 မဖြစ်သွားစေရန် စနစ်တကျ ရယူခြင်း
                    int parentId = rs.getInt("parent_comment_id");
                    comment.setParentCommentId(rs.wasNull() ? null : parentId);
                    
                    comment.setCreatedAt(rs.getTimestamp("created_at"));
                    comment.setUsername(rs.getString("username"));
                    comment.setUserRole(rs.getString("role"));
                    
                    // 💡 အသစ်ထပ်တိုးလိုက်သော Status နှင့် Ban Reason များကို DB မှ ဆွဲထုတ်ခြင်း
                    comment.setStatus(rs.getString("status"));
                    comment.setBanReason(rs.getString("ban_reason"));
                    
                    list.add(comment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 💡 ၃။ Admin မှ ကွန်မန့်တစ်ခုအား အကြောင်းပြချက်ဖြင့် ပိတ်ပင်ရန် (Ban Function)
    public boolean banComment(int commentId, String reason) {
        String sql = "UPDATE comments SET status = 'BANNED', ban_reason = ? WHERE id = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, reason);
            ps.setInt(2, commentId);
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}