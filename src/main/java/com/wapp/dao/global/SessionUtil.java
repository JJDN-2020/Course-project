package com.wapp.dao.global;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
	public Session currentSession;
    public Transaction currentTransaction;
    
	public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
 
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
     
    public void closeCurrentSession() {
        currentSession.close();
    }
     
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
     
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
        configuration.addAnnotatedClass(com.wapp.entities.User.class);
        configuration.addAnnotatedClass(com.wapp.entities.UserRole.class);
        configuration.addAnnotatedClass(com.wapp.entities.Grape.class);
        configuration.addAnnotatedClass(com.wapp.entities.GrapeVariety.class);
        configuration.addAnnotatedClass(com.wapp.entities.Bottle.class);
        configuration.addAnnotatedClass(com.wapp.entities.BottleSize.class);
        configuration.addAnnotatedClass(com.wapp.entities.Wine.class);
        configuration.addAnnotatedClass(com.wapp.entities.WineRecipe.class);
        configuration.addAnnotatedClass(com.wapp.entities.Bottling.class);
        
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
 
    public Session getCurrentSession() {
        return currentSession;
    }
 
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
 
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
 
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
}
