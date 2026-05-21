package com.cheatsheet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.User;

public class UserRepositoryImpl {

	public boolean isValidUser(String username, String password) {
	    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pst = conn.prepareStatement(sql)) {
	        pst.setString(1, username);
	        pst.setString(2, password);
	        ResultSet rs = pst.executeQuery();
	        return rs.next(); // user ရှိရင် true ပြန်မယ်
	    } catch (SQLException e) { e.printStackTrace(); }
	    return false;
	}
	
	// UserRepositoryImpl.java ထဲမှာ ထည့်ရန်
	public boolean registerUser(String username, String password, String email) { // 💡 email ပါ လက်ခံလိုက်မယ်
	    String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, 'User')";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pst = conn.prepareStatement(sql)) {
	        
	        pst.setString(1, username);
	        pst.setString(2, password);
	        pst.setString(3, email); // 💡 ကွက်တိပဲ! နံပါတ် ၃ parameter အတွက် email ထည့်ပေးလိုက်ပြီ
	        
	        int rows = pst.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	// UserRepositoryImpl.java ထဲမှာ ထည့်ရန်
	public User login(String username, String password) {
	    // id, username, role အကုန်ယူမယ်
	    String sql = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pst = conn.prepareStatement(sql)) {
	        
	        pst.setString(1, username);
	        pst.setString(2, password);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            User userObj = new User();
	            userObj.setId(rs.getInt("id")); // ဒါရှိမှ Personal Note လုပ်လို့ရမှာ
	            userObj.setUsername(rs.getString("username"));
	            userObj.setRole(rs.getString("role"));
	            return userObj;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}	
	
	// ၁။ ID ကိုကြည့်ပြီး သူက Admin လား User လား လှမ်းစစ်ပေးမည့် method
	public String getUserRoleById(int id) {
	    String role = "";
	    String sql = "SELECT role FROM users WHERE id = ?"; // မင်းရဲ့ table နဲ့ column နာမည် ပြန်ညှိပါ
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement p = conn.prepareStatement(sql)) {
	        p.setInt(1, id);
	        ResultSet rs = p.executeQuery();
	        if (rs.next()) {
	            role = rs.getString("role");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return role;
	}

	// ၂။ တကယ် ဖျက်ပေးမည့် method
	public boolean deleteUser(int id) {
	    boolean f = false;
	    String sql = "DELETE FROM users WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement p = conn.prepareStatement(sql)) {
	        p.setInt(1, id);
	        int row = p.executeUpdate();
	        if (row == 1) {
	            f = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return f;
	}
	public List<User> getAllUsers() {
	    List<User> list = new ArrayList<>();
	    String sql = "SELECT * FROM users"; // မင်းရဲ့ table နာမည်နဲ့ ညှိပါ
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement p = conn.prepareStatement(sql)) {
	        
	        ResultSet rs = p.executeQuery();
	        while (rs.next()) {
	            User u = new User();
	            u.setId(rs.getInt("id"));
	            u.setUsername(rs.getString("username"));
	            u.setEmail(rs.getString("email"));
	            u.setRole(rs.getString("role"));
	            list.add(u);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
}

