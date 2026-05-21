package com.cheatsheet.repository;

	import java.util.List;
	import com.cheatsheet.model.Category;

	public interface CategoryRepository {
		
	    List<Category> getAllCategories();
	    Category getCategoryById(int id);
	}

