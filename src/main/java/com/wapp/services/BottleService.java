package com.wapp.services;


import java.util.List;

import com.wapp.dao.BottleDao;
import com.wapp.entities.Bottle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class BottleService {
    private static BottleDao bottleDao;
 
    public BottleService() {
    	bottleDao = new BottleDao();
    }
 
    public void create(Bottle entity) {
    	bottleDao.getSU().openCurrentSessionwithTransaction();
    	bottleDao.create(entity);
    	bottleDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(Bottle entity) {
    	bottleDao.getSU().openCurrentSessionwithTransaction();
    	bottleDao.update(entity);
    	bottleDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public Bottle getById(Long id) {
    	bottleDao.getSU().openCurrentSession();
    	Bottle entity = bottleDao.getById(id);
    	bottleDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public Bottle getByBottleSizeId(Long id) {
    	bottleDao.getSU().openCurrentSession();
    	Bottle entity = bottleDao.getByBottleSizeId(id);
    	bottleDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public void delete(Long id) {
    	bottleDao.getSU().openCurrentSessionwithTransaction();
    	Bottle entity = bottleDao.getById(id);
    	bottleDao.delete(entity);
    	bottleDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<Bottle> getAll() {
    	bottleDao.getSU().openCurrentSession();
    	List<Bottle> entityList = bottleDao.getAll();
    	bottleDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	bottleDao.getSU().openCurrentSessionwithTransaction();
    	bottleDao.deleteAll();
    	bottleDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<Bottle> OLgetAll() {
        ObservableList<Bottle> entityList = FXCollections.observableArrayList();
        List<Bottle> bList = getAll();
        for (Bottle entity : bList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
