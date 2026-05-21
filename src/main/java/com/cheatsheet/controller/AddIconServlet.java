package com.cheatsheet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Icon;
import com.cheatsheet.repository.IconRepository;
import com.cheatsheet.repository.IconRepositoryImpl;

@WebServlet("/add-icon")
public class AddIconServlet extends HttpServlet {
    private IconRepository iconRepo = new IconRepositoryImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("displayName");
        String iconClass = request.getParameter("iconClass"); // ဥပမာ- fab fa-angular
        
        Icon newIcon = new Icon();
        newIcon.setDisplayName(name);
        newIcon.setIconClass(iconClass);
        
        iconRepo.saveIcon(newIcon);
        
        // သိမ်းပြီးရင် Category ဆောက်တဲ့ page ကို ပြန်လွှတ်မယ်
        response.sendRedirect("manage-categories"); 
    }
}
