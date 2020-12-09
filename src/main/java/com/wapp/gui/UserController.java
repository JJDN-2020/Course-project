package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.hibernate.HibernateException;

import com.wapp.entities.User;
import com.wapp.entities.UserRole;
import com.wapp.entities.nonpersistent.TableViewUser;
import com.wapp.services.UserRoleService;
import com.wapp.services.UserService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UserController implements Initializable{
	
	    @FXML
	    private AnchorPane apSubScene;
	    
	    @FXML
	    private TableView<TableViewUser> tvUser;
	    
	    @FXML
	    private TableColumn<TableViewUser, String> colUsername;
	    
	    @FXML
	    private TableColumn<TableViewUser, String> colPassword;
	    
	    @FXML
	    private TableColumn<TableViewUser, UserRole> colRole;
	    
	    @FXML
	    private TextField tfSearch;
	    
	    @FXML
	    private TextField tfUsername;

	    @FXML
	    private PasswordField pfPassword;
	    
	    @FXML
	    private ComboBox<String> cmbRole;

	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    private UserService us = new UserService();
		private UserRoleService urs = new UserRoleService();
		private Map<Long, String> urMap;
		private FilteredList<TableViewUser> filteredData;
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetUserData();
			resetRoleData();
	    }
		
		public void resetUserData() {
			tvUser.getItems().clear();
			
			ObservableList<TableViewUser> observableList = FXCollections.observableArrayList();
			List<User> users = us.getAll();
			for(int i = 0; i < users.size(); i ++){
				observableList.add(new TableViewUser(
				               new SimpleStringProperty(users.get(i).getUsername()),
				               new SimpleStringProperty(users.get(i).getPassword()),
				               new SimpleStringProperty(users.get(i).getRole().getRoleName())));
				}
			
			filteredData = new FilteredList<TableViewUser>(observableList, e -> true);
			
			colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
			colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
			colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
				
			tvUser.setItems(observableList);
		}
		
		public void resetRoleData() {
			urMap = new HashMap<>();
			urs.OLgetAll().forEach(ur ->{
				cmbRole.getItems().add(ur.getRoleName());
				try {urMap.put(ur.RoleID, ur.RoleName);}
				catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewUser sUser = tvUser.getSelectionModel().getSelectedItem();
			if(sUser != null) {
				tfUsername.setText(sUser.getUsername());
				pfPassword.setText(sUser.getPassword());
				cmbRole.setValue(sUser.getRole());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfUsername.clear();
			pfPassword.clear();
			cmbRole.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createUser(MouseEvent event) {
			Alert alert;
			String usernameText = tfUsername.getText();
			String passwordText = pfPassword.getText();
	            if (usernameText.isEmpty() || usernameText.length() < 3 || usernameText.length() > 24) {
	            	tfUsername.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The username must be between 3 and 24 characters long!");
	                alert.show();
	            } else if (passwordText.isEmpty() || passwordText.length() < 6 || passwordText.length() > 48) {
	            	pfPassword.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The password must be between 6 and 48 characters long!");
	                alert.show();
	            } else if (cmbRole.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a user role!");
	                alert.show();
	            } else {
	            logger.info("Attempt to create a new User "+usernameText);
	            	
            	User newUser = new User();
            	newUser.setUsername(usernameText);
            	newUser.setPassword(passwordText);
            	newUser.setRole(urs.getByName(cmbRole.getValue()));
            	
	            boolean isCreated = checkAddUser(newUser);
	                if (isCreated) {
	                    alert = new Alert(Alert.AlertType.INFORMATION, "User created successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "Username already exists!");
	                }
	            alert.show();
	            resetUserData();
	            }
        }
		
		public boolean checkAddUser(User user) {
            try {
            	clearInputData();
            	us.create(user);
                return true;
            }
            catch(Exception  e) {clearInputData();	return false;}
		}
		
		@FXML
		public void updateUser(MouseEvent event) {
			Alert alert;
			String usernameText = tfUsername.getText();
			String passwordText = pfPassword.getText();
			TableViewUser sUser = tvUser.getSelectionModel().getSelectedItem();
				if (usernameText.isEmpty() || usernameText.length() < 3 || usernameText.length() > 24) {
	            	tfUsername.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The username must be between 3 and 24 characters long!");
	                alert.show();
	            } else if (passwordText.isEmpty() || passwordText.length() < 6 || passwordText.length() > 48) {
	            	pfPassword.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The password must be between 6 and 48 characters long!");
	                alert.show();
	            } else if (cmbRole.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a user role!");
	                alert.show();
	            } else if (tvUser.getSelectionModel().getSelectedItem() == null){
	            	clearInputData();
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose an existing record from table!");
	                alert.show();
	            } else {
	            	logger.info("Attempt to update User "+usernameText);
	            	
	            	User newUser = new User();
	            	newUser.setUsername(usernameText);
	            	newUser.setPassword(passwordText);
	            	newUser.setRole(urs.getByName(cmbRole.getValue()));
	            	newUser.setID(us.getUserByUnPwd(sUser.getUsername(), sUser.getPassword()).getId());
	            	
		            boolean isUpdated = checkUpdateUser(newUser);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "User "+usernameText+" updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetUserData();
	            }
        }		
			
		public boolean checkUpdateUser(User user) {
            try {
            	clearInputData();
            	us.update(user);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeUser(MouseEvent event) {
			Alert alert;
			String usernameText = tfUsername.getText();
			String passwordText = pfPassword.getText();
			
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete "+usernameText+"?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (usernameText.isEmpty() || usernameText.length() < 3 || usernameText.length() > 24) {
	            	tfUsername.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The username must be between 3 and 24 characters long!");
	                alert.show();
	            } else if (passwordText.isEmpty() || passwordText.length() < 6 || passwordText.length() > 48) {
	            	pfPassword.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The password must be between 6 and 48 characters long!");
	                alert.show();
	            } else if (cmbRole.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a user role!");
	                alert.show();
	            } else if (us.getUserByUnPwd(usernameText, passwordText) == null) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Type or select an existing record!");              alert.show();
	            } else {
	            	logger.info("Attempt to delete User "+usernameText);
	            	User newUser = new User();
	            	newUser.setUsername(usernameText);
	            	newUser.setPassword(passwordText);
	            	newUser.setRole(urs.getByName(cmbRole.getValue()));
	            	newUser.setID(us.getUserByUnPwd(usernameText, passwordText).getId());
	            boolean isDeleted = checkRemoveUser(newUser);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetUserData();
	            }
			}
		}
		
		public boolean checkRemoveUser(User user) {
            try {
            	clearInputData();
            	us.delete(user.getId());
                return true;
            }
            catch(HibernateException  e) {System.out.println(e);	return false;}
		}
		
		@FXML
		public void onClearLabels(MouseEvent event) {
			clearInputData();
		}
		
		@FXML
	    void searchUser(KeyEvent event) {
			tfSearch.textProperty().addListener((o, ov, nv) -> {
				filteredData.setPredicate( (TableViewUser tvu) -> {
					String newVal = nv.toString();
					return tvu.getUsername().contains(newVal);
				});
			});
			ObservableList<TableViewUser> osData = FXCollections.observableArrayList(filteredData);
			tvUser.setItems(osData);

	    }
}
