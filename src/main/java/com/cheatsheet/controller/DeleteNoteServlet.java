package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/delete-note")
public class DeleteNoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🎯 JSP က လာမယ့် noteId ကို ဖတ်ပါတယ်
        String idStr = request.getParameter("noteId");
        HttpSession session = request.getSession();

        try {
            if (idStr != null && !idStr.isEmpty()) {

                int id = Integer.parseInt(idStr);
                NoteRepositoryImpl repo = new NoteRepositoryImpl();
                boolean isDeleted = false;

                // 🛡️ Login ဝင်ထားတဲ့ User ရဲ့ Role ကို အရင်စစ်မယ်
                String userRole = (String) session.getAttribute("userRole"); 

                // 👑 ၁။ အကယ်၍ Admin ဖြစ်ခဲ့ရင် Note ID သက်သက်နဲ့ပဲ တိုက်ရိုက်ဖျက်မယ်
                if (userRole != null && userRole.equalsIgnoreCase("Admin")) {
                    isDeleted = repo.deleteNoteById(id); 
                } 
                // 👤 ၂။ သာမန် User မိမိကိုယ်တိုင် ဆောက်ထားတဲ့ Note ဆိုရင်
                else {
                    // 💡 စိတ်အချရဆုံးဖြစ်အောင် Object အနေနဲ့ အရင်ထုတ်ပြီးမှ အောက်ကအတိုင်း Type Cast စစ်ပါမယ်
                    Object userObj = session.getAttribute("user"); // Login တုန်းက session.setAttribute("user", user) လို့ ပေးခဲ့ရင် ဒါကို သုံးပါ
                    Object userIdObj = session.getAttribute("userId"); // သီးသန့် userId လို့ ပေးခဲ့ရင် ဒါကို သုံးပါ

                    int uId = 0;
                    
                    if (userIdObj != null) {
                        uId = Integer.parseInt(userIdObj.toString()); // String ဖြစ်နေရင်လည်း ကိစ္စမရှိအောင် toString() သုံးလိုက်ပါတယ်
                    } else if (userObj != null) {
                        // တကယ်လို့ မင်းရဲ့ Login စနစ်က User Object သုံးထားရင် ဤသို့ ယူနိုင်ပါတယ်
                        com.cheatsheet.model.User user = (com.cheatsheet.model.User) userObj;
                        uId = user.getId();
                    }

                    // ⚙️ User ID ရပြီဆိုမှ Repository ရဲ့ deleteNote(userId, noteId) ကို ခေါ်မယ်
                    if (uId > 0) {
                        isDeleted = repo.deleteNote(uId, id);
                    }
                }

                // 🔄 ၃။ ရလဒ် စစ်ဆေးခြင်း
                if (isDeleted) {
                    session.setAttribute("succMsg", "Note removed successfully!");
                } else {
                    session.setAttribute("errorMsg", "Delete failed. You don't have permission to delete this note.");
                }

            } else {
                session.setAttribute("errorMsg", "Invalid ID provided."); // 👈
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "Server error: " + e.getMessage());
        }
        
        response.sendRedirect("my-notes");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}