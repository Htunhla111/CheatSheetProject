package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.User;
import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/my-notes")
public class MyNotesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NoteRepositoryImpl noteRepo = new NoteRepositoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // မင်းရဲ့ Session Attribute 'user' အတိုင်း သုံးထားပါတယ်
        
        // 🔐 Login မဝင်ထားရင် Login Page သို့ မောင်းထုတ်မည်
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        
        // ❌ ၁။ Note ဖျက်ခြင်းလုပ်ငန်းစဉ် (Action = delete)
        if ("delete".equals(action)) {
            try {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    
                    // 💡 Repository ရဲ့ Parameter ၂ ခု (userId, id) အတိုင်း ကွက်တိ ပို့ပေးလိုက်ပါတယ်
                    boolean isDeleted = noteRepo.deleteNote(user.getId(), id); 
                    
                    if (isDeleted) {
                        session.setAttribute("succMsg", "Note deleted successfully.");
                    } else {
                        session.setAttribute("errorMsg", "Delete failed.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("my-notes");
            return;
        }

        // 📖 ၂။ ပုံမှန်စာမျက်နှာလာကြည့်လျှင် User ၏ Note စာရင်းအားလုံးကို ဆွဲထုတ်ပြီး JSP သို့ ပို့ပေးမည်
        request.setAttribute("noteList", noteRepo.getNotesByUserId(user.getId()));
        request.getRequestDispatcher("my-notes.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // မြန်မာစာ Font တွေ မပျက်အောင် ကာကွယ်ခြင်း
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 🔐 Login မဝင်ထားရင် Login Page သို့ မောင်းထုတ်မည်
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        String remark = request.getParameter("personalRemark");
        
        // 💡 User က ဘာမှမရေးဘဲ အလွတ်ကြီး ပို့လိုက်ရင် null/empty ဖြစ်မနေစေဘဲ String အလွတ် "" အဖြစ် ပြောင်းပေးပါမယ်
        if (remark == null || remark.trim().isEmpty()) {
            remark = ""; 
        }

        // 📝 နည်းလမ်း (၁) - Note သီးသန့် Dashboard ကနေ ပြန်ပြင်တဲ့ လုပ်ငန်းစဉ် (Action = update)
        if ("update".equals(action)) {
            int noteId = Integer.parseInt(request.getParameter("id"));
            noteRepo.updateNote(noteId, user.getId(), remark);
            session.setAttribute("succMsg", "Note updated successfully.");
            
        // ➕ နည်းလမ်း (၂) - Detail.jsp ကနေ ကတ်ပြားကို သိမ်းဆည်းတဲ့ လုပ်ငန်းစဉ် (Action = create)
        } else if ("create".equals(action)) {
            // detail.jsp က name="sheetId" နဲ့ ပို့လိုက်တဲ့ Cheat Sheet ID ကို ဖမ်းယူခြင်း
            int sheetId = Integer.parseInt(request.getParameter("sheetId")); 
            
            // 💡 ဒေတာဘေ့စ်ထဲမှာ ဒီ User က ဒီ Cheat Sheet အတွက် မှတ်စု ရှိပြီးသားလား အရင်စစ်မည်
            boolean isAlreadySaved = noteRepo.isNoteSavedByUser(user.getId(), sheetId);
            
            if (isAlreadySaved) {
                // 🔄 ရှိပြီးသားဖြစ်နေလျှင် Error မတက်စေဘဲ နောက်ဆုံးရေးလိုက်သည့်စာသားဖြင့် အလိုအလျောက် အစားထိုး UPDATE လုပ်ပေးမည်
                // Parameter (၃) ခုစလုံး စနစ်တကျ ကွက်တိ ကိုက်ညီအောင် ဖြည့်သွင်းပေးထားပါတယ်
                noteRepo.saveNote(user.getId(), sheetId, remark); 
                session.setAttribute("succMsg", "Note updated successfully.");
            } else {
                // 🆕 မရှိသေးလျှင် အသစ် INSERT လုပ်မည်
                boolean isSaved = noteRepo.saveNote(user.getId(), sheetId, remark);
                if (isSaved) {
                    session.setAttribute("succMsg", "Note saved successfully.");
                } else {
                    session.setAttribute("errorMsg", "Something went wrong on server.");
                }
            }
        }

        // လုပ်ဆောင်ချက် ပြီးဆုံးပါက My Notes List สာမျက်နှာသို့ ပြန်လည်ညွှန်းပို့မည်
        response.sendRedirect("my-notes");
    }
}