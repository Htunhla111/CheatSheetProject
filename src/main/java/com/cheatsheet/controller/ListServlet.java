package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.repository.CategoryRepositoryImpl; // 💡 ခေါင်းစဉ်နာမည်ဆွဲထုတ်ဖို့ Repo ကို Import လုပ်ရပါမယ်
import com.cheatsheet.repository.CheatsheetRepository;
import com.cheatsheet.repository.CheatsheetRepositoryImpl;

@WebServlet("/sheets")
public class ListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subIdStr = request.getParameter("subId");
        
        CheatsheetRepository repo = new CheatsheetRepositoryImpl();
        List<Cheatsheet> sheets;
        
        // 💡 Default ခေါင်းစဉ်စာသား သတ်မှတ်ပေးထားခြင်း
        String titleName = "Cheat Sheets"; 

        if (subIdStr != null && !subIdStr.isEmpty()) {
            int subId = Integer.parseInt(subIdStr);
            
            // ၁။ အောက်က Card content တွေအတွက် Cheat Sheets စာရင်းကို ဆွဲထုတ်ခြင်း
            sheets = repo.getSheetsBySubId(subId);
            
            // 💡 ၂။ အပေါ်မှာ 'Java' သို့မဟုတ် 'Python' ထင်းခနဲ ပေါ်လာစေဖို့ ဒေတာဘေ့စ်ကနေ နာမည်လှမ်းယူခြင်း
            CategoryRepositoryImpl catRepo = new CategoryRepositoryImpl();
            titleName = catRepo.getSubCategoryNameById(subId);
            
        } else {
            sheets = repo.getAllSheets();
        }

        // ⚠️ CRITICAL: list.jsp ထဲက ${pageTitle} နှင့် ${sheets} နေရာတွေမှာ သုံးဖို့ Attribute ထည့်ပေးခြင်း
        request.setAttribute("pageTitle", titleName);
        request.setAttribute("sheets", sheets);
        
        // list.jsp စာမျက်နှာဆီသို့ ဒေတာများနှင့်အတူ လမ်းကြောင်းလွှဲလိုက်ခြင်း
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}