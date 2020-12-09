package com.wapp.gui;

import java.io.IOException;

import com.wapp.entities.User;
import com.wapp.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class WinemakerController {
	
	@FXML
    private BorderPane borderPane;

    @FXML
    private ImageView homeIcon;

    @FXML
    private Label usernameText;

    @FXML
    private ImageView exitIcon;

    @FXML
    private Pane mGrape;
    
    @FXML
    private Pane mRecipes;
    
    @FXML
    private Pane mWine;
    
    @FXML
    private Pane mBottles;

    @FXML
    private Pane mBottling;

    @FXML
    private AnchorPane homePage;

    @FXML
    private Label usernameWelcome;

    @FXML
    void adminPanel(MouseEvent event) throws IOException{
    	borderPane.setCenter(homePage);
    }
    
    @FXML
    void logOut(MouseEvent event) throws IOException{
    	Stage stage = (Stage) exitIcon.getScene().getWindow();
	    stage.close();
	    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/logPanel.fxml")));
	    stage.setScene(scene);
	    stage.show();
    }
    
    @FXML
    void mGrapeClick(MouseEvent event) {
    	loadPage("grapePage");
    }
    
    @FXML
    void mBottleClick(MouseEvent event) {
    	loadPage("bottlePage");
    }
    
    @FXML
    void mRecipeClick(MouseEvent event) {
    	loadPage("recipePage");
    }
    
    @FXML
    void mWineClick(MouseEvent event) {
    	loadPage("winePage");
    }
    
    @FXML
    void mBottlingClick(MouseEvent event) {
    	loadPage("bottlingPage");
    }
    
    private User buffUser;
	private UserService us = new UserService();
    
    private void loadPage(String page){
    	Parent parent = null;
    	try {
    		parent = FXMLLoader.load(getClass().getResource("/fxml/"+page+".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	borderPane.setCenter(parent);
    	
    }
    
    
    
    public void setUser(User u) {
    	this.buffUser = us.getUserByUn(u.getUsername());
    	this.usernameWelcome.setText(buffUser.username);
    	this.usernameText.setText(buffUser.username);
    }
}
