package com.wapp.dao.interfaces;

import com.wapp.entities.UserRole;
 
public interface UserRoleDaoInterface<T, Id> {
	public UserRole getByName(String name);
}