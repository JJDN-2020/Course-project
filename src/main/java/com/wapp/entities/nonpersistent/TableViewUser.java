package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleStringProperty;

public class TableViewUser {

	private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty role;

    public TableViewUser() {}
	
	public TableViewUser(SimpleStringProperty ssp, SimpleStringProperty ssp2, SimpleStringProperty ssp3) {
			this.setUsername(ssp);
			this.setPassword(ssp2);
			this.setRole(ssp3);
		}
	public String getUsername() {
		return username.get();
	}

	public void setUsername(SimpleStringProperty ssp) {
		this.username = ssp;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(SimpleStringProperty ssp) {
		this.password = ssp;
	}

	public String getRole() {
		return role.get();
	}

	public void setRole(SimpleStringProperty ssp) {
		this.role = ssp;
	}

}
