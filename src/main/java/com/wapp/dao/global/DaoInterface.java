package com.wapp.dao.global;

import java.util.List;
 
public interface DaoInterface<T, Id>{
    public void create(T entity);
     
    public void update(T entity);
     
    public T getById(Id id);
     
    public void delete(T entity);
     
    public List<T> getAll();
     
    public void deleteAll();
}