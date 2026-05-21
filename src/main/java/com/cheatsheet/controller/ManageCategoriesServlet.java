package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Category;
import com.cheatsheet.repository.CategoryRepository;
import com.cheatsheet.repository.CategoryRepositoryImpl;

@WebServlet("/manage-categories") // 💡 Browser ကနေ လှမ်းခေါ်မည့် URL လမ်းကြောင်း
public class ManageCategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        CategoryRepository catRepo = new CategoryRepositoryImpl();
        
        // 1. Database ကနေ Item Count ပါပြီးသား Category List ကို ယူမယ်
        List<Category> categories = catRepo.getAllCategories();
        
        // 2. JSP က သုံးမယ့် "categories" ဆိုတဲ့ နာမည်နဲ့ ကွက်တိ သိမ်းပေးမယ်
        request.setAttribute("categoryList", categories);
        
        // 3. JSP ဖိုင်ဆီ ဒေတာ ပို့ပေးမယ် (ဖိုင်နာမည် အမှန်အတိုင်း ဖြစ်ရပါမယ်)
        request.getRequestDispatcher("manage-categories.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}