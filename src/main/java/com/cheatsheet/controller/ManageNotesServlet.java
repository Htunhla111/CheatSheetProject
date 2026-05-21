package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.Note;
import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/manage-notes")
public class ManageNotesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // 1. Session ထဲမှာ User Object အလိုက် သိမ်းထားခဲ့ရင် လှမ်းယူမယ် (ပုံထဲက Model အတိုင်း)
        com.cheatsheet.model.User loggedInUser = (com.cheatsheet.model.User) session.getAttribute("user");
        
        String role = null;
        if (loggedInUser != null) {
            role = loggedInUser.getRole(); // User Object ရှိရင် ၎င်းထဲက role ကို ယူမယ်
        } else {
            // အကယ်၍ Login စဉ်က စာသားသီးသန့်ပဲ သိမ်းခဲ့ရင် ဒါနဲ့ ဖတ်မယ်
            role = (String) session.getAttribute("role");
        }
        
        // 🛡️ Admin လုံခြုံရေး စစ်ဆေးခြင်း
        // Role မရှိရင် သို့မဟုတ် Admin မဟုတ်ရင် ခိုးဝင်လို့မရအောင် login.jsp ဆီ စနစ်တကျ မောင်းထုတ်မယ်
        if (role == null || !role.equalsIgnoreCase("Admin")) {
            // ✅ ပြင်ဆင်ချက်: 404 Not Found မဖြစ်အောင် Context Path ပါးပြီး တိုက်ရိုက်ခေါ်ခိုင်းလိုက်ပါတယ်
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 2. Repository ထဲကနေ Note အားလုံးကို ဆွဲထုတ်ပြီး JSP ဆီ ပါးမယ်
        NoteRepositoryImpl noteRepo = new NoteRepositoryImpl();
        List<Note> allNotes = noteRepo.getAllUsersNotesWithDetails();
        
        request.setAttribute("allNotes", allNotes);
        
        // ✅ ပြင်ဆင်ချက်: WebContent/webapp အောက်တည့်တည့်က manage-notes.jsp ဆီကို Forward လှမ်းလုပ်ပေးပါတယ်
        request.getRequestDispatcher("/manage-notes.jsp").forward(request, response);
    }
}