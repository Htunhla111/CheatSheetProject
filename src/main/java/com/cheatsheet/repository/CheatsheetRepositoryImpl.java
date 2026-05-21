package com.cheatsheet.repository;

import java.sql.*;
import java.util.*;
import com.cheatsheet.model.Cheatsheet;
import com.cheatsheet.config.DBConnection;

public class CheatsheetRepositoryImpl implements CheatsheetRepository {

	@Override
	public void addSheet(Cheatsheet sheet) {
		// 💡 ဖြေရှင်းချက်: SQL Query ထဲမှာ ? ၄ ခုပဲ သုံးပြီး sub_category_id ကို တိုက်ရိုက် ထည့်သွင်းပါမည် (category_id ကို ဖြုတ်ထားပါသည်)
		String sql = "INSERT INTO cheatsheets (title, content, user_id, sub_category_id) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setString(1, sheet.getTitle());
			pst.setString(2, sheet.getContent());
			
			// User ID စစ်ဆေးခြင်း
			if (sheet.getUserId() <= 0) {
				pst.setNull(3, java.sql.Types.INTEGER);
			} else {
				pst.setInt(3, sheet.getUserId());
			}
			
			// 💡 ဖြေရှင်းချက်: စောစောက ဒေတာဘေ့စ်ထဲ NULL ဝင်ရသည့်အကြောင်းရင်းမှာ ဒီနေရာတွင် sub_category_id ကို မသိမ်းဘဲ ကျန်ခဲ့၍ ဖြစ်ပါသည်။
			// ယခု နံပါတ် ၄ နေရာတွင် sub_category_id ကို ကွက်တိ သတ်မှတ်ပေးလိုက်ပါပြီ။
			if (sheet.getSubCategoryId() <= 0) {
				pst.setNull(4, java.sql.Types.INTEGER);
			} else {
				pst.setInt(4, sheet.getSubCategoryId());
			}
			
			pst.executeUpdate();
			System.out.println("DEBUG: Sheet added successfully with SubCategoryID!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cheatsheet> getAllSheets() {
		List<Cheatsheet> list = new ArrayList<>();
		String sql = "SELECT * FROM cheatsheets ORDER BY id DESC";
		try (Connection conn = DBConnection.getConnection();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return list;
	}

	@Override
	public List<Cheatsheet> getSheetsBySubId(int subId) {
		List<Cheatsheet> list = new ArrayList<>();
		// 💡 ဖြေရှင်းချက်: sub_category_id တစ်ခုတည်းနဲ့ပဲ စစ်ထုတ်ပြီး စာမျက်နှာပေါ် ပြသပါမည်
		String sql = "SELECT * FROM cheatsheets WHERE sub_category_id = ? ORDER BY id DESC";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setInt(1, subId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	// 💡 ဖြေရှင်းချက်: Interface အသစ်နှင့် ကိုက်ညီစေရန် နာမည်ကို getSheetById ဟု ပြောင်းလဲပြီး @Override လုပ်ထားပါသည်
	public Cheatsheet getSheetById(int id) { 
		Cheatsheet sheet = null;
		
		// SQL Query ကို sub_categories table နဲ့ပဲ ကွက်တိ JOIN ထားပါတယ်
		String sql = "SELECT s.*, sc.name as sub_category_name " +
					 "FROM cheatsheets s " +
					 "JOIN sub_categories sc ON s.sub_category_id = sc.id " +
					 "WHERE s.id = ?";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, id);
			
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					sheet = mapRow(rs);
					// Database ကလာတဲ့ sub_category_name ကို Model ထဲသို့ ထည့်သွင်းခြင်း
					sheet.setSubCategoryName(rs.getString("sub_category_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sheet;
	}

	private Cheatsheet mapRow(ResultSet rs) throws SQLException {
		Cheatsheet s = new Cheatsheet();
		s.setId(rs.getInt("id"));
		s.setTitle(rs.getString("title"));
		s.setContent(rs.getString("content"));
		s.setUserId(rs.getInt("user_id"));
		s.setIconId(rs.getString("icon_id"));
		s.setSubCategoryId(rs.getInt("sub_category_id"));
		return s;
	}
	
	@Override
	public List<Cheatsheet> searchSheets(String keyword) {
		List<Cheatsheet> list = new ArrayList<>();
		String sql = "SELECT * FROM cheatsheets WHERE title LIKE ? ORDER BY id DESC";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateSheet(Cheatsheet sheet) {
		// 💡 ဖြေရှင်းချက်: Update လုပ်ရာတွင်လည်း category_id နေရာတွင် sub_category_id ကို ပြောင်းလဲပြင်ဆင်ပေးထားပါတယ်
		String sql = "UPDATE cheatsheets SET title = ?, content = ?, sub_category_id = ? WHERE id = ?";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setString(1, sheet.getTitle());
			pst.setString(2, sheet.getContent());
			pst.setInt(3, sheet.getSubCategoryId());
			pst.setInt(4, sheet.getId());
			
			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void deleteSheet(int id) {
		String sql = "DELETE FROM cheatsheets WHERE id = ?";
		
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			pst.executeUpdate();
			System.out.println("DEBUG: Sheet deleted successfully!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}