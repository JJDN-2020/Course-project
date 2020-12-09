package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.WineRecipeDaoInterface;
import com.wapp.entities.WineRecipe;
 
public class WineRecipeDao implements WineRecipeDaoInterface<WineRecipe, String>, DaoInterface<WineRecipe, Long> { 
	private static SessionUtil su;
	
	public WineRecipeDao(){
		su = new SessionUtil();
	}
 
    public void create(WineRecipe entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(WineRecipe entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public WineRecipe getById(Long id) {
    	WineRecipe entity = (WineRecipe) su.getCurrentSession().get(WineRecipe.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
    public WineRecipe findByNm(String wr) {
    	List<WineRecipe> entityList = (List<WineRecipe>) su.getCurrentSession().createQuery("SELECT WR FROM WineRecipe WR WHERE WR.recipeName = :wrn").setParameter("wrn",  wr).list();
    	WineRecipe entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {}
    	
    	return null;
    }
 
    public void delete(WineRecipe entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<WineRecipe> getAll() {
    	List<WineRecipe> entityList = (List<WineRecipe>) su.getCurrentSession().createQuery("FROM WineRecipe WR ORDER BY WR.recipeID DESC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<WineRecipe> entityList = getAll();
        for (WineRecipe entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
