package com.wapp.services;


import java.util.List;

import com.wapp.dao.UserRoleDao;
import com.wapp.entities.UserRole;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class UserRoleService {
    private static UserRoleDao userRoleDao;
 
    public UserRoleService() {
    	userRoleDao = new UserRoleDao();
    }
 
    public void create(UserRole entity) {
    	userRoleDao.getSU().openCurrentSessionwithTransaction();
    	userRoleDao.create(entity);
    	userRoleDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(UserRole entity) {
    	userRoleDao.getSU().openCurrentSessionwithTransaction();
    	userRoleDao.update(entity);
    	userRoleDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public UserRole getById(Long id) {
    	userRoleDao.getSU().openCurrentSession();
    	UserRole entity = userRoleDao.getById(id);
    	userRoleDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public UserRole getByName(String name) {
    	userRoleDao.getSU().openCurrentSession();
    	UserRole entity = userRoleDao.getByName(name);
    	userRoleDao.getSU().closeCurrentSession();
        return entity;
    }
 
    public void delete(Long id) {
    	userRoleDao.getSU().openCurrentSessionwithTransaction();
    	UserRole entity = userRoleDao.getById(id);
    	userRoleDao.delete(entity);
    	userRoleDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public List<UserRole> getAll() {
    	userRoleDao.getSU().openCurrentSession();
        List<UserRole> entityList = userRoleDao.getAll();
        userRoleDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	userRoleDao.getSU().openCurrentSessionwithTransaction();
    	userRoleDao.deleteAll();
    	userRoleDao.getSU().closeCurrentSessionwithTransaction();
    }
    
    public ObservableList<UserRole> OLgetAll() {
        ObservableList<UserRole> entityList = FXCollections.observableArrayList();
        List<UserRole> urList = getAll();
        for (UserRole entity : urList) {
        	entityList.add(entity);
        }
        return entityList;
    }
}
