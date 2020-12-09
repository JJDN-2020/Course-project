package com.wapp.services;


import java.util.List;

import com.wapp.dao.UserDao;
import com.wapp.entities.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class UserService {
    private static UserDao userDao;
 
    public UserService() {
    	userDao = new UserDao();
    }
 
    public void create(User entity) {
    	userDao.getSU().openCurrentSessionwithTransaction();
    	userDao.create(entity);
    	userDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public void update(User entity) {
    	userDao.getSU().openCurrentSessionwithTransaction();
    	userDao.update(entity);
    	userDao.getSU().closeCurrentSessionwithTransaction();
    }
 
    public User getById(Long id) {
    	userDao.getSU().openCurrentSession();
    	User entity = userDao.getById(id);
    	userDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public User getUserByUn(String un) {
    	userDao.getSU().openCurrentSession();
    	User entity = userDao.findByUn(un);
    	userDao.getSU().closeCurrentSession();
        return entity;
    }
    
    public User getUserByUnPwd(String un, String pwd) {
    	userDao.getSU().openCurrentSession();
    	User entity = userDao.findByUnPwd(un, pwd);
    	userDao.getSU().closeCurrentSession();
        return entity;
    }
 
    public void delete(Long id) {
    	userDao.getSU().openCurrentSessionwithTransaction();
    	User entity = userDao.getById(id);
        userDao.delete(entity);
        userDao.getSU().closeCurrentSessionwithTransaction();
    }

    public List<User> getAll() {
    	userDao.getSU().openCurrentSession();
    	List<User> entityList = userDao.getAll();
        userDao.getSU().closeCurrentSession();
        return entityList;
    }
 
    public void deleteAll() {
    	userDao.getSU().openCurrentSessionwithTransaction();
    	userDao.deleteAll();
    	userDao.getSU().closeCurrentSessionwithTransaction();
    }
    

    public ObservableList<User> OLgetAll() {
        ObservableList<User> entityList = FXCollections.observableArrayList();
        List<User> uList = getAll();
        for (User entity : uList) {
        	entityList.add(entity);
        }
        return entityList;
    }
    
    /*
    public UserDao userDao() {
        return userDao;
    }
    */
}
