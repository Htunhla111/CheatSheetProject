package com.cheatsheet.repository; // မင်းရဲ့ package name သေချာပြန်စစ်ပါ

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheatsheet.model.Icon;
import com.cheatsheet.config.DBConnection; // ဒါထည့်မှ နီတာပျောက်မှာပါ

public class IconRepositoryImpl implements IconRepository {

    @Override
    public List<Icon> getAllIcons() {
        List<Icon> icons = new ArrayList<>();
        String query = "SELECT * FROM icons"; 

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Icon icon = new Icon();
                icon.setId(rs.getInt("id"));
                icon.setIconClass(rs.getString("icon_class"));
                icon.setDisplayName(rs.getString("display_name"));
                icons.add(icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return icons;
    }
    
    public void saveIcon(Icon icon) {
        String sql = "INSERT INTO icons (display_name, icon_class) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, icon.getDisplayName());
            pst.setString(2, icon.getIconClass());
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}