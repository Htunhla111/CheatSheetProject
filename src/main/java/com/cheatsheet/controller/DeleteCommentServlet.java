package com.cheatsheet.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 💡 404 မတက်အောင် JSP ဘက်က Form Action နာမည်နဲ့ ကွက်တိ Mapping ပေးထားပါတယ်
@WebServlet("/delete-comment")
public class DeleteCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 🛡️ ၁။ Admin ဟုတ်မဟုတ် အရင်စစ်ဆေးမယ်
        HttpSession session = request.getSession();
        com.cheatsheet.model.User loggedInUser = (com.cheatsheet.model.User) session.getAttribute("user");
        
        if (loggedInUser == null || !loggedInUser.getRole().equalsIgnoreCase("Admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 🆔 ၂။ JSP Modal ဘက်က Hidden Field နဲ့ ပါးလိုက်တဲ့ ID တွေကို ဖတ်မယ်
        String commentIdStr = request.getParameter("commentId");
        String sheetIdStr = request.getParameter("sheetId");

        if (commentIdStr != null && !commentIdStr.isEmpty()) {
            int commentId = Integer.parseInt(commentIdStr);
            
            // ⚙️ ၃။ JDBC သုံးပြီး Database ထဲက Comment ကို တိုက်ရိုက် ဖျက်ချမယ်
            // (Repository ထဲ သီးသန့်မဆောက်ချင်ရင် Servlet ထဲမှာပဲ တစ်ခါတည်း ရှင်းအောင် ရေးပေးထားပါတယ်ဗျာ)
            String sql = "DELETE FROM comments WHERE id = ?";
            
            try (Connection conn = com.cheatsheet.config.DBConnection.getConnection(); // မင်းရဲ့ DBConnection package အတိုင်း ပြင်ပေးပါ
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                
                pst.setInt(1, commentId);
                int rows = pst.executeUpdate();
                
                if (rows > 0) {
                    session.setAttribute("succMsg", "Comment deleted successfully!");
                } else {
                    session.setAttribute("errorMsg", "Failed to delete comment.");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("errorMsg", "Database error: " + e.getMessage());
            }
        }

        // 🔄 ၄။ ဖျက်ပြီးရင် မူလ ကြည့်နေလက်စ Detail စာမျက်နှာဆီကိုပဲ ID ပြန်ပါးပြီး Redirect လှည့်ခိုင်းမယ်
        response.sendRedirect(request.getContextPath() + "/detail?id=" + sheetIdStr);
    }
}