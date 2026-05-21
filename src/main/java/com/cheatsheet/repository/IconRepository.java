package com.cheatsheet.repository;

import java.util.List;

import com.cheatsheet.model.Icon;

public interface IconRepository {
	
	List<Icon> getAllIcons();

	void saveIcon(Icon newIcon);

}

