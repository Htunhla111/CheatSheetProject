package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.repository.UserRepositoryImpl;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserRepositoryImpl userRepo = new UserRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Form ဘက်က ပို့လိုက်တဲ့ Data တွေကို ဖတ်ခြင်း
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        String email = request.getParameter("email"); // 💡 အသစ်ထည့်လိုက်သည်: register.jsp က email ကို ဖတ်ရန်

        // 2. Password တိုက်ဆိုင်စစ်ဆေးခြင်း
        if (pass != null && !pass.equals(confirmPass)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 3. ဒေတာ ၃ ခုလုံးကို Repository ထဲ လှမ်းပို့ပြီး Register လုပ်ခြင်း
        // 💡 userRepo.registerUser ထဲမှာ email ပါ ပူးတွဲ ထည့်ပေးလိုက်ပါပြီ
        if (userRepo.registerUser(user, pass, email)) { 
            response.sendRedirect("login?msg=Registered successfully!");
        } else {
            request.setAttribute("error", "Registration failed or Username already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}