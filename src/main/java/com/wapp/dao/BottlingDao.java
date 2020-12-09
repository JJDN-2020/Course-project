package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.BottlingDaoInterface;
import com.wapp.entities.Bottling;
 
public class BottlingDao implements BottlingDaoInterface<Bottling, String>, DaoInterface<Bottling, Long> { 
	private static SessionUtil su;
	
	public BottlingDao(){
		su = new SessionUtil();
	}
 
    public void create(Bottling entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(Bottling entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public Bottling getById(Long id) {
    	Bottling entity = (Bottling) su.getCurrentSession().get(Bottling.class, id);
        return entity; 
    }
 
    public void delete(Bottling entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<Bottling> getAll() {
    	List<Bottling> entityList = (List<Bottling>) su.getCurrentSession().createQuery("FROM Bottling B ORDER BY B.bottlingID DESC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<Bottling> entityList = getAll();
        for (Bottling entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
