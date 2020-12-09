package com.wapp.dao.interfaces;

import com.wapp.entities.GrapeVariety;
 
public interface GrapeVarietyDaoInterface<T, Id> {
	public GrapeVariety getByName(String name);
}