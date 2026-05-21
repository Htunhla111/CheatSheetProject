package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/delete-user-note")
public class DeleteUserNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 🛡️ ၁။ Admin ဟုတ်မဟုတ် အရင်စစ်ဆေးမယ် (လုံခြုံရေးအတွက်ပါ)
        HttpSession session = request.getSession();
        com.cheatsheet.model.User loggedInUser = (com.cheatsheet.model.User) session.getAttribute("user");
        
        if (loggedInUser == null || !loggedInUser.getRole().equalsIgnoreCase("Admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 🆔 ၂။ JSP Table ဘက်က ပါးလိုက်တဲ့ 'id' (Note ID) ကို ဖတ်မယ်
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int noteId = Integer.parseInt(idStr);
                
                // ⚙️ ၃။ Repository ကို လှမ်းခေါ်ပြီး DB ထဲကနေ ဖျက်ခိုင်းမယ်
                NoteRepositoryImpl noteRepo = new NoteRepositoryImpl();
                boolean isDeleted = noteRepo.deleteNoteById(noteId);
                
                if (isDeleted) {
                    session.setAttribute("succMsg", "User note deleted successfully!");
                } else {
                    session.setAttribute("errorMsg", "Failed to delete user note.");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMsg", "Server error: " + e.getMessage());
            }
        }
        
        // 🔄 ၄။ ဖျက်ပြီးရင် ဒီ Admin ရဲ့ User Notes စာမျက်နှာဆီကိုပဲ အလိုအလျောက် Refresh ပြန်လုပ်ခိုင်းမယ်
        response.sendRedirect(request.getContextPath() + "/manage-notes");
    }
}