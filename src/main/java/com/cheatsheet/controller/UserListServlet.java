package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.model.User;
import com.cheatsheet.repository.UserRepositoryImpl;

 // မင်းရဲ့ User Repo Path
// import com.cheatsheet.entity.User; // မင်းရဲ့ User Entity Class Path (လိုအပ်လျှင် import ပေးပါ)

@WebServlet("/userList") // 💡 Navbar က 'userList' နဲ့ ဒီနေရာက နာမည် ကွက်တိတူရပါမယ်
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        UserRepositoryImpl repo = new UserRepositoryImpl();
        
        // Database ထဲက user အားလုံးကို ဆွဲထုတ်မည့် method (မရှိသေးရင် UserRepository မှာ ဆောက်ပေးရပါမယ်)
        List<User> list = repo.getAllUsers(); 
        
        // ရလာတဲ့ list ကို request ထဲထည့်ပြီး JSP စာမျက်နှာဆီ ပို့ပေးခြင်း
        request.setAttribute("userList", list);
        request.getRequestDispatcher("userList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}