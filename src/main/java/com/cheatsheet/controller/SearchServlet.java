package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.repository.*;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
  //  private CheatsheetRepository sheetRepo = new CheatsheetRepositoryImpl();

 // SearchServlet ရဲ့ doGet ထဲမှာ ဒီလိုပြင်ရေးပါ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        // query မပါရင် သို့မဟုတ် အလွတ်ဖြစ်ရင် home ကို ပြန်လွှတ်မယ်
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        CheatsheetRepository repo = new CheatsheetRepositoryImpl();
        List<Cheatsheet> results = repo.searchSheets(query); // အပေါ်က method အသစ်ကို လှမ်းခေါ်တာပါ

        request.setAttribute("sheets", results);
        request.setAttribute("searchKey", query);
        
        // Results တွေကို list.jsp မှာပဲ ပြချင်ရင် ဒါကိုသုံးပါ
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }}