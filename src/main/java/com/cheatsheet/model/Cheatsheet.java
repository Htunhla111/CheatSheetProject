package com.cheatsheet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cheatsheet {
    private int id;
    private String title;
    private String content;
    private int userId;
    private int categoryId;
    private String iconId;
    private int subCategoryId;
    private String subCategoryName;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getIconId() { return iconId; }
    public void setIconId(String iconId) { this.iconId = iconId; }
}