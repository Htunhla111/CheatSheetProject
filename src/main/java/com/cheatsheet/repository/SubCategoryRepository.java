package com.cheatsheet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.SubCategory; // SubCategory class ရဲ့ package လမ်းကြောင်းကို သေချာစစ်ပါ // DBConnection ရှိတဲ့နေရာကို သေချာစစ်ပါ

public class SubCategoryRepository {

    public List<SubCategory> getSubCategoriesByCategoryId(int catId) {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM sub_categories WHERE category_id = ?";
        
        // try-with-resources ကိုသုံးထားလို့ connection ကို manual ပိတ်စရာမလိုပါဘူး
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, catId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                SubCategory sub = new SubCategory();
                sub.setId(rs.getInt("id"));
                sub.setName(rs.getString("name"));
                sub.setIcon(rs.getString("icon"));
                sub.setCategoryId(rs.getInt("category_id"));
                list.add(sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSubCategory(String name, String icon, int catId) {
        // Column နာမည်တွေက Database table ထဲကအတိုင်း ဖြစ်ရပါမယ်
        String sql = "INSERT INTO sub_categories (name, icon, category_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, name);
            pst.setString(2, icon);
            pst.setInt(3, catId);
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
        } catch (Exception e) {
            // ဒီ line က Console မှာ error အနီရောင်စာသားတွေ ပြပေးမှာပါ
            e.printStackTrace(); 
            return false;
        }
    }
    
 // SubCategoryRepositoryImpl.java သို့မဟုတ် မင်းသုံးနေတဲ့ Repo ထဲတွင် ထည့်ရန်
    public String getSubCategoryNameById(int subId) {
        String subCategoryName = "Cheat Sheets"; // Default စာသား
        String sql = "SELECT name FROM sub_categories WHERE id = ?"; // မင်းရဲ့ Table column အတိုင်းပါ
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, subId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    subCategoryName = rs.getString("name"); // Database ထဲက "Java" သို့မဟုတ် "Python" ကို ယူမယ်
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategoryName;
    }
    
}