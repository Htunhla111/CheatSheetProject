package com.cheatsheet.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.SubCategory;
import com.cheatsheet.repository.NoteRepositoryImpl;

@WebServlet("/add-category")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		NoteRepositoryImpl repo = new NoteRepositoryImpl();
		
		// ၁။ Database ထဲက နည်းပညာ (Sub-category) စာရင်းကို အရင်ဆုံး လှမ်းယူမယ်
		List<SubCategory> subList = repo.getAllSubCategories(); 
		
		// ၂။ ရလာတဲ့ List ကို subList ဆိုတဲ့ နာမည်နဲ့ JSP ဘက်ကို သယ်သွားဖို့ သေချာထည့်မယ်
		request.setAttribute("subList", subList);
		
		// ၃။ ဒေတာတွေအကုန် အဆင်သင့်ဖြစ်မှ Form ရှိရာ add-category.jsp စာမျက်နှာဆီကို forward လုပ်မယ်
		request.getRequestDispatcher("add-category.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    // Request ကို UTF-8 ပြောင်းပေးခြင်း (မြန်မာစာ ပါခဲ့လျှင် မပျက်စေရန်)
	    request.setCharacterEncoding("UTF-8");
	    
	    // HTML Input ရဲ့ name="" တန်ဖိုးတွေအတိုင်း အတိအကျ လှမ်းဖတ်ခြင်း
	    String name = request.getParameter("categoryName"); 
	    String icon = request.getParameter("iconClass");    

	    // SQL Query: မင်းရဲ့ Database Column က name နဲ့ icon ဖြစ်လို့ အောက်ပါအတိုင်း ရေးပါတယ်
	    String sql = "INSERT INTO categories (name, icon) VALUES (?, ?)";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pst = conn.prepareStatement(sql)) {
	        
	        pst.setString(1, name);
	        pst.setString(2, icon); // အပေါ်က ဖတ်လာတဲ့ icon code (ဥပမာ- fab fa-linux) ကို ထည့်ခြင်း
	        
	        int rows = pst.executeUpdate();
	        
	        if(rows > 0) {
	            // အောင်မြင်ရင် Home Controller သို့ ပြန်လွှတ်မည်
	            response.sendRedirect("home"); 
	        } else {
	            response.getWriter().println("Failed to insert category.");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.getWriter().println("Database Error: " + e.getMessage());
	    }
	}}