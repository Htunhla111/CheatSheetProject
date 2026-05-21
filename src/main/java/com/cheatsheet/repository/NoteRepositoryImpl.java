package com.cheatsheet.repository;

import java.sql.*;
import java.util.*;
import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.model.Note;
import com.cheatsheet.model.SubCategory;

public class NoteRepositoryImpl {

    /**
     * Cheat Sheet အသစ်ကို Database ထဲ သိမ်းဆည်းရန်
     */
    public boolean saveSheet(Cheatsheet sheet) {
        String sql = "INSERT INTO cheatsheets (title, content, category_id, user_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, sheet.getTitle());
            pst.setString(2, sheet.getContent());
            pst.setInt(3, sheet.getCategoryId());
            pst.setInt(4, sheet.getUserId());
            
            int rows = pst.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 💡 Detail Page မှာ အသုံးပြုရန်- 
     * User က ရေးပြီးသား Note ရှိရင် ပြန်ဆွဲထုတ်ပြသပေးမည့် Method
     */
    public Note getNoteByUserAndSheet(int userId, int sheetId) {
        Note note = null;
        String sql = "SELECT * FROM personal_notes WHERE user_id = ? AND cheatsheet_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, userId);
            pst.setInt(2, sheetId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setCheatsheetId(rs.getInt("cheatsheet_id"));
                    note.setPersonalRemark(rs.getString("personal_remark"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }

    /**
     * ✅ အချောသတ်ပြီးသား saveNote:
     * Servlet ဘက်က တောင်းဆိုတဲ့ နာမည်အတိုင်း ပြောင်းလဲထားပြီး၊ Unique Key တိုက်မိရင် အလိုအလျောက် UPDATE လုပ်ပေးမည့် စနစ်ဖြစ်ပါတယ်
     */
    public boolean saveNote(int userId, int cheatsheetId, String personalRemark) {
        String sql = "INSERT INTO personal_notes (user_id, cheatsheet_id, personal_remark) VALUES (?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE personal_remark = ?";
                   
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            pst.setInt(2, cheatsheetId);
            pst.setString(3, personalRemark);
            pst.setString(4, personalRemark); 
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 📖 Read: မိမိ (User) ပိုင်ဆိုင်သော ကိုယ်ပိုင် Note အားလုံးကို Dashboard တွင် ပြသရန်
     */
    public List<Note> getNotesByUserId(int userId) {
        List<Note> list = new ArrayList<>();
        String sql = "SELECT n.*, c.title AS sheet_title FROM personal_notes n " +
                     "JOIN cheatsheets c ON n.cheatsheet_id = c.id WHERE n.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setUserId(rs.getInt("user_id"));
                    note.setCheatsheetId(rs.getInt("cheatsheet_id"));
                    note.setPersonalRemark(rs.getString("personal_remark"));
                    note.setSheetTitle(rs.getString("sheet_title"));
                    list.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 📝 Update: Note ကို သီးသန့် ပြန်ပြင်ရန်
     */
    public boolean updateNote(int noteId, int userId, String newRemark) {
        String sql = "UPDATE personal_notes SET personal_remark = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newRemark);
            pst.setInt(2, noteId);
            pst.setInt(3, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ❌ Delete: Note ကို User ID နှင့် Cheatsheet ID အလိုက် ကွက်တိ ဖျက်ရန်
     * (Servlet ဘက်က လှမ်းပို့လိုက်တဲ့ အစီအစဉ်အတိုင်း ကိုက်ညီအောင် SQL ကို ညှိပေးထားပါတယ်)
     */
    public boolean deleteNote(int userId, int cheatsheetId) {
        String sql = "DELETE FROM personal_notes WHERE user_id = ? AND cheatsheet_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.setInt(2, cheatsheetId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 💡 User က Note ကို သိမ်းပြီးပြီလား စစ်ပေးမည့် logic (တစ်ကြိမ်တည်းပဲ ချန်လှပ်ထားပါသည်)
     */
    public boolean isNoteSavedByUser(int userId, int sheetId) {
        String sql = "SELECT COUNT(*) FROM personal_notes WHERE user_id = ? AND cheatsheet_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.setInt(2, sheetId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- SubCategory Management Methods ---
    
    public List<Cheatsheet> getSheetsByCategoryId(int categoryId) {
        List<Cheatsheet> list = new ArrayList<>();
        String sql = "SELECT * FROM cheatsheets WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, categoryId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cheatsheet s = new Cheatsheet();
                s.setId(rs.getInt("id"));
                s.setTitle(rs.getString("title"));
                s.setContent(rs.getString("content"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Cheatsheet> searchSheets(String query) {
        List<Cheatsheet> list = new ArrayList<>();
        String sql = "SELECT * FROM cheatsheets WHERE LOWER(title) LIKE LOWER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + query + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cheatsheet s = new Cheatsheet();
                s.setId(rs.getInt("id"));
                s.setTitle(rs.getString("title"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addSubCategory(String name) {
        String sql = "INSERT INTO sub_categories (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean updateSubCategory(int id, String name) {
        String sql = "UPDATE sub_categories SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean deleteSubCategory(int id) {
        String sql = "DELETE FROM sub_categories WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public String getSubCategoryNameById(int id) {
        String sql = "SELECT name FROM sub_categories WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) { e.printStackTrace(); }
        return "";
    }
    
    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> subList = new ArrayList<>();
        String sql = "SELECT id, name, icon FROM sub_categories"; 
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                SubCategory sub = new SubCategory();
                sub.setId(rs.getInt("id"));
                sub.setName(rs.getString("name"));
                sub.setIcon(rs.getString("icon"));
                subList.add(sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subList;
    }
    
 // NoteRepositoryImpl.java ထဲတွင် ထည့်သွင်းရန်
    public List<Note> getAllUsersNotesWithDetails() {
        List<Note> list = new ArrayList<>();
        
        // ✅ u.name နေရာမှာ u.username လို့ ပြင်ထားပါတယ် (Error ကင်းသွားပါပြီ)
        // 💡 JOIN စစ်တဲ့နေရာမှာ p.cheatsheet_id သို့မဟုတ် p.sheet_id မင်းရဲ့ DB Column အတိုင်း မှန်အောင် စစ်ပေးပါ
        String sql = "SELECT p.*, u.username AS user_name, s.title AS sheet_title " +
                "FROM personal_notes p " +
                "JOIN users u ON p.user_id = u.id " +
                "JOIN cheatsheets s ON p.cheatsheet_id = s.id " + // 👈 အမှန်ပြင်ဆင်ချက်
                "ORDER BY p.id DESC";
                     
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setUserId(rs.getInt("user_id"));
                
                // 💡 မင်းရဲ့ personal_notes table ထဲက နိုင်ငံခြားသော့ (Foreign Key) အမည်အတိုင်း ယူရပါမယ်
                note.setCheatsheetId(rs.getInt("cheatsheet_id")); 
                note.setPersonalRemark(rs.getString("personal_remark"));
                
                // ✅ u.username ကနေ ထွက်လာတဲ့ Alias Name "user_name" ကို အမှန်အတိုင်း ဆွဲထုတ်ခြင်း
                note.setUserName(rs.getString("user_name"));
                note.setSheetTitle(rs.getString("sheet_title"));
                
                list.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
 // ✅ Admin က Note ID တစ်ခုတည်းဖြင့် တိုက်ရိုက်ဖျက်ရန် JDBC ကုဒ်
    public boolean deleteNoteById(int id) {
        boolean f = false;
        try {
            // DBConnection သုံးပြီး တိုက်ရိုက်ဖျက်ချလိုက်တဲ့ SQL Query ပါ
            String sql = "DELETE FROM personal_notes WHERE id = ?";
            Connection conn = DBConnection.getConnection(); 
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            
            int i = pst.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }    }