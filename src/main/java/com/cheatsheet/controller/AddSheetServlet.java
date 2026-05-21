package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.model.User;
import com.cheatsheet.repository.CategoryRepositoryImpl;
import com.cheatsheet.repository.CheatsheetRepositoryImpl;

@WebServlet("/add-sheet")
public class AddSheetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subIdParam = request.getParameter("subId");
        String titleName = "Cheat Sheet"; // Default ခေါင်းစဉ်
        
        if (subIdParam != null && !subIdParam.isEmpty()) {
            try {
                int subId = Integer.parseInt(subIdParam);
                
                // 💡 sub_categories table ကနေ နာမည် (Java, Python) ကို လှမ်းယူခြင်း
                CategoryRepositoryImpl subCatRepo = new CategoryRepositoryImpl();
                titleName = subCatRepo.getSubCategoryNameById(subId);
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        // JSP ဘက်မှာ ခေါင်းစဉ်ရော၊ Form ရဲ့ hidden input မှာ သုံးဖို့ subId ရော ထည့်ပေးလိုက်မယ်
        request.setAttribute("subId", subIdParam); 
        request.setAttribute("pageTitle", titleName); // 💡 JSP ထဲက ခေါင်းစဉ်မှာ ${pageTitle} ဆိုပြီး သုံးလို့ရပါပြီ
        
        request.getRequestDispatcher("add-sheet.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // ၁။ လူလည်ကျပြီး ဝင်မလာအောင် Login စစ်ခြင်း
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) { 
            response.sendRedirect("login.jsp"); 
            return; 
        }
        
        // ၂။ Form ထဲက subId ကို ဖတ်ခြင်း
        String subId = request.getParameter("subId");

        // ၃။ ဒေတာတွေကို Model Object ထဲ ထည့်ခြင်း
        Cheatsheet sheet = new Cheatsheet();
        sheet.setTitle(request.getParameter("title"));
        sheet.setContent(request.getParameter("content"));
        sheet.setUserId(user.getId());
        
        // 💡 ဖြေရှင်းချက်က ကွက်တိ ဒီနေရာလေးပါဗျာ!
        // Repository ထဲက ? ၄ ခုမြောက်နေရာမှာ 'sheet.getSubId()' ကို သုံးထားလို့
        // ဒီမှာလည်း setSubId() ရော setSubCategoryId() ပါ ၂ ခုလုံးကို တစ်ခါတည်း သတ်မှတ်ပေးလိုက်ပါတယ် (ဒါမှ Error လုံးဝ ကင်းမှာပါ)
        if(subId != null && !subId.isEmpty()) {
            int subIdInt = Integer.parseInt(subId);
            sheet.setSubCategoryId(subIdInt);            // <--- ⚠️ Repository အတွက်
            sheet.setSubCategoryId(subIdInt);    // <--- ⚠️ Model matching အတွက်
        }
        
        // ၄။ Database ထဲသို့ သွားသိမ်းခြင်း
        new CheatsheetRepositoryImpl().addSheet(sheet);
        
        // ၅။ အောင်မြင်သွားရင် သက်ဆိုင်ရာ Category sheet list ဆီ ပြန်ပို့ပေးမယ်
        response.sendRedirect("sheets?subId=" + subId);   
    }
}