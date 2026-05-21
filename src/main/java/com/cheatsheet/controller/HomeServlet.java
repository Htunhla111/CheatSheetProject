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
import com.cheatsheet.repository.NotificationRepository; // 💡 NotificationRepository ကို Import လုပ်လိုက်ပါသည်

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Repository ကို အသုံးပြုဖို့ ကြေညာမယ်
    private CategoryRepository catRepo = new CategoryRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Database ကနေ Category list ကို ယူမယ်
        List<Category> categoryList = catRepo.getAllCategories();
        
        // 2. ရလာတဲ့ list ကို "categories" ဆိုတဲ့ နာမည်နဲ့ Request ထဲ ထည့်လိုက်မယ်
        request.setAttribute("categories", categoryList);
        
        // ----------------------------------------------------------------
        // 💡 ဖြည့်စွက်ကုဒ် - HOME စာမျက်နှာတွင် NOTI ပြသရန်အတွက် ဒေတာများကို ဆွဲထုတ်ခြင်း
        // ----------------------------------------------------------------
        NotificationRepository notiRepo = new NotificationRepository();
        
        // home.jsp ဘက်မှ ${notiList} နှင့် ${unreadNotiCount} တို့ဖြင့် ဖတ်နိုင်ရန် Request ထဲ ထည့်ပေးခြင်း
        request.setAttribute("notiList", notiRepo.getAllNotifications());
        request.setAttribute("unreadNotiCount", notiRepo.getUnreadCount());
        // ----------------------------------------------------------------
        
        // 3. home.jsp စာမျက်နှာဆီ data တွေ သယ်ပြီး ကူးသွားမယ်
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}