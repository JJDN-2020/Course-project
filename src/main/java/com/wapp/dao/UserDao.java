package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.UserDaoInterface;
import com.wapp.entities.User;
 
public class UserDao implements UserDaoInterface<User, String>, DaoInterface<User, Long> { 
	private static SessionUtil su;
	
	public UserDao(){
		su = new SessionUtil();
	}
 
    public void create(User entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(User entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public User getById(Long id) {
    	User entity = (User) su.getCurrentSession().get(User.class, id);
        return entity; 
    }
    
    @SuppressWarnings("unchecked")
    public User findByUn(String u) {
    	List<User> entityList = (List<User>) su.getCurrentSession().createQuery("SELECT U FROM User U WHERE U.username = :un").setParameter("un",  u).list();
    	User entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {}
    	
    	return null;
    }
    
    @SuppressWarnings("unchecked")
    public User findByUnPwd(String u, String p) {
    	List<User> entityList = (List<User>) su.getCurrentSession().createQuery("SELECT U FROM User U WHERE U.username = :un AND U.password = :pwd").setParameter("un",  u).setParameter("pwd", p).list();
    	User entity = null;
    	try {
    		entity = entityList.get(0);
            return entity;
        } catch (Exception e) {}
    	
    	return null;
    }
 
    public void delete(User entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
    	List<User> entityList = (List<User>) su.getCurrentSession().createQuery("FROM User U ORDER BY U.userID DESC").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<User> entityList = getAll();
        for (User entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
