package com.wapp.services;


import java.util.List;

import com.wapp.dao.WineRecipeDao;
import com.wapp.entities.WineRecipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class WineRecipeService {
    private static WineRecipeDao wineRecipeDao;
 
    public WineRecipeService() {
    	wineRecipeDao = new WineRecipeDao();
    }
 
    public void create(WineRecipe entity) {
    	wineRecipeDao.getSU().openCurrentSessionwithTransaction();
    	wineRecipeDao.create(entity);
    	wineRecipeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(WineRecipe entity) {
    	wineRecipeDao.getSU().openCurrentSessionwithTransaction();
    	wineRecipeDao.update(entity);
    	wineRecipeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public WineRecipe getById(Long id) {
    	wineRecipeDao.getSU().openCurrentSession();
    	WineRecipe entity = wineRecipeDao.getById(id);
    	wineRecipeDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public WineRecipe getWineRecipeByNm(String un) {
    	wineRecipeDao.getSU().openCurrentSession();
    	WineRecipe entity = wineRecipeDao.findByNm(un);
    	wineRecipeDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public void delete(Long id) {
    	wineRecipeDao.getSU().openCurrentSessionwithTransaction();
    	WineRecipe entity = wineRecipeDao.getById(id);
    	wineRecipeDao.delete(entity);
    	wineRecipeDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<WineRecipe> getAll() {
    	wineRecipeDao.getSU().openCurrentSession();
    	List<WineRecipe> entityList = wineRecipeDao.getAll();
    	wineRecipeDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	wineRecipeDao.getSU().openCurrentSessionwithTransaction();
    	wineRecipeDao.deleteAll();
    	wineRecipeDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<WineRecipe> OLgetAll() {
        ObservableList<WineRecipe> entityList = FXCollections.observableArrayList();
        List<WineRecipe> gList = getAll();
        for (WineRecipe entity : gList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
