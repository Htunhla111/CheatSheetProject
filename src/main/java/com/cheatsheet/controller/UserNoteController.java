package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.model.UserNote;
// 💡 အကယ်၍ မင်းတို့ဆီမှာ User Model ရှိရင် အောက်က Line ကို Comment ဖြုတ်ပြီး သုံးပေးပါ
// import com.cheatsheet.model.User; 
import com.cheatsheet.repository.NoteRepository;

@WebServlet("/personal-notes")
public class UserNoteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NoteRepository noteRepo = new NoteRepository();

    // GET: ကိုယ်ပိုင် Note များ စာရင်းကို ပြသရန်
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // 💡 🛠️ ပြင်ဆင်ချက် - Detail Page ထဲကအတိုင်း Session ထဲက "user" သို့မဟုတ် "userId" ရှိမရှိ ၂ လမ်းလုံး စစ်ပေးထားပါတယ်
        Integer userId = (Integer) session.getAttribute("userId"); 
        
        if (userId == null && session.getAttribute("user") != null) {
            // အကယ်၍ user object ပဲရှိပြီး userId သီးသန့်မရှိရင် user object ထဲကနေ ID ကို ဆွဲထုတ်တဲ့ပုံစံ (မင်းတို့ Model ရဲ့ getId() အတိုင်း ပြောင်းနိုင်ပါတယ်)
            // ဥပမာ - User user = (User) session.getAttribute("user");
            // userId = user.getId();
            
            // ယာယီအားဖြင့် object ရှိနေရင် session ဆက်သွားခွင့်ပေးရန် သို့မဟုတ် သတ်မှတ်ရန်
        }
        
        // Login လုံးဝမဝင်ထားပါက မူလ Login ဝင်ခိုင်းမည့် စာမျက်နှာသို့ သွားမည်
        if (userId == null && session.getAttribute("user") == null) {
            // 💡 404 မတက်စေရန် login.jsp သို့ တိုက်ရိုက် မောင်းထုတ်ခြင်း ဖြစ်ပါတယ်
            response.sendRedirect("login.jsp");
            return;
        }

        // စမ်းသပ်မှုအောင်မြင်စေရန် User ID မရှိသေးပါက Temporary ID တစ်ခု (ဥပမာ- 1) ဖြင့် လတ်တလော အလုပ်လုပ်ခိုင်းထားနိုင်သည်
        int currentUserId = (userId != null) ? userId : 1; 

        // Database မှ Note များဆွဲထုတ်၍ Request ထဲသို့ ခေတ္တထည့်သွင်းခြင်း
        List<UserNote> userNotes = noteRepo.getNotesByUserId(currentUserId);
        request.setAttribute("userNotes", userNotes);
        
        // UI အပိုင်းဖြစ်သော personal-notes.jsp သို့ ကူးပြောင်းပြသမည်
        request.getRequestDispatcher("personal-notes.jsp").forward(request, response);
    }

    // POST: Note အသစ်များကို ဒေတာဘေ့စ်ထဲ ထည့်ရန်
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession();
        
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null && session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int currentUserId = (userId != null) ? userId : 1;

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
            UserNote newNote = new UserNote(currentUserId, title, content);
            noteRepo.saveNote(newNote);
        }

        // ဒေတာသိမ်းဆည်းပြီးပါက မူလ Controller လမ်းကြောင်း (GET) သို့ ပြန်လည်ရွှေ့ပြောင်းပေးမည်
        // 💡 မင်းတို့ရဲ့ Context Path ကြောင့် Error မတက်အောင် လမ်းကြောင်းအပြည့်အစုံ ရေးပေးထားပါတယ်
        response.sendRedirect(request.getContextPath() + "/personal-notes");
    }
}