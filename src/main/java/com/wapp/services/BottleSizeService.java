package com.wapp.services;


import java.util.List;

import com.wapp.dao.BottleSizeDao;
import com.wapp.entities.BottleSize;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class BottleSizeService {
    private static BottleSizeDao bottleSizeDao;
 
    public BottleSizeService() {
    	bottleSizeDao = new BottleSizeDao();
    }
 
    public void create(BottleSize entity) {
    	bottleSizeDao.getSU().openCurrentSessionwithTransaction();
    	bottleSizeDao.create(entity);
    	bottleSizeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(BottleSize entity) {
    	bottleSizeDao.getSU().openCurrentSessionwithTransaction();
    	bottleSizeDao.update(entity);
    	bottleSizeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public BottleSize getById(Long id) {
    	bottleSizeDao.getSU().openCurrentSession();
    	BottleSize entity = bottleSizeDao.getById(id);
    	bottleSizeDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public BottleSize getBySize(Integer size) {
    	bottleSizeDao.getSU().openCurrentSession();
    	BottleSize entity = bottleSizeDao.getBySZ(size);
    	bottleSizeDao.getSU().closeCurrentSession();
        return entity;
    }
 
    public void delete(Long id) {
    	bottleSizeDao.getSU().openCurrentSessionwithTransaction();
    	BottleSize entity = bottleSizeDao.getById(id);
    	bottleSizeDao.delete(entity);
    	bottleSizeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public List<BottleSize> getAll() {
    	bottleSizeDao.getSU().openCurrentSession();
        List<BottleSize> entityList = bottleSizeDao.getAll();
        bottleSizeDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	bottleSizeDao.getSU().openCurrentSessionwithTransaction();
    	bottleSizeDao.deleteAll();
    	bottleSizeDao.getSU().closeCurrentSessionwithTransaction();
    }
    
    public ObservableList<BottleSize> OLgetAll() {
        ObservableList<BottleSize> entityList = FXCollections.observableArrayList();
        List<BottleSize> bsList = getAll();
        for (BottleSize entity : bsList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
