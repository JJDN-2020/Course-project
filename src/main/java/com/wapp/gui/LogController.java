package com.wapp.gui;

import java.io.IOException;

import com.wapp.entities.User;
import com.wapp.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LogController{
	
	@FXML
	public Button logButton = new Button();
	
	@FXML
	public TextField un = new TextField();
	
	@FXML
	private PasswordField pwd = new PasswordField();
	
	@FXML	
	public void loginCheck(ActionEvent event) throws IOException {
		User u = us.getUserByUnPwd(un.getText(), pwd.getText());
		if(	u!= null) {	
			Stage stage = (Stage) logButton.getScene().getWindow();
		    stage.close();
		    String fxmlFile = "";
		    if(u.getRole().getId() == 1) fxmlFile = "admin";
		    else if(u.getRole().getId() == 2) fxmlFile = "winemaker";
		    else if(u.getRole().getId() == 3) fxmlFile = "warehouseManager";
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+ fxmlFile + "Panel.fxml"));
	        Parent root = loader.load();
	        GlobalController ac = loader.getController();
	        ac.setUser(u);
	        stage.setScene(new Scene(root));
	        stage.show();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Username or password is incorrect!");
			alert.setContentText("Please enter your details correctly!");
			alert.showAndWait();
		}
	}
	
	UserService us = new UserService();
}
