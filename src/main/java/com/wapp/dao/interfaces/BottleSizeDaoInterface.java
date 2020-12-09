package com.wapp.dao.interfaces;

import com.wapp.entities.BottleSize;
 
public interface BottleSizeDaoInterface<T, Id> {
	public BottleSize getBySZ(Integer size);
}