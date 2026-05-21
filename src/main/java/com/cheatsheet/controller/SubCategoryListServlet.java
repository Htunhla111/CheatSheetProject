package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.SubCategory;
import com.cheatsheet.repository.SubCategoryRepository;

@WebServlet("/sub-categories")
public class SubCategoryListServlet extends HttpServlet {
    private SubCategoryRepository subRepo = new SubCategoryRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // ၁။ URL ကနေ catId ကို ယူမယ်
            String catIdStr = request.getParameter("catId");
            
            if (catIdStr != null && !catIdStr.isEmpty()) {
                int catId = Integer.parseInt(catIdStr);
                
                // ၂။ ဒီ Category အောက်မှာရှိတဲ့ Sub-categories စာရင်းကို ယူမယ်
                List<SubCategory> subList = subRepo.getSubCategoriesByCategoryId(catId);
                
                // ၃။ (DYNAMIC အတွက် အဓိကအချက်) 
                // list ထဲက ပထမဆုံးတစ်ခုရဲ့ အချက်အလက်ကို 'sub' ဆိုတဲ့ နာမည်နဲ့ ပို့ပေးမယ်
                if (!subList.isEmpty()) {
                    request.setAttribute("sub", subList.get(0)); 
                }
                
                // ၄။ အရင်အတိုင်း list ကိုလည်း ပို့မယ်
                request.setAttribute("subList", subList);
                
                request.getRequestDispatcher("sub-list.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}