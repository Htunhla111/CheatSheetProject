package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.model.Icon;
import com.cheatsheet.repository.IconRepositoryImpl;
import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String catIdParam = request.getParameter("id");
        
        if (catIdParam != null) {
            int catId = Integer.parseInt(catIdParam);
            NoteRepositoryImpl repo = new NoteRepositoryImpl();
            IconRepositoryImpl iconRepo = new IconRepositoryImpl();
           
         
            List<Icon> iconList = iconRepo.getAllIcons(); 
            request.setAttribute("icons", iconList);
            
   //         request.getRequestDispatcher("add-category.jsp").forward(request, response);
            // Category အလိုက် sheet list ကို ယူမယ်
            List<Cheatsheet> sheets = repo.getSheetsByCategoryId(catId);
            
            request.setAttribute("sheets", sheets);
            request.getRequestDispatcher("category-sheets.jsp").forward(request, response);
        } else {
            response.sendRedirect("home");
        }
    }
}