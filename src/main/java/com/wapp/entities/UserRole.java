package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
@Table(name = "USER_ROLE")
public class UserRole {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id")
	@SequenceGenerator(name="role_id", sequenceName = "role_idSEQ", allocationSize=1)
	@Column(name = "role_id", updatable = true, nullable = false)
	public Long RoleID;
	
	@Column(name = "role_name", length = 30)
	public String RoleName;
	
	//@OneToMany(mappedBy = "userRole")
	//private List<User> users;
	
	public UserRole() {}
	
	public UserRole(String rn) {
		this.RoleName = rn;
	}
	
	public UserRole(Long rid, String rn) {
		this.RoleID = rid;
		this.RoleName = rn;
	}
	
	public Long getId() {
	      return RoleID;
	}
	
	public void setID(Long rid) {
		this.RoleID = rid;
	}
	
	public String getRoleName() {
		return RoleName;
	}
	
	public void setRoleName(String rn) {
		this.RoleName = rn;
	}
	
	/*
	public List<User> getUsers() {
		return users;
	}
	
	public void setRoleName(List<User> users) {
		this.users = users;
	}
	*/
	
	
	@Override
    public String toString() {
        return RoleName;
    }
}
