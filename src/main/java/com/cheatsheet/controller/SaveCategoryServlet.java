package com.cheatsheet.controller; // မင်းရဲ့ package name အတိုင်း ပြင်ပါ

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.config.DBConnection; // မင်းရဲ့ DB Connection class ကို ညွှန်းပါ

@WebServlet("/save-category") // JSP က action name နဲ့ အတူတူ ဖြစ်ရပါမယ်
public class SaveCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ၁။ Form က ပို့လိုက်တဲ့ Data တွေကို ဖမ်းမယ်
        String categoryName = request.getParameter("categoryName");
        String iconClass = request.getParameter("iconClass");

        // ၂။ Database ထဲ သိမ်းမယ့် SQL Query
        String sql = "INSERT INTO categories (name, icon) VALUES (?, ?)";

        // ၃။ JDBC သုံးပြီး Database ထဲ သိမ်းမယ်
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, categoryName);
            pst.setString(2, iconClass);
            
            int rowCount = pst.executeUpdate();
            
            if (rowCount > 0) {
                // သိမ်းပြီးရင် Manage Categories page ကို ပြန်သွားမယ်
                response.sendRedirect("manage-categories");
            } else {
                // မအောင်မြင်ရင် Error ပြန်ပြချင်ပြနိုင်တယ်
                response.getWriter().println("Failed to save category.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        }
    }
}