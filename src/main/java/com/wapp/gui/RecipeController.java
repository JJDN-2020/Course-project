package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;

import com.wapp.entities.WineRecipe;
import com.wapp.entities.nonpersistent.TableViewRecipe;
import com.wapp.services.GrapeService;
import com.wapp.services.WineRecipeService;

import javafx.beans.property.SimpleDoubleProperty;
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


public class RecipeController implements Initializable{
		
	    @FXML
	    private AnchorPane apSubScene;
	
	    @FXML
	    private TableView<TableViewRecipe> tvRecipe;
	
	    @FXML
	    private TableColumn<TableViewRecipe, String> colName;
	
	    @FXML
	    private TableColumn<TableViewRecipe, String> colFirstGrape;
	
	    @FXML
	    private TableColumn<TableViewRecipe, Double> colQuantityFirst;
	
	    @FXML
	    private TableColumn<TableViewRecipe, String> colSecondGrape;
	
	    @FXML
	    private TableColumn<TableViewRecipe, Double> colQuantitySecond;
	    
	    @FXML
	    private TextField tfSearch;
	
	    @FXML
	    private TextField tfName;
	    
	    @FXML
	    private ComboBox<String> cmbFirstGrape;
	    
	    @FXML
	    private TextField tfQuantityFirst;
	    
	    @FXML
	    private ComboBox<String> cmbSecondGrape;
	    
	    @FXML
	    private TextField tfQuantitySecond;
	    
	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    private GrapeService gs = new GrapeService();
		private WineRecipeService wrs = new WineRecipeService();
		private Map<Long, String> gMap1;
		private Map<Long, String> gMap2;
		private FilteredList<TableViewRecipe> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetRecipeData();
			resetGrapeData();
	    }
		
		public void resetRecipeData() {
			tvRecipe.getItems().clear();
			ObservableList<TableViewRecipe> observableList = FXCollections.observableArrayList();
			List<WineRecipe> recipes = wrs.getAll();
			for(int i = 0; i < recipes.size(); i ++){
				System.out.println("Test grape name ="+recipes.get(i).getName());
				observableList.add(new TableViewRecipe(
				               new SimpleStringProperty(recipes.get(i).getName()),
				               new SimpleStringProperty(recipes.get(i).getGrapeF().getName()),
				               new SimpleDoubleProperty(recipes.get(i).getQuantityF()),
				               new SimpleStringProperty(recipes.get(i).getGrapeS().getName()),
				               new SimpleDoubleProperty(recipes.get(i).getQuantityS())));
				}
			filteredData = new FilteredList<TableViewRecipe>(observableList, e -> true);
			
			colName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colFirstGrape.setCellValueFactory(new PropertyValueFactory<>("grapeNameFirst"));
			colQuantityFirst.setCellValueFactory(new PropertyValueFactory<>("quantityFirst"));	
			colSecondGrape.setCellValueFactory(new PropertyValueFactory<>("grapeNameSecond"));
			colQuantitySecond.setCellValueFactory(new PropertyValueFactory<>("quantitySecond"));
			
			tvRecipe.setItems(observableList);
		}
		
		public void resetGrapeData() {
			gMap1 = new HashMap<>();
			gMap2 = new HashMap<>();
			gs.OLgetAll().forEach(g ->{
				cmbFirstGrape.getItems().add(g.getName());
				cmbSecondGrape.getItems().add(g.getName());
				try {gMap1.put(g.grapeID, g.grapeName);}
				catch (Exception e) {System.out.println(e);}
				try {gMap2.put(g.grapeID, g.grapeName);}
				catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewRecipe sRecipe = tvRecipe.getSelectionModel().getSelectedItem();
			if(sRecipe != null) {
				tfName.setText(sRecipe.getName());
				cmbFirstGrape.setValue(sRecipe.getGrapeNameFirst());
				tfQuantityFirst.setText(sRecipe.getQuantityFirst().toString());
				cmbSecondGrape.setValue(sRecipe.getGrapeNameSecond());
				tfQuantitySecond.setText(sRecipe.getQuantitySecond().toString());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfName.clear();
			tfQuantityFirst.clear();
			tfQuantitySecond.clear();
			cmbFirstGrape.getSelectionModel().clearSelection();
			cmbSecondGrape.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createRecipe(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityFirstText = tfQuantityFirst.getText();
			String quantitySecondText = tfQuantitySecond.getText();
	            if (nameText.isEmpty() || nameText.length() < 4 || nameText.length() > 48) {
	            	tfName.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The name must be between 4 and 48 characters long!");
	                alert.show();
	            } else if (cmbFirstGrape.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a first grape!");
	                alert.show();
	            } else if (quantityFirstText.isEmpty() || isParsable(quantityFirstText) == false)  {
	            	tfQuantityFirst.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The first quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbSecondGrape.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a second grape!");
	                alert.show();
	            } else if (quantitySecondText.isEmpty() || isParsable(quantityFirstText) == false)  {
	            	tfQuantitySecond.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The second quantity record must be a number and must not be empty!");
	                alert.show();
	            } else {
	            //logger.info(lUsernameWelcome + " attempting to create a new Grape "+nameText);
	            	WineRecipe newRecipe = new WineRecipe();
	            	newRecipe.setName(nameText);
	            	newRecipe.setQuantityF(Double.parseDouble(quantityFirstText));
	            	newRecipe.setGrapeF(gs.getGrapeByNm(cmbFirstGrape.getValue()));
	            	newRecipe.setQuantityS(Double.parseDouble(quantitySecondText));
	            	newRecipe.setGrapeS(gs.getGrapeByNm(cmbSecondGrape.getValue()));
		            boolean isCreated = checkAddRecipe(newRecipe);
		                if (isCreated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Recipe created successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "Recipe already exists!");
		                }
		            alert.show();
		            resetRecipeData();
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
		
		public boolean checkAddRecipe(WineRecipe recipe) {
            try {
            	clearInputData();
            	wrs.create(recipe);
                return true;
            }
            catch(Exception  e) {System.out.println(e); clearInputData();	return false;}
		}
		
		
		@FXML
		public void updateRecipe(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityFirstText = tfQuantityFirst.getText();
			String quantitySecondText = tfQuantitySecond.getText();
			TableViewRecipe sRecipe = tvRecipe.getSelectionModel().getSelectedItem();
			if (nameText.isEmpty() || nameText.length() < 4 || nameText.length() > 48) {
            	tfName.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The name must be between 4 and 48 characters long!");
                alert.show();
            } else if (cmbFirstGrape.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a first grape!");
                alert.show();
            } else if (quantityFirstText.isEmpty() || isParsable(quantityFirstText) == false)  {
            	tfQuantityFirst.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The first quantity record must be a number and must not be empty!");
                alert.show();
            } else if (cmbSecondGrape.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a second grape!");
                alert.show();
            } else if (quantitySecondText.isEmpty() || isParsable(quantityFirstText) == false)  {
            	tfQuantitySecond.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The second quantity record must be a number and must not be empty!");
                alert.show();
            } else if (tvRecipe.getSelectionModel().getSelectedItem() == null){
		            	clearInputData();
		            	alert = new Alert(Alert.AlertType.ERROR);
		                alert.setContentText("Choose an existing record from table!");
		                alert.show();
		    } else {
	            	//logger.info(lUsernameWelcome + " attempting to update Grape "+nameText);
		        	WineRecipe newRecipe = new WineRecipe();
	            	newRecipe.setName(nameText);
	            	newRecipe.setQuantityF(Double.parseDouble(quantityFirstText));
	            	newRecipe.setQuantityS(Double.parseDouble(quantitySecondText));
	            	newRecipe.setGrapeF(gs.getGrapeByNm(cmbFirstGrape.getValue().toString()));
	            	newRecipe.setGrapeS(gs.getGrapeByNm(cmbSecondGrape.getValue().toString()));
	            	newRecipe.setID(wrs.getWineRecipeByNm(sRecipe.getName()).getId());
	            	
		            boolean isUpdated = checkUpdateRecipe(newRecipe);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Recipe "+nameText+" updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetRecipeData();
	            }
        }		
			
		public boolean checkUpdateRecipe(WineRecipe recipe) {
            try {
            	clearInputData();
            	wrs.update(recipe);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeRecipe(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityFirstText = tfQuantityFirst.getText();
			String quantitySecondText = tfQuantitySecond.getText();
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete "+nameText+"?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (nameText.isEmpty() || nameText.length() < 4 || nameText.length() > 48) {
	            	tfName.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The name must be between 4 and 48 characters long!");
	                alert.show();
	            } else if (cmbFirstGrape.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a first grape!");
	                alert.show();
	            } else if (quantityFirstText.isEmpty() || isParsable(quantityFirstText) == false)  {
	            	tfQuantityFirst.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The first quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbSecondGrape.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a second grape!");
	                alert.show();
	            } else if (quantitySecondText.isEmpty() || isParsable(quantityFirstText) == false)  {
	            	tfQuantitySecond.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The second quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (wrs.getWineRecipeByNm(nameText) == null) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Type or select an existing record!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to delete User "+usernameText);
	            	WineRecipe newRecipe = new WineRecipe();
	            	newRecipe.setName(nameText);
	            	newRecipe.setQuantityF(Double.parseDouble(quantityFirstText));
	            	newRecipe.setQuantityS(Double.parseDouble(quantitySecondText));
	            	newRecipe.setGrapeF(gs.getGrapeByNm(cmbFirstGrape.getValue()));
	            	newRecipe.setGrapeS(gs.getGrapeByNm(cmbSecondGrape.getValue()));
	            	newRecipe.setID(wrs.getWineRecipeByNm(nameText).getId());
	            boolean isDeleted = checkRemoveRecipe(newRecipe);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "Recipe deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetRecipeData();
	            }
			}
		}
		
		public boolean checkRemoveRecipe(WineRecipe recipe) {
            try {
            	clearInputData();
            	wrs.delete(recipe.getId());
                return true;
            }
            catch(HibernateException  e) {System.out.println(e);	return false;}
		}
		
		@FXML
		public void onClearLabels(MouseEvent event) {
			clearInputData();
		}
		
		@FXML
	    void searchGrapeByName(KeyEvent event) {
			tfSearch.textProperty().addListener((o, ov, nv) -> {
				filteredData.setPredicate( (TableViewRecipe tvr) -> {
					String newVal = nv.toString();
					return tvr.getName().contains(newVal);
				});
			});
			ObservableList<TableViewRecipe> osData = FXCollections.observableArrayList(filteredData);
			tvRecipe.setItems(osData);

	    }
}
