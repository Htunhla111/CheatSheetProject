package com.cheatsheet.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {
    private int id;
    private int userId;
    private int cheatsheetId;
    private String sheetTitle; // UI မှာ ပြဖို့အတွက်
    private String personalRemark;
    private Timestamp savedAt;
    private String userName;

    // Getters and Setters များ ထည့်ပေးပါ
}