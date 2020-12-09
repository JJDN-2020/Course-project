package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;

import com.wapp.entities.Grape;
import com.wapp.entities.GrapeVariety;
import com.wapp.entities.nonpersistent.TableViewGrape;
import com.wapp.services.GrapeService;
import com.wapp.services.GrapeVarietyService;

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

public class GrapeController implements Initializable{
		
	    @FXML
	    private AnchorPane apSubScene;
	
	    @FXML
	    private TableView<TableViewGrape> tvGrape;
	
	    @FXML
	    private TableColumn<TableViewGrape, String> colName;
	
	    @FXML
	    private TableColumn<TableViewGrape, GrapeVariety> colVariety;
	
	    @FXML
	    private TableColumn<TableViewGrape, String> colQuantity;
	
	    @FXML
	    private TableColumn<TableViewGrape, String> colAmmountWine;
	    
	    @FXML
	    private TextField tfSearch;
	
	    @FXML
	    private TextField tfName;
	    
	    @FXML
	    private TextField tfQuantity;
	
	    @FXML
	    private TextField tfAmmountWine;
	    
	    @FXML
	    private ComboBox<String> cmbVariety;
	    
	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    private GrapeService gs = new GrapeService();
		private GrapeVarietyService gvs = new GrapeVarietyService();
		private Map<Long, String> gvMap;
		private FilteredList<TableViewGrape> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetGrapeData();
			resetVarietyData();
	    }
		
		public void resetGrapeData() {
			tvGrape.getItems().clear();
			ObservableList<TableViewGrape> observableList = FXCollections.observableArrayList();
			List<Grape> grapes = gs.getAll();
			for(int i = 0; i < grapes.size(); i ++){
				observableList.add(new TableViewGrape(
				               new SimpleStringProperty(grapes.get(i).getName()),
				               new SimpleStringProperty(grapes.get(i).getVariety().getVarietyName()),
				               new SimpleDoubleProperty(grapes.get(i).getQuantity()),
				               new SimpleDoubleProperty(grapes.get(i).getWinePerKG())));
				}
			filteredData = new FilteredList<TableViewGrape>(observableList, e -> true);
			
			colName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			colAmmountWine.setCellValueFactory(new PropertyValueFactory<>("ammountWine"));	
			colVariety.setCellValueFactory(new PropertyValueFactory<>("variety"));
			
			tvGrape.setItems(observableList);
		}
		
		public void resetVarietyData() {
			gvMap = new HashMap<>();
			gvs.OLgetAll().forEach(gv ->{
				cmbVariety.getItems().add(gv.getVarietyName());
				try {gvMap.put(gv.varietyID, gv.varietyName);}
				catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewGrape sGrape = tvGrape.getSelectionModel().getSelectedItem();
			if(sGrape != null) {
				tfName.setText(sGrape.getName());
				tfQuantity.setText(sGrape.getQuantity().toString());
				tfAmmountWine.setText(sGrape.getAmmountWine().toString());
				cmbVariety.setValue(sGrape.getVariety());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfName.clear();
			tfQuantity.clear();
			tfAmmountWine.clear();
			cmbVariety.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createGrape(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityText = tfQuantity.getText();
			String ammountWineText = tfAmmountWine.getText();
	            if (nameText.isEmpty() || nameText.length() < 2 || nameText.length() > 48) {
	            	tfName.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The name must be between 2 and 48 characters long!");
	                alert.show();
	            } else if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (ammountWineText.isEmpty() || isParsable(ammountWineText) == false) {
	            	tfAmmountWine.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The ammount wine record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbVariety.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a grape variety!");
	                alert.show();
	            } else {
	            //logger.info(lUsernameWelcome + " attempting to create a new Grape "+nameText);
	            	
            	Grape newGrape = new Grape();
            	newGrape.setName(nameText);
            	newGrape.setQuantity(Double.parseDouble(quantityText));
            	newGrape.setWinePerKG(Double.parseDouble(ammountWineText));
            	newGrape.setVariety(gvs.getByName(cmbVariety.getValue()));
	            boolean isCreated = checkAddGrape(newGrape);
	                if (isCreated) {
	                    alert = new Alert(Alert.AlertType.INFORMATION, "Grape created successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "Grape already exists!");
	                }
	            alert.show();
	            resetGrapeData();
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
		
		public boolean checkAddGrape(Grape grape) {
            try {
            	clearInputData();
            	gs.create(grape);
                return true;
            }
            catch(Exception  e) {System.out.println(e); clearInputData();	return false;}
		}
		
		@FXML
		public void updateGrape(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityText = tfQuantity.getText();
			String ammountWineText = tfAmmountWine.getText();
			TableViewGrape sGrape = tvGrape.getSelectionModel().getSelectedItem();
				if (nameText.isEmpty() || nameText.length() < 2 || nameText.length() > 48) {
	            	tfName.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The name must be between 2 and 48 characters long!");
	                alert.show();
	            } else if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (ammountWineText.isEmpty() || isParsable(ammountWineText) == false) {
	            	tfAmmountWine.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The ammount wine record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbVariety.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a grape variety!");
	                alert.show();
	            } else if (tvGrape.getSelectionModel().getSelectedItem() == null){
	            	clearInputData();
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose an existing record from table!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to update Grape "+nameText);
	            	
	            	Grape newGrape = new Grape();
	            	newGrape.setName(nameText);
	            	newGrape.setQuantity(Double.parseDouble(quantityText));
	            	newGrape.setWinePerKG(Double.parseDouble(ammountWineText));
	            	newGrape.setVariety(gvs.getByName(cmbVariety.getValue()));
	            	newGrape.setID(gs.getGrapeByNm(sGrape.getName()).getId());
	            	
		            boolean isUpdated = checkUpdateGrape(newGrape);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Grape "+nameText+" updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetGrapeData();
	            }
        }		
			
		public boolean checkUpdateGrape(Grape grape) {
            try {
            	clearInputData();
            	gs.update(grape);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeGrape(MouseEvent event) {
			Alert alert;
			String nameText = tfName.getText();
			String quantityText = tfQuantity.getText();
			String ammountWineText = tfAmmountWine.getText();
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete "+nameText+"?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (nameText.isEmpty() || nameText.length() < 2 || nameText.length() > 48) {
	            	tfName.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The name must be between 2 and 48 characters long!");
	                alert.show();
	            } else if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (ammountWineText.isEmpty() || isParsable(ammountWineText) == false) {
	            	tfAmmountWine.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The ammount wine record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbVariety.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a grape variety!");
	                alert.show();
	            } else if (gs.getGrapeByNm(nameText) == null) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Type or select an existing record!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to delete User "+usernameText);
	            	Grape newGrape = new Grape();
	            	newGrape.setName(nameText);
	            	newGrape.setQuantity(Double.parseDouble(quantityText));
	            	newGrape.setWinePerKG(Double.parseDouble(ammountWineText));
	            	newGrape.setVariety(gvs.getByName(cmbVariety.getValue()));
	            	newGrape.setID(gs.getGrapeByNm(nameText).getId());
	            boolean isDeleted = checkRemoveGrape(newGrape);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "Grape deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetGrapeData();
	            }
			}
		}
		
		public boolean checkRemoveGrape(Grape grape) {
            try {
            	clearInputData();
            	gs.delete(grape.getId());
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
				filteredData.setPredicate( (TableViewGrape tvg) -> {
					String newVal = nv.toString();
					return tvg.getName().contains(newVal);
				});
			});
			ObservableList<TableViewGrape> osData = FXCollections.observableArrayList(filteredData);
			tvGrape.setItems(osData);

	    }	
}
