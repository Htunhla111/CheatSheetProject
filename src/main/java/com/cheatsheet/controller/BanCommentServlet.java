package com.cheatsheet.controller;

import com.cheatsheet.repository.CommentRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cheatsheet.model.User;

@WebServlet("/ban-comment")
public class BanCommentServlet extends HttpServlet {
    private CommentRepository commentRepository = new CommentRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        // Admin ဖြစ်မှ ပေးလုပ်မယ်
        if (loggedInUser == null || !"Admin".equals(loggedInUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int sheetId = Integer.parseInt(request.getParameter("sheetId"));
        String reason = request.getParameter("reason");

        if (reason == null || reason.trim().isEmpty()) {
            reason = "Spam သို့မဟုတ် စည်းကမ်းမလိုက်နာသော ကွန်မန့်ဖြစ်ခြင်း။";
        }

        // DB ထဲမှာ သွားပြီး Status ကို BANNED ပြောင်းမယ်
        commentRepository.banComment(commentId, reason.trim());

        // ပြီးရင် Detail စာမျက်နှာဆီ ပြန်မောင်းမယ်
        response.sendRedirect("detail?id=" + sheetId);
    }
}