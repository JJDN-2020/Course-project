package com.wapp.services;


import java.util.List;

import com.wapp.dao.BottlingDao;
import com.wapp.entities.Bottling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class BottlingService {
    private static BottlingDao bottlingDao;
 
    public BottlingService() {
    	bottlingDao = new BottlingDao();
    }
 
    public void create(Bottling entity) {
    	bottlingDao.getSU().openCurrentSessionwithTransaction();
    	bottlingDao.create(entity);
    	bottlingDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(Bottling entity) {
    	bottlingDao.getSU().openCurrentSessionwithTransaction();
    	bottlingDao.update(entity);
    	bottlingDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public Bottling getById(Long id) {
    	bottlingDao.getSU().openCurrentSession();
    	Bottling entity = bottlingDao.getById(id);
    	bottlingDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public void delete(Long id) {
    	bottlingDao.getSU().openCurrentSessionwithTransaction();
    	Bottling entity = bottlingDao.getById(id);
    	bottlingDao.delete(entity);
    	bottlingDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<Bottling> getAll() {
    	bottlingDao.getSU().openCurrentSession();
    	List<Bottling> entityList = bottlingDao.getAll();
    	bottlingDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	bottlingDao.getSU().openCurrentSessionwithTransaction();
    	bottlingDao.deleteAll();
    	bottlingDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<Bottling> OLgetAll() {
        ObservableList<Bottling> entityList = FXCollections.observableArrayList();
        List<Bottling> bList = getAll();
        for (Bottling entity : bList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
