package com.cheatsheet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.repository.SubCategoryRepository;

@WebServlet("/add-sub-category")
public class AddSubCategoryServlet extends HttpServlet {
    private SubCategoryRepository subRepo = new SubCategoryRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Form က data တွေကို ယူမယ်
        String name = request.getParameter("name");
        String icon = request.getParameter("icon");
        int catId = Integer.parseInt(request.getParameter("catId"));
        String catIdRaw = request.getParameter("catId");
        System.out.println("Received catId: " + catIdRaw); // Eclipse Console မှာ ကြည့်ရန်

       // int catId = Integer.parseInt(catIdRaw);
        // Database ထဲ ထည့်မယ်
        boolean success = subRepo.addSubCategory(name, icon, catId);

        if (success) {
            // အောင်မြင်ရင် sub-categories page ကို ပြန်သွားမယ်
            response.sendRedirect("sub-categories?catId=" + catId);
        } else {
            // မအောင်မြင်ရင် error ပြမယ်
            response.getWriter().println("Error adding technology.");
        }
    }
}