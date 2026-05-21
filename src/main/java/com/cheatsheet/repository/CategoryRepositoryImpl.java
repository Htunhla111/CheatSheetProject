package com.cheatsheet.repository;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	import com.cheatsheet.model.Category;
import com.cheatsheet.config.DBConnection;

	public class CategoryRepositoryImpl implements CategoryRepository {

		@Override
		public List<Category> getAllCategories() {
		    List<Category> categoryList = new ArrayList<>();
		    
		    // 💡 SQL Query အမှန်: categories နဲ့ sub_categories ကို JOIN တွဲပြီး COUNT တွက်ချက်ခြင်း
		    String sql = "SELECT c.id, c.name, c.icon, COUNT(s.id) AS item_count " +
		                 "FROM categories c " +
		                 "LEFT JOIN sub_categories s ON c.id = s.category_id " +
		                 "GROUP BY c.id, c.name, c.icon";

		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement pst = conn.prepareStatement(sql);
		         ResultSet rs = pst.executeQuery()) {

		        while (rs.next()) {
		            Category category = new Category();
		            category.setId(rs.getInt("id"));
		            category.setName(rs.getString("name"));
		            category.setIcon(rs.getString("icon"));
		            
		            // Database column 'item_count' ထဲက တွက်ချက်ပြီးသား အရေအတွက်ကို Model ထဲ ထည့်ခြင်း
		            category.setTotalItems(rs.getInt("item_count")); 

		            categoryList.add(category);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return categoryList;
			    
		}	  
		@Override
	    public Category getCategoryById(int id) {
	        String sql = "SELECT * FROM categories WHERE id = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement pst = conn.prepareStatement(sql)) {
	            
	            pst.setInt(1, id);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return new Category(rs.getInt("id"), rs.getString("name"), rs.getString("icon"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
		// com.cheatsheet.repository.CategoryRepositoryImpl ထဲသို့ သွားရောက်ပေါင်းထည့်ရန်
		public String getSubCategoryNameById(int subId) {
		    String subCategoryName = "Cheat Sheets"; // ဒေတာမတွေ့ရင် ဒါပဲ ပြန်မယ်
		    String sql = "SELECT name FROM sub_categories WHERE id = ?"; // 💡 column နာမည်ကို သေချာစစ်ပါ
		    
		    try (Connection conn = DBConnection.getConnection(); // မင်းရဲ့ DB Connection Class နာမည် သေချာစစ်ပါ
		         PreparedStatement pst = conn.prepareStatement(sql)) {
		        
		        pst.setInt(1, subId);
		        try (ResultSet rs = pst.executeQuery()) {
		            if (rs.next()) {
		                subCategoryName = rs.getString("name"); // 💡 Database ထဲက 'Java' ကို ယူတာပါ
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); // 💡 Eclipse Console ထဲမှာ Error တက်နေလား သိရအောင်ပါ
		    }
		    return subCategoryName;
		}		  
		
	}
	

