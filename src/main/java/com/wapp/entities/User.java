package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

//User_Data
@Entity
@Table(name = "USER_DATA_NEW")
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
	@SequenceGenerator(name="user_id", sequenceName = "user_idSEQ", allocationSize=1)
	@Column(name = "user_id", updatable = true, nullable = false)
	public Long userID;
	
	@Column(name = "username", length = 24, unique=true)
	public String username;
	
	@Column(name = "password", length = 48)
	public String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_ROLE_ID"))
	
    private UserRole userRole;
	
	public User() {}
	
	public User(String un, String pwd, UserRole urole) {
		this.username = un;
		this.password = pwd;
		this.userRole = urole;
	}
	
	public Long getId() {
	      return userID;
	}
	
	public void setID(Long uid) {
		this.userID = uid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String un) {
		this.username = un;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pwd) {
		this.password = pwd;
	}
	
	public UserRole getRole() {
		return userRole;
	}
	
	public void setRole(UserRole us) {
		this.userRole = us;
	}

	@Override
    public String toString() {
        return "User: " + this.userID + ", " + this.username + ", " + this.password + ", " + this.userRole.toString();
    }	
}
