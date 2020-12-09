package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;

import com.wapp.entities.Bottle;
import com.wapp.entities.Bottling;
import com.wapp.entities.nonpersistent.TableViewBottling;
import com.wapp.services.BottleService;
import com.wapp.services.BottleSizeService;
import com.wapp.services.BottlingService;
import com.wapp.services.WineRecipeService;
import com.wapp.services.WineService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class BottlingController implements Initializable{
		
	    @FXML
	    private AnchorPane apSubScene;
	
	    @FXML
	    private TableView<TableViewBottling> tvBottling;
	
	    @FXML
	    private TableColumn<TableViewBottling, Long> colID;
	
	    @FXML
	    private TableColumn<TableViewBottling, Bottle> colBottleSize;
	
	    @FXML
	    private TableColumn<TableViewBottling, Integer> colBottleQuantity;
	
	    @FXML
	    private TableColumn<TableViewBottling, String> colWineName;
	
	    @FXML
	    private TableColumn<TableViewBottling, Double> colWineQuantity;
	    
	    @FXML
	    private TextField tfSearch;
	
	    @FXML
	    private TextField tfID;
	    
	    @FXML
	    private ComboBox<String> cmbBottle;
	    
	    @FXML
	    private TextField tfQuantityBottles;
	    
	    @FXML
	    private ComboBox<String> cmbWine;
	    
	    @FXML
	    private TextField tfQuantityWine;
	    
	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    private BottlingService bs = new BottlingService();
	    private BottleService bs2 = new BottleService();
	    private BottleSizeService bss = new BottleSizeService();
		private WineService ws = new WineService();
		private WineRecipeService wrs = new WineRecipeService();
		private Map<Long, String> bMap;
		private Map<Long, String> wMap;
		private FilteredList<TableViewBottling> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetBottlingData();
			resetOtherData();
	    }
		
		public void resetBottlingData() {
			tvBottling.getItems().clear();
			ObservableList<TableViewBottling> observableList = FXCollections.observableArrayList();
			List<Bottling> bottling = bs.getAll();
			for(int i = 0; i < bottling.size(); i ++){
				observableList.add(new TableViewBottling(
				               new SimpleLongProperty(bottling.get(i).getId()),
				               new SimpleIntegerProperty(bottling.get(i).getSize().getSize()),
				               new SimpleIntegerProperty(bottling.get(i).getBQuantity()),
				               new SimpleStringProperty(bottling.get(i).getRecipe().getName()),
				               new SimpleDoubleProperty(bottling.get(i).getWQuantity())));
				}		    
			filteredData = new FilteredList<TableViewBottling>(observableList, e -> true);
			
			colID.setCellValueFactory(new PropertyValueFactory<>("id"));
			colBottleSize.setCellValueFactory(new PropertyValueFactory<>("bottleSize"));
			colBottleQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityBottles"));	
			colWineName.setCellValueFactory(new PropertyValueFactory<>("wineName"));
			colWineQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityWine"));
			
			tvBottling.setItems(observableList);
		}
		
		public void resetOtherData() {
			bMap = new HashMap<>();
			wMap = new HashMap<>();
			bs2.OLgetAll().forEach(b ->{
				cmbBottle.getItems().add(b.getSize().getSize().toString());
				try {bMap.put(b.getId(), b.getSize().getSize().toString());}
				catch (Exception e) {System.out.println(e);}
				
			});
			ws.OLgetAll().forEach(w ->{
			cmbWine.getItems().add(w.getRecipe().getName());
			try {wMap.put(w.getId(), w.getRecipe().getName());}
			catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewBottling sBottling = tvBottling.getSelectionModel().getSelectedItem();
			if(sBottling != null) {
				tfID.setText(sBottling.getId().toString());
				cmbBottle.setValue(sBottling.getBottleSize().toString());
				tfQuantityBottles.setText(sBottling.getQuantityBottles().toString());
				cmbWine.setValue(sBottling.getWineName());
				tfQuantityWine.setText(sBottling.getQuantityWine().toString());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfID.clear();
			tfQuantityBottles.clear();
			tfQuantityWine.clear();
			cmbBottle.getSelectionModel().clearSelection();
			cmbWine.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createBottling(MouseEvent event) {
			Alert alert;
			String quantityBottles = tfQuantityBottles.getText();
			String quantityWine = tfQuantityWine.getText();
	            if (quantityBottles.isEmpty() || isParsableInt(quantityBottles) == false)  {
	            	tfQuantityBottles.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity of bottles record must be a number and must not be empty!");
	                alert.show();
	            } else if (quantityWine.isEmpty() || isParsableDouble(quantityWine) == false) {
	            	tfQuantityWine.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity of wine record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbBottle.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a bottle size!");
	                alert.show();
	            } else if (cmbWine.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a wine!");
	                alert.show();
	            } else {
		            //logger.info(lUsernameWelcome + " attempting to create a new Grape "+nameText);
	            	Bottling newBottling = new Bottling();
	            	newBottling.setBQuantity(Integer.parseInt(quantityBottles));
	            	newBottling.setWQuantity(Double.parseDouble(quantityWine));
	            	newBottling.setSize(bss.getBySize(Integer.parseInt(cmbBottle.getValue())));
	            	newBottling.setRecipe(wrs.getWineRecipeByNm(cmbWine.getValue()));
		            boolean isCreated = checkAddBottling(newBottling);
		                if (isCreated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Bottling created successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "Bottling already exists!");
		                }
		            alert.show();
		            resetBottlingData();
	            }
        }
		
		public static boolean isParsableInt(String input) {
		    try {
		        Integer.parseInt(input);
		        return true;
		    } catch (final NumberFormatException e) {
		        return false;
		    }
		}
		
		public static boolean isParsableDouble(String input) {
		    try {
		        Double.parseDouble(input);
		        return true;
		    } catch (final NumberFormatException e) {
		        return false;
		    }
		}
		
		public boolean checkAddBottling(Bottling bottling) {
            try {
            	clearInputData();
            	bs.create(bottling);
                return true;
            }
            catch(Exception  e) {System.out.println(e); clearInputData();	return false;}
		}
		
		@FXML
		public void updateBottling(MouseEvent event) {
			Alert alert;
			String idText = tfID.getText();
			String quantityBottles = tfQuantityBottles.getText();
			String quantityWine = tfQuantityWine.getText();
			if (quantityBottles.isEmpty() || isParsableInt(quantityBottles) == false)  {
            	tfQuantityBottles.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The quantity of bottles record must be a number and must not be empty!");
                alert.show();
            } else if (quantityWine.isEmpty() || isParsableDouble(quantityWine) == false) {
            	tfQuantityWine.clear();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The quantity of wine record must be a number and must not be empty!");
                alert.show();
            } else if (cmbBottle.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a bottle size!");
                alert.show();
            } else if (cmbWine.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a wine!");
                alert.show();
            } else if (tvBottling.getSelectionModel().getSelectedItem() == null){
		            	clearInputData();
		            	alert = new Alert(Alert.AlertType.ERROR);
		                alert.setContentText("Choose an existing record from table!");
		                alert.show();
		    } else {
	            	//logger.info(lUsernameWelcome + " attempting to update Grape "+nameText);
			    	Bottling newBottling = new Bottling();
	            	newBottling.setBQuantity(Integer.parseInt(quantityBottles));
	            	newBottling.setWQuantity(Double.parseDouble(quantityWine));
	            	//newBottling.setBottle(bs2.getByBottleSizeId(bss.getBySize(Integer.parseInt(cmbBottle.getValue())).getId()));
	            	//newBottling.setWine(ws.getByWineRecipeId(wrs.getWineRecipeByNm(cmbWine.getValue()).getId()));
	            	newBottling.setSize(bss.getBySize(Integer.parseInt(cmbBottle.getValue())));
	            	newBottling.setRecipe(wrs.getWineRecipeByNm(cmbWine.getValue()));
	            	newBottling.setId(Long.parseLong(idText));
	            	
		            boolean isUpdated = checkUpdateBottling(newBottling);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Bottling "+idText+" updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetBottlingData();
	            }
        }		
			
		public boolean checkUpdateBottling(Bottling bottling) {
            try {
            	clearInputData();
            	bs.update(bottling);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeBottling(MouseEvent event) {
			Alert alert;
			String idText = tfID.getText();
			String quantityBottles = tfQuantityBottles.getText();
			String quantityWine = tfQuantityWine.getText();
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete Bottling "+idText+"?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (quantityBottles.isEmpty() || isParsableInt(quantityBottles) == false)  {
	            	tfQuantityBottles.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity of bottles record must be a number and must not be empty!");
	                alert.show();
	            } else if (quantityWine.isEmpty() || isParsableDouble(quantityWine) == false) {
	            	tfQuantityWine.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity of wine record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbBottle.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a bottle size!");
	                alert.show();
	            } else if (cmbWine.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a wine!");
	                alert.show();
	            } else if (bs.getById(Long.parseLong(idText)) == null){
			            	clearInputData();
			            	alert = new Alert(Alert.AlertType.ERROR);
			                alert.setContentText("Choose an existing record from table!");
			                alert.show();
			    } else {
	            	//logger.info(lUsernameWelcome + " attempting to delete User "+usernameText);	            	
	            	Bottling newBottling = new Bottling();
	            	newBottling.setBQuantity(Integer.parseInt(quantityBottles));
	            	newBottling.setWQuantity(Double.parseDouble(quantityWine));
	            	newBottling.setSize(bss.getBySize(Integer.parseInt(cmbBottle.getValue())));
	            	newBottling.setRecipe(wrs.getWineRecipeByNm(cmbWine.getValue()));
	            	//newBottling.setBottle(bs2.getByBottleSizeId(bss.getBySize(Integer.parseInt(cmbBottle.getValue())).getId()));
	            	//newBottling.setWine(ws.getByWineRecipeId(wrs.getWineRecipeByNm(cmbWine.getValue()).getId()));
	            	newBottling.setId(Long.parseLong(idText));
	            boolean isDeleted = checkRemoveBottling(newBottling);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "Bottling deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetBottlingData();
	            }
			}
		}
		
		public boolean checkRemoveBottling(Bottling bottling) {
            try {
            	clearInputData();
            	bs.delete(bottling.getId());
                return true;
            }
            catch(HibernateException  e) {System.out.println(e);	return false;}
		}
		
		@FXML
		public void onClearLabels(MouseEvent event) {
			clearInputData();
		}
		
		@FXML
	    void searchBottlingByID(KeyEvent event) {
			tfSearch.textProperty().addListener((o, ov, nv) -> {
				filteredData.setPredicate( (TableViewBottling tvb) -> {
					String newVal = nv.toString();
					return tvb.getId().toString().contains(newVal);
				});
			});
			ObservableList<TableViewBottling> osData = FXCollections.observableArrayList(filteredData);
			tvBottling.setItems(osData);

	    }
		
}
