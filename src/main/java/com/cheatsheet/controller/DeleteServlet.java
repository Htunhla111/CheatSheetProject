package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.repository.CheatsheetRepository;
import com.cheatsheet.repository.CheatsheetRepositoryImpl;

@WebServlet("/delete-sheet")
public class DeleteServlet extends HttpServlet {
    private CheatsheetRepository sheetRepo = new CheatsheetRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            
            // ၁။ မဖျက်ခင် subCategoryId ကို အရင်လှမ်းယူထားမယ်
            Cheatsheet sheet = sheetRepo.getSheetById(id);
            
            if (sheet != null) {
                int subId = sheet.getSubCategoryId(); // ဥပမာ - Java ဆိုရင် 1
                
                // ၂။ ပြီးမှ ဖျက်မယ်
                sheetRepo.deleteSheet(id);
                
                // ၃။ Home ကို မသွားတော့ဘဲ သက်ဆိုင်ရာ List Page (Java list) ဆီ ပြန်လွှတ်မယ်
                response.sendRedirect("sheets?subId=" + subId);
                return;
            }
        }
        
        // Error တစ်ခုခုရှိရင်တော့ home ကိုပဲ လွှတ်ထားမယ်
        response.sendRedirect("home"); 
    }
}