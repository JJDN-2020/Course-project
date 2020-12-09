package com.wapp.services;


import java.util.List;

import com.wapp.dao.WineDao;
import com.wapp.entities.Wine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class WineService {
    private static WineDao wineDao;
 
    public WineService() {
    	wineDao = new WineDao();
    }
 
    public void create(Wine entity) {
    	wineDao.getSU().openCurrentSessionwithTransaction();
    	wineDao.create(entity);
    	wineDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(Wine entity) {
    	wineDao.getSU().openCurrentSessionwithTransaction();
    	wineDao.update(entity);
    	wineDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public Wine getById(Long id) {
    	wineDao.getSU().openCurrentSession();
    	Wine entity = wineDao.getById(id);
    	wineDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public Wine getByWineRecipeId(Long id) {
    	wineDao.getSU().openCurrentSession();
    	Wine entity = wineDao.getByWineRecipeId(id);
    	wineDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public void delete(Long id) {
    	wineDao.getSU().openCurrentSessionwithTransaction();
    	Wine entity = wineDao.getById(id);
    	wineDao.delete(entity);
    	wineDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<Wine> getAll() {
    	wineDao.getSU().openCurrentSession();
    	List<Wine> entityList = wineDao.getAll();
    	wineDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	wineDao.getSU().openCurrentSessionwithTransaction();
    	wineDao.deleteAll();
    	wineDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<Wine> OLgetAll() {
        ObservableList<Wine> entityList = FXCollections.observableArrayList();
        List<Wine> gList = getAll();
        for (Wine entity : gList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
