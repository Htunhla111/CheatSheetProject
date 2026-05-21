package com.cheatsheet.controller;

import com.cheatsheet.repository.RatingRepository;
import com.cheatsheet.repository.NotificationRepository; // 💡 NotificationRepository ကို Import သွင်းလိုက်ပါသည်
import com.cheatsheet.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/add-rating")
public class AddRatingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private RatingRepository ratingRepo = new RatingRepository();
    private NotificationRepository notiRepo = new NotificationRepository(); // 💡 Noti Object ဆောက်လိုက်ပါသည်

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // မြန်မာစာအက္ခရာများ မပျက်စီးစေရန် UTF-8 သတ်မှတ်ခြင်း
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Login မဝင်ရသေးပါက Login Form သို့ မောင်းထုတ်ခြင်း
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int sheetId = Integer.parseInt(request.getParameter("sheetId"));
        int ratingValue = Integer.parseInt(request.getParameter("ratingValue"));

        // Rating အား DB ထဲ သိမ်းဆည်းခြင်း
        boolean isSaved = ratingRepo.saveRating(sheetId, user.getId(), ratingValue);

        // 💡 Rating ပေးတာ အောင်မြင်ခဲ့လျှင် Home တွင် ပြသမည့် Noti အား လှမ်းထည့်မည်
        if (isSaved) {
            // ----------------------------------------------------------------
            // 💡 HOME တွင် NOTI တက်စေရန်အတွက် DATA ထည့်သွင်းခြင်း
            // ----------------------------------------------------------------
            String notiMessage = user.getUsername() + " က ဤ Cheat Sheet ကို ကြယ် (" + ratingValue + ") ပွင့် သတ်မှတ်ပေးခဲ့ပါသည်။";
            notiRepo.addNotification(sheetId, notiMessage);
            // ----------------------------------------------------------------
        }

        // မူလ Detail စာမျက်နှာဆီ ပြန်ပို့ခြင်း
        response.sendRedirect("detail?id=" + sheetId);
    }
}