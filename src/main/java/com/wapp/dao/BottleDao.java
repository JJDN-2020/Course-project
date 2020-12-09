package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.BottleDaoInterface;
import com.wapp.entities.Bottle;
 
public class BottleDao implements BottleDaoInterface<Bottle, String>, DaoInterface<Bottle, Long> { 
	private static SessionUtil su;
	
	public BottleDao(){
		su = new SessionUtil();
	}
 
    public void create(Bottle entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(Bottle entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public Bottle getById(Long id) {
    	Bottle entity = (Bottle) su.getCurrentSession().get(Bottle.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
	public Bottle getByBottleSizeId(Long id) {
    	List<Bottle> entityList = (List<Bottle>) su.getCurrentSession().createQuery("SELECT BS FROM BottleSize BS WHERE BS.bottleSizeID = :i").setParameter("i",  id).list();
		Bottle entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {System.out.println(e);}
    	
    	return null;
    }
    
    public void delete(Bottle entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<Bottle> getAll() {
    	List<Bottle> entityList = (List<Bottle>) su.getCurrentSession().createQuery("FROM Bottle B ORDER BY B.bottleQuantity ASC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<Bottle> entityList = getAll();
        for (Bottle entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
