package com.wapp.dao;

import java.util.List;

import com.wapp.dao.global.DaoInterface;
import com.wapp.dao.global.SessionUtil;
import com.wapp.dao.interfaces.UserRoleDaoInterface;
import com.wapp.entities.UserRole;
 
public class UserRoleDao implements UserRoleDaoInterface<UserRole, String>, DaoInterface<UserRole, Long> { 
	private static SessionUtil su;
	
	public UserRoleDao(){
		su = new SessionUtil();
	}
 
    public void create(UserRole entity) {
    	su.getCurrentSession().save(entity);
    }
 
    public void update(UserRole entity) {
    	su.getCurrentSession().update(entity);
    }
 
    public UserRole getById(Long id) {
    	UserRole entity = (UserRole) su.getCurrentSession().get(UserRole.class, id);
        return entity; 
    }
    
    public UserRole getByName(String name) {
        	@SuppressWarnings("unchecked")
			List<UserRole> entityList = (List<UserRole>) su.getCurrentSession().createQuery("SELECT UR FROM UserRole UR WHERE UR.RoleName = :rn").setParameter("rn",  name).list();
        	UserRole entity = null;
        	try {
        		entity = entityList.get(0);
                return entity;
            } catch (Exception e) {System.out.println("Exception in UserRoleDao.getByName(String) "+e);}
        	
        	return null;
    }
    
    public void delete(UserRole entity) {
    	su.getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<UserRole> getAll() {
    	System.out.println();
        List<UserRole> entityList = (List<UserRole>) su.getCurrentSession().createQuery("from UserRole").list();
        return entityList;
    }
 
    public void deleteAll() {
        List<UserRole> entityList = getAll();
        for (UserRole entity : entityList) {
            delete(entity);
        }
    }
	
	public SessionUtil getSU() {
		return su;
	}
}
