package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.model.Comment;
import com.cheatsheet.model.User;
import com.cheatsheet.repository.CheatsheetRepository;
import com.cheatsheet.repository.CheatsheetRepositoryImpl;
import com.cheatsheet.repository.CommentRepository;
import com.cheatsheet.repository.RatingRepository; // 💡 RatingRepository ကိုပါ Import သွင်းလိုက်ပါသည်

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CheatsheetRepository sheetRepo = new CheatsheetRepositoryImpl();
    private CommentRepository commentRepo = new CommentRepository();
    private RatingRepository ratingRepo = new RatingRepository(); // 💡 Rating Repository Object ဆောက်လိုက်ပါသည်

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            // ၁။ Cheat Sheet အချက်အလက်ကို ID ဖြင့် ဆွဲထုတ်ခြင်း
            Cheatsheet sheet = sheetRepo.getSheetById(id);
            request.setAttribute("sheet", sheet);
            
            // ၂။ ဤ Cheat Sheet နှင့် သက်ဆိုင်သော Comments & Replies များအားလုံးကို ဆွဲထုတ်ခြင်း
            List<Comment> commentList = commentRepo.getCommentsBySheetId(id);
            request.setAttribute("commentList", commentList);
            
            // ----------------------------------------------------------------
            // 💡 ၃။ RATING စနစ်အတွက် DATA များ တွက်ချက်ရယူခြင်း
            // ----------------------------------------------------------------
            // (က) ဤ Cheat Sheet ၏ ပျမ်းမျှ Rating အမှတ်ကို ရှာဖွေခြင်း
            double avgRating = ratingRepo.getAverageRating(id);
            request.setAttribute("avgRating", avgRating);
            
            // (ခ) လက်ရှိ Login ဝင်ထားသော User သည် ဤ Post အား Rating ပေးပြီးပြီလား စစ်ဆေးခြင်း
            boolean hasRated = false;
            HttpSession session = request.getSession();
            User loggedInUser = (User) session.getAttribute("user");
            
            if (loggedInUser != null) {
                hasRated = ratingRepo.hasUserRated(id, loggedInUser.getId());
            }
            request.setAttribute("hasRated", hasRated); // JSP ဘက်တွင် ကြယ်ပွင့်များ ဖျောက်/ပြ လုပ်ရန် ပို့ပေးခြင်း
            // ----------------------------------------------------------------
            
            // detail.jsp စာမျက်နှာဆီသို့ Data များနှင့်တကွ Forward လုပ်ခြင်း
            request.getRequestDispatcher("detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("home"); // ID Format အမှားပါက Home စာမျက်နှာသို့ ပြန်မောင်းထုတ်မည်
        }
        System.out.print(false);
    }
}