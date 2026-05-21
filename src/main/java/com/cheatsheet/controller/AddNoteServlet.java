package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/add-note")
public class AddNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String sheetIdParam = request.getParameter("sheetId");

        if (userId != null && sheetIdParam != null) {
            int sheetId = Integer.parseInt(sheetIdParam);
            NoteRepositoryImpl noteRepo = new NoteRepositoryImpl();
            
            // 💡 မျဉ်းတားကုဒ်အသစ်: Database မှာ ဒီ User က ဒီ Note ကို သိမ်းပြီးသားလား အရင်စစ်မယ်
            boolean isAlreadySaved = noteRepo.isNoteSavedByUser(userId, sheetId);
            
            if (isAlreadySaved) {
                // ရှိပြီးသားဆိုရင် Error တက်ခွင့်မပေးဘဲ Message လေးနဲ့အတူ List Page ဆီ ပြန်လွှတ်မယ်
                session.setAttribute("errorMsg", "This note have already exist!!");
                response.sendRedirect("my-notes");
            } else {
                // မရှိသေးမှသာ အသစ် ထည့်ခွင့်ပြုမယ်
                boolean isSaved = noteRepo.saveNote(userId, sheetId, sheetIdParam);
                if (isSaved) {
                    session.setAttribute("succMsg", "Note save successfully.");
                    response.sendRedirect("my-notes"); 
                } else {
                    response.sendRedirect("home?error=SaveFailed");
                }
            }
        } else {
            response.sendRedirect("login.jsp"); 
        }
    }
}