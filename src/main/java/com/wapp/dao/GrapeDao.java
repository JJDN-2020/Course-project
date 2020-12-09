package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.GrapeDaoInterface;
import com.wapp.entities.Grape;
 
public class GrapeDao implements GrapeDaoInterface<Grape, String>, DaoInterface<Grape, Long> { 
	private static SessionUtil su;
	
	public GrapeDao(){
		su = new SessionUtil();
	}
 
    public void create(Grape entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(Grape entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public Grape getById(Long id) {
    	Grape entity = (Grape) su.getCurrentSession().get(Grape.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
    public Grape findByNm(String g) {
    	List<Grape> entityList = (List<Grape>) su.getCurrentSession().createQuery("SELECT G FROM Grape G WHERE G.grapeName = :gn").setParameter("gn",  g).list();
    	Grape entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {}
    	
    	return null;
    }
 
    public void delete(Grape entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<Grape> getAll() {
    	List<Grape> entityList = (List<Grape>) su.getCurrentSession().createQuery("FROM Grape G ORDER BY G.grapeID DESC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<Grape> entityList = getAll();
        for (Grape entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
