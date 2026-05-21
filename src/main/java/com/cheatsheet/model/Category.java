package com.cheatsheet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class Category {

	
		    private int id;
	    private String name;
	    private String icon;
	    private int totalItems;

	    // Constructors
	    public Category(int id, String name, String icon) {
	        this.id = id;
	        this.name = name;
	        this.icon = icon;
	    }

		public Category() {
			// TODO Auto-generated constructor stub
		}
}
