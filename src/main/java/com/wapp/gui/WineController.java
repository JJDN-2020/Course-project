package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;

import com.wapp.entities.Wine;
import com.wapp.entities.WineRecipe;
import com.wapp.entities.nonpersistent.TableViewWine;
import com.wapp.services.WineRecipeService;
import com.wapp.services.WineService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class WineController implements Initializable{
		
	    @FXML
	    private AnchorPane apSubScene;
	
	    @FXML
	    private TableView<TableViewWine> tvWine;
	
	    @FXML
	    private TableColumn<TableViewWine, Long> colID;
	
	    @FXML
	    private TableColumn<TableViewWine, Integer> colQuantity;
	
	    @FXML
	    private TableColumn<TableViewWine, WineRecipe> colRecipe;
	    
	    @FXML
	    private TextField tfSearch;
	    
	    @FXML
	    private TextField tfID;
	
	    @FXML
	    private TextField tfQuantity;
	
	    @FXML
	    private ComboBox<String> cmbRecipe;
	    
	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    private WineService ws = new WineService();
		private WineRecipeService wrs = new WineRecipeService();
		private Map<Long, String> wrMap;

		private FilteredList<TableViewWine> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetWineData();
			resetRecipeData();
	    }
		
		public void resetWineData() {
			tvWine.getItems().clear();
			ObservableList<TableViewWine> observableList = FXCollections.observableArrayList();
			List<Wine> wines = ws.getAll();
			for(int i = 0; i < wines.size(); i ++){
				observableList.add(new TableViewWine(
				               new SimpleLongProperty(wines.get(i).getId()),
				               new SimpleDoubleProperty(wines.get(i).getQuantity()),
				               new SimpleStringProperty(wines.get(i).getRecipe().getName())));
				}
			filteredData = new FilteredList<TableViewWine>(observableList, e -> true);
			
			colID.setCellValueFactory(new PropertyValueFactory<>("id"));
			colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			colRecipe.setCellValueFactory(new PropertyValueFactory<>("wineRecipe"));
			
			tvWine.setItems(observableList);
		}
		
		public void resetRecipeData() {
			wrMap = new HashMap<>();
			wrs.OLgetAll().forEach(wr ->{
				cmbRecipe.getItems().add(wr.getName());
				try {wrMap.put(wr.recipeID, wr.recipeName);}
				catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewWine sWine = tvWine.getSelectionModel().getSelectedItem();
			if(sWine != null) {
				tfID.setText(sWine.getId().toString());
				tfQuantity.setText(sWine.getQuantity().toString());
				cmbRecipe.setValue(sWine.getWineRecipe());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfID.clear();
			tfQuantity.clear();
			cmbRecipe.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createWine(MouseEvent event) {
			Alert alert;
			String quantityText = tfQuantity.getText();
	            if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbRecipe.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a wine recipe!");
	                alert.show();
	            } else {
	            //logger.info(lUsernameWelcome + " attempting to create a new Wine "+wineID);
	            	
            	Wine newWine = new Wine();
            	newWine.setQuantity(Double.parseDouble(quantityText));
            	newWine.setRecipe(wrs.getWineRecipeByNm(cmbRecipe.getValue()));
	            boolean isCreated = checkAddWine(newWine);
	                if (isCreated) {
	                    alert = new Alert(Alert.AlertType.INFORMATION, "Wine created successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "Wine already exists!");
	                }
	            alert.show();
	            resetWineData();
	            }
        }
		
		public static boolean isParsable(String input) {
		    try {
		        Double.parseDouble(input);
		        return true;
		    } catch (final NumberFormatException e) {
		        return false;
		    }
		}
		
		public boolean checkAddWine(Wine wine) {
            try {
            	clearInputData();
            	ws.create(wine);
                return true;
            }
            catch(Exception  e) {System.out.println(e); clearInputData();	return false;}
		}
		
		@FXML
		public void updateWine(MouseEvent event) {
			Alert alert;
			String idText = tfID.getText();
			String quantityText = tfQuantity.getText();
				if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
				} else if (cmbRecipe.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a wine recipe!");
	                alert.show();
	            } else if (tvWine.getSelectionModel().getSelectedItem() == null){
	            	clearInputData();
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose an existing record from table!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to update Wine "+nameText);
	            	
	            	Wine newWine = new Wine();
	            	newWine.setQuantity(Double.parseDouble(quantityText));
	            	newWine.setRecipe(wrs.getWineRecipeByNm(cmbRecipe.getValue()));
	            	newWine.setId(Long.parseLong(idText));
	            	
		            boolean isUpdated = checkUpdateWine(newWine);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Wine updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetWineData();
	            }
        }		
			
		public boolean checkUpdateWine(Wine wine) {
            try {
            	clearInputData();
            	ws.update(wine);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeWine(MouseEvent event) {
			Alert alert;
			String idText = tfID.getText();
			String quantityText = tfQuantity.getText();
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete wine?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbRecipe.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a wine recipe!");
	                alert.show();
	            } else if (ws.getById(Long.parseLong(idText)) == null) {
	            	//.getWineByNm(nameText) == null
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Type or select an existing record!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to delete User "+usernameText);
	            	Wine newWine = new Wine();
	            	newWine.setQuantity(Double.parseDouble(quantityText));
	            	newWine.setRecipe(wrs.getWineRecipeByNm(cmbRecipe.getValue()));
	            	newWine.setId(Long.parseLong(idText));
	            boolean isDeleted = checkRemoveWine(newWine);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "Wine deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetWineData();
	            }
			}
		}
		
		public boolean checkRemoveWine(Wine wine) {
            try {
            	clearInputData();
            	ws.delete(wine.getId());
                return true;
            }
            catch(HibernateException  e) {System.out.println(e);	return false;}
		}
		
		@FXML
		public void onClearLabels(MouseEvent event) {
			clearInputData();
		}
		
		@FXML
	    void searchWineByID(KeyEvent event) {
			tfSearch.textProperty().addListener((o, ov, nv) -> {
				filteredData.setPredicate( (TableViewWine tvg) -> {
					String newVal = nv.toString();
					return tvg.getId().toString().contains(newVal);
				});
			});
			ObservableList<TableViewWine> osData = FXCollections.observableArrayList(filteredData);
			tvWine.setItems(osData);

	    }
}
