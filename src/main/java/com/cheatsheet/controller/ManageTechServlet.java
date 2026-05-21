package com.cheatsheet.controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cheatsheet.model.User;
import com.cheatsheet.repository.NoteRepositoryImpl; // မင်းရဲ့ Repository နာမည်

@WebServlet("/manage-tech")
public class ManageTechServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        NoteRepositoryImpl repo = new NoteRepositoryImpl();

        if ("delete".equals(action)) {
            int id = Integer.parseInt(idStr);
            repo.deleteSubCategory(id); // Database မှ ဖျက်မည့် method
            response.sendRedirect("home"); // မူလ Home Page သို့ ပြန်လွှတ်မည်
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(idStr);
            // တည်းဖြတ်မည့် Category နာမည်ကို ယူပြီး Form သို့ ပို့ပေးရန်
            String currentName = repo.getSubCategoryNameById(id); 
            request.setAttribute("techId", id);
            request.setAttribute("techName", currentName);
        }
        
        request.getRequestDispatcher("manage-tech.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        String techName = request.getParameter("techName");
        NoteRepositoryImpl repo = new NoteRepositoryImpl();

        if (idStr == null || idStr.isEmpty()) {
            // ID မပါရင် အသစ်ထည့်မယ်
            repo.addSubCategory(techName);
        } else {
            // ID ပါရင် နာမည်အဟောင်းကို update လုပ်မယ်
            int id = Integer.parseInt(idStr);
            repo.updateSubCategory(id, techName);
        }
        response.sendRedirect("home");
    }
}