package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.GrapeVarietyDaoInterface;
import com.wapp.entities.GrapeVariety;
 
public class GrapeVarietyDao implements GrapeVarietyDaoInterface<GrapeVariety, String>, DaoInterface<GrapeVariety, Long> { 
	private static SessionUtil su;
	
	public GrapeVarietyDao(){
		su = new SessionUtil();
	}
 
    public void create(GrapeVariety entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(GrapeVariety entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public GrapeVariety getById(Long id) {
    	GrapeVariety entity = (GrapeVariety) su.getCurrentSession().get(GrapeVariety.class, id);
        return entity; 
    }
    
    public GrapeVariety getByName(String name) {
        	@SuppressWarnings("unchecked")
			List<GrapeVariety> entityList = (List<GrapeVariety>) su.getCurrentSession().createQuery("SELECT GV FROM GrapeVariety GV WHERE GV.varietyName = :vn").setParameter("vn",  name).list();
        	GrapeVariety entity = null;
        	try {
        		entity = entityList.get(0);
                return entity;
            } catch (Exception e) {System.out.println("Exception in GrapeVarietyDao.getByName(String) "+e);}
        	
        	return null;
    }
    
    public void delete(GrapeVariety entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<GrapeVariety> getAll() {
    	System.out.println();
        List<GrapeVariety> entityList = (List<GrapeVariety>) su.getCurrentSession().createQuery("FROM GrapeVariety").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<GrapeVariety> entityList = getAll();
        for (GrapeVariety entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
