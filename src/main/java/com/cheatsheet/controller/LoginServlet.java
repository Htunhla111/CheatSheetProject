package com.cheatsheet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.User;
import com.cheatsheet.repository.UserRepositoryImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
   // private UserRepositoryImpl userRepo = new UserRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        User userObj = userRepo.login(user, pass); // အပေါ်က method အသစ်ကို ခေါ်မယ်

        if (userObj != null) {
            HttpSession session = request.getSession(); // variable ကြေညာခြင်း
            
            // Session ထဲမှာ data တွေ သိမ်းဆည်းခြင်း
            session.setAttribute("userId", userObj.getId()); // Note သိမ်းဖို့အတွက် အဓိက
            session.setAttribute("adminUser", userObj.getUsername());
            session.setAttribute("userRole", userObj.getRole());
            session.setAttribute("user", userObj); // ဒါလေး ထည့်ပေးဖို့ လိုပါတယ်
            
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Invalid Username or Password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }}
