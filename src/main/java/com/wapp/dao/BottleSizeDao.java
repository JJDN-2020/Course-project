package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.BottleSizeDaoInterface;
import com.wapp.entities.BottleSize;
 
public class BottleSizeDao implements BottleSizeDaoInterface<BottleSize, String>, DaoInterface<BottleSize, Long> { 
	private static SessionUtil su;
	
	public BottleSizeDao(){
		su = new SessionUtil();
	}
 
    public void create(BottleSize entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(BottleSize entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public BottleSize getById(Long id) {
    	BottleSize entity = (BottleSize) su.getCurrentSession().get(BottleSize.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
    public BottleSize getBySZ(Integer bottleSize) {
			List<BottleSize> entityList = (List<BottleSize>) su.getCurrentSession().createQuery("SELECT BS FROM BottleSize BS WHERE BS.bottleSize = :s").setParameter("s",  bottleSize).list();
			BottleSize entity = null;
        	try {
        		entity = entityList.get(0);
                return entity;
            } catch (Exception e) {System.out.println(e);}
        	
        	return null;
    }
    
    public void delete(BottleSize entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<BottleSize> getAll() {
    	System.out.println();
        List<BottleSize> entityList = (List<BottleSize>) su.getCurrentSession().createQuery("FROM BottleSize").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<BottleSize> entityList = getAll();
        for (BottleSize entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
