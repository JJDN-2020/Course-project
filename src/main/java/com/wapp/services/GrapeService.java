package com.wapp.services;


import java.util.List;

import com.wapp.dao.GrapeDao;
import com.wapp.entities.Grape;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class GrapeService {
    private static GrapeDao grapeDao;
 
    public GrapeService() {
    	grapeDao = new GrapeDao();
    }
 
    public void create(Grape entity) {
    	grapeDao.getSU().openCurrentSessionwithTransaction();
    	grapeDao.create(entity);
    	grapeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(Grape entity) {
    	grapeDao.getSU().openCurrentSessionwithTransaction();
    	grapeDao.update(entity);
    	grapeDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public Grape getById(Long id) {
    	grapeDao.getSU().openCurrentSession();
    	Grape entity = grapeDao.getById(id);
    	grapeDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public Grape getGrapeByNm(String un) {
    	grapeDao.getSU().openCurrentSession();
    	Grape entity = grapeDao.findByNm(un);
    	grapeDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public void delete(Long id) {
    	grapeDao.getSU().openCurrentSessionwithTransaction();
    	Grape entity = grapeDao.getById(id);
    	grapeDao.delete(entity);
    	grapeDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<Grape> getAll() {
    	grapeDao.getSU().openCurrentSession();
    	List<Grape> entityList = grapeDao.getAll();
    	grapeDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	grapeDao.getSU().openCurrentSessionwithTransaction();
    	grapeDao.deleteAll();
    	grapeDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<Grape> OLgetAll() {
        ObservableList<Grape> entityList = FXCollections.observableArrayList();
        List<Grape> gList = getAll();
        for (Grape entity : gList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
