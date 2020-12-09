package com.wapp.services;


import java.util.List;

import com.wapp.dao.GrapeVarietyDao;
import com.wapp.entities.GrapeVariety;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class GrapeVarietyService {
    private static GrapeVarietyDao grapeVarietyDao;
 
    public GrapeVarietyService() {
    	grapeVarietyDao = new GrapeVarietyDao();
    }
 
    public void create(GrapeVariety entity) {
    	grapeVarietyDao.getSU().openCurrentSessionwithTransaction();
    	grapeVarietyDao.create(entity);
    	grapeVarietyDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(GrapeVariety entity) {
    	grapeVarietyDao.getSU().openCurrentSessionwithTransaction();
    	grapeVarietyDao.update(entity);
    	grapeVarietyDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public GrapeVariety getById(Long id) {
    	grapeVarietyDao.getSU().openCurrentSession();
    	GrapeVariety entity = grapeVarietyDao.getById(id);
    	grapeVarietyDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public GrapeVariety getByName(String name) {
    	grapeVarietyDao.getSU().openCurrentSession();
    	GrapeVariety entity = grapeVarietyDao.getByName(name);
    	grapeVarietyDao.getSU().closeCurrentSession();
        return entity;
    }
 
    public void delete(Long id) {
    	grapeVarietyDao.getSU().openCurrentSessionwithTransaction();
    	GrapeVariety entity = grapeVarietyDao.getById(id);
    	grapeVarietyDao.delete(entity);
    	grapeVarietyDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public List<GrapeVariety> getAll() {
    	grapeVarietyDao.getSU().openCurrentSession();
        List<GrapeVariety> entityList = grapeVarietyDao.getAll();
        grapeVarietyDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	grapeVarietyDao.getSU().openCurrentSessionwithTransaction();
    	grapeVarietyDao.deleteAll();
    	grapeVarietyDao.getSU().closeCurrentSessionwithTransaction();
    }
    
    public ObservableList<GrapeVariety> OLgetAll() {
        ObservableList<GrapeVariety> entityList = FXCollections.observableArrayList();
        List<GrapeVariety> gvList = getAll();
        for (GrapeVariety entity : gvList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
