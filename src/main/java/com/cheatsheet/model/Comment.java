package com.cheatsheet.model; // မိမိ project package အတိုင်း ပြောင်းပါ

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int cheatSheetId;
    private int userId;
    private String content;
    private Integer parentCommentId; // Null ဖြစ်နိုင်လို့ Integer သုံးထားပါတယ်
    private Timestamp createdAt;
    
    // UI မှာ လှမ်းပြဖို့အတွက် Join Tables ကနေ ယူမယ့် fields များ
    private String username;
    private String userRole;

    // Default Constructor
    public Comment() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCheatSheetId() { return cheatSheetId; }
    public void setCheatSheetId(int cheatSheetId) { this.cheatSheetId = cheatSheetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Integer parentCommentId) { this.parentCommentId = parentCommentId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    
 // Comment.java ရဲ့ fields ထဲမှာ ဒီနှစ်လိုင်း ထပ်ထည့်ပါ
    private String status;
    private String ban_reason; // Variables နာမည်ကို Database အတိုင်း ပေးထားတာ အကောင်းဆုံးပါ

    // Constructor တွေထဲမှာလည်း ထည့်ချင်ထည့်နိုင်ပါတယ်

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBanReason() { return ban_reason; }
    public void setBanReason(String ban_reason) { this.ban_reason = ban_reason; }
}