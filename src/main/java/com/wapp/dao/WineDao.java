package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.WineDaoInterface;
import com.wapp.entities.Wine;
 
public class WineDao implements WineDaoInterface<Wine, String>, DaoInterface<Wine, Long> { 
	private static SessionUtil su;
	
	public WineDao(){
		su = new SessionUtil();
	}
 
    public void create(Wine entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(Wine entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public Wine getById(Long id) {
    	Wine entity = (Wine) su.getCurrentSession().get(Wine.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
	public Wine getByWineRecipeId(Long id) {
    	List<Wine> entityList = (List<Wine>) su.getCurrentSession().createQuery("SELECT WR FROM WineRecipe WR WHERE WR.recipeID = :i").setParameter("i",  id).list();
    	Wine entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {System.out.println(e);}
    	
    	return null;
    }
 
    public void delete(Wine entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<Wine> getAll() {
    	List<Wine> entityList = (List<Wine>) su.getCurrentSession().createQuery("FROM Wine W ORDER BY W.wineID DESC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<Wine> entityList = getAll();
        for (Wine entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
