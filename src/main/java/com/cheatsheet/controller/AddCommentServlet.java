package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.Comment;
import com.cheatsheet.model.User;
import com.cheatsheet.repository.CommentRepository;
import com.cheatsheet.repository.NotificationRepository; // 💡 NotificationRepository ကို Import သွင်းခြင်း

@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CommentRepository commentRepo = new CommentRepository();
    private NotificationRepository notiRepo = new NotificationRepository(); // 💡 Noti Object ဆောက်ခြင်း

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // မြန်မာစာအက္ခရာများ မပျက်စီးစေရန် UTF-8 သတ်မှတ်ခြင်း
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");
        
        // Login မဝင်ရသေးပါက Login Form သို့ မောင်းထုတ်ခြင်း
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Form မှ ပေးပို့လိုက်သော Data များကို လက်ခံခြင်း
        int sheetId = Integer.parseInt(request.getParameter("sheetId"));
        String content = request.getParameter("content");
        String parentIdParam = request.getParameter("parentCommentId");
        
        // Comment Object တည်ဆောက်ပြီး Data ထည့်ခြင်း
        Comment comment = new Comment();
        comment.setCheatSheetId(sheetId);
        comment.setUserId(loggedInUser.getId());
        comment.setContent(content);
        
        // Reply ဖြစ်ပါက Parent Comment ID ကို စနစ်တကျ ထည့်သွင်းခြင်း
        Integer parentCommentId = null;
        if (parentIdParam != null && !parentIdParam.isEmpty()) {
            parentCommentId = Integer.parseInt(parentIdParam);
            comment.setParentCommentId(parentCommentId);
        }
        
        // Database ထဲသို့ ကွန်မန့်အား လှမ်းသိမ်းခြင်း
        boolean isSaved = commentRepo.saveComment(comment);
        
        if (isSaved) {
            // ----------------------------------------------------------------
            // 💡 HOME တွင် NOTI တက်စေရန်အတွက် DATA ထည့်သွင်းခြင်း
            // ----------------------------------------------------------------
            String notiMessage;
            if (parentCommentId != null) {
                // Admin က ပြန်တဲ့ Reply ဖြစ်လျှင်
                notiMessage = loggedInUser.getUsername() + " (Admin) က ကွန်မန့်တစ်ခုအား အကြောင်းပြန်ပေးလိုက်ပါသည်။";
            } else {
                // User ပုံမှန်မန့်သည့် ကွန်မန့်ဖြစ်လျှင်
                notiMessage = loggedInUser.getUsername() + " က Cheat Sheet တွင် ကွန်မန့်တစ်ခု ရေးသားခဲ့ပါသည်။";
            }
            
            // Notification Table ထဲသို့ လှမ်းထည့်ခြင်း
            notiRepo.addNotification(sheetId, notiMessage);
            // ----------------------------------------------------------------
        }
        
        // ကွန်မန့်ပေးပြီးပါက မူလ အသေးစိတ် (Detail) စာမျက်နှာဆီသို့ အလိုအလျောက် ပြန်ပို့ပေးခြင်း
        response.sendRedirect("detail?id=" + sheetId);
    }
}