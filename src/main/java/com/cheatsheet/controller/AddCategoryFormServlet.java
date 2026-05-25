package com.cheatsheet.controller;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Icon;
import com.cheatsheet.repository.IconRepositoryImpl;

@WebServlet("/add-category-form") // ဒီ URL ကို Button မှာ သုံးရပါမယ်
public class AddCategoryFormServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // ၁။ Icon တွေကို Database ကနေ ဆွဲထုတ်မယ်
        IconRepositoryImpl iconRepo = new IconRepositoryImpl();
        List<Icon> iconList = iconRepo.getAllIcons();
        
        // ၂။ JSP မှာ သုံးနိုင်အောင် Request ထဲ ထည့်ပေးမယ်
       // request.setAttribute("icons", iconList);
        request.setAttribute("icons", iconList);

        // ၃။ Category ဆောက်တဲ့ JSP စာမျက်နှာဆီကို ပို့ပေးမယ်
        request.getRequestDispatcher("create-category.jsp").forward(request, response);
        System.out.print("Hello");
        
    }
    
  
}