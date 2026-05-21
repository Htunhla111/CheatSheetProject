package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.repository.UserRepositoryImpl;

// မင်းရဲ့ User Repo Path အမှန် ထည့်ပါ

@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userIdStr = request.getParameter("id");
        
        if (userIdStr != null && !userIdStr.isEmpty()) {
            int userId = Integer.parseInt(userIdStr);
            UserRepositoryImpl repo = new UserRepositoryImpl();
            
            // 💡 အဓိကအဆင့်: မဖျက်ခင် အဲဒီ User ရဲ့ Role ကို DB ထဲမှာ အရင်လှမ်းစစ်မယ်
            String userRole = repo.getUserRoleById(userId);
            
            if ("Admin".equalsIgnoreCase(userRole)) {
                // Admin ဖြစ်နေရင် ဖျက်ခွင့်မပြုဘဲ Error message နဲ့အတူ user list ဆီ ပြန်လွှတ်မယ်
                request.getSession().setAttribute("errorMsg", "Admin အချင်းချင်းကို ဖျက်ခွင့်မရှိပါ!");
                response.sendRedirect("userList"); // မင်းရဲ့ user list ပြတဲ့ servlet/page လမ်းကြောင်း ထည့်ပါ
            } else {
                // User ဖြစ်ခဲ့ရင် အောင်အောင်မြင်မြင် ဖျက်မယ်
                boolean isDeleted = repo.deleteUser(userId);
                if (isDeleted) {
                    request.getSession().setAttribute("succMsg", "User ကို စာရင်းထဲက ဖျက်ပြီးပါပြီ။");
                } else {
                    request.getSession().setAttribute("errorMsg", "ဖျက်ဆီးခြင်း မအောင်မြင်ပါ။");
                }
                response.sendRedirect("userList");
            }
        }
    }
}