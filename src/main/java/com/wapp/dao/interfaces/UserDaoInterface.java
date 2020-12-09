package com.wapp.dao.interfaces;
 
public interface UserDaoInterface<T, Id> {
    public T findByUnPwd(String u, String p);     
}