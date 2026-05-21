package com.cheatsheet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Icon {
    private int id;
	private String iconClass;
    private String displayName;

    public Icon(String iconClass, String displayName) {
        this.iconClass = iconClass;
        this.displayName = displayName;
    }

	public Icon() {
		// TODO Auto-generated constructor stub
	}

	}
