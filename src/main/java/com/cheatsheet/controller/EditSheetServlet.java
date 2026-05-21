package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.repository.CheatsheetRepositoryImpl;

@WebServlet("/edit-sheet")
public class EditSheetServlet extends HttpServlet {
    private CheatsheetRepositoryImpl sheetRepo = new CheatsheetRepositoryImpl();

    // Edit နှိပ်လိုက်ရင် Database က data ကို ရှာပြီး Form ဆီ ပို့ပေးတာ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Cheatsheet sheet = sheetRepo.getSheetById(id);
        
        request.setAttribute("sheet", sheet);
        request.setAttribute("categoryName", sheet.getSubCategoryName());
        request.getRequestDispatcher("edit-sheet.jsp").forward(request, response);
    }

    // Form ထဲမှာ ပြင်ပြီး Update နှိပ်လိုက်ရင် Database ထဲ သွားသိမ်းတာ
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8"); // မြန်မာစာ font မပျက်အောင် ထည့်ပေးပါ

        try {
            // 1. JSP Form ထဲက name="id" ဆိုတဲ့ field ကနေ တန်ဖိုးယူတာပါ
            String idParam = request.getParameter("id");
            String subIdParam = request.getParameter("subCategoryId"); // categoryId အစား subCategoryId သုံးပါ

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("categories"); // id မပါရင် list page ကို ပြန်ပို့မယ်
                return;
            }

            int id = Integer.parseInt(idParam);
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            Cheatsheet sheet = new Cheatsheet();
            sheet.setId(id);
            sheet.setTitle(title);
            sheet.setContent(content);
            
            // 2. subCategoryId ရှိရင် ထည့်ပေးမယ်
            if (subIdParam != null && !subIdParam.isEmpty()) {
                sheet.setSubCategoryId(Integer.parseInt(subIdParam));
            }

            // 3. Database မှာ update လုပ်မယ်
            if (sheetRepo.updateSheet(sheet)) {
                // Update အောင်မြင်ရင် detail page ကို id နဲ့တကွ ပြန်သွားမယ်
                response.sendRedirect("detail?id=" + id);
            } else {
                response.sendRedirect("edit?id=" + id + "&error=UpdateFailed");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            // ID က နံပါတ်မဟုတ်ဘဲ "null" ဖြစ်နေရင် ဒီကို ရောက်လာပါလိမ့်မယ်
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        }
    }}