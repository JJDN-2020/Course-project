package com.wapp.gui;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.hibernate.HibernateException;

import com.wapp.entities.Bottle;
import com.wapp.entities.BottleSize;
import com.wapp.entities.nonpersistent.TableViewBottle;
import com.wapp.services.BottleService;
import com.wapp.services.BottleSizeService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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

public class BottleController implements Initializable{
		
	    @FXML
	    private AnchorPane apSubScene;
	
	    @FXML
	    private TableView<TableViewBottle> tvBottle;
	
	    @FXML
	    private TableColumn<TableViewBottle, Long> colID;
	
	    @FXML
	    private TableColumn<TableViewBottle, Integer> colQuantity;
	
	    @FXML
	    private TableColumn<TableViewBottle, BottleSize> colSize;
	    
	    @FXML
	    private TextField tfSearch;
	    
	    @FXML
	    private TextField tfID;
	    
	    @FXML
	    private TextField tfQuantity;
	
	    @FXML
	    private ComboBox<Integer> cmbSize;
	    
	    @FXML
	    private Button btnCreate;
	    
	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Button btnRemove;
	    
	    @FXML
	    private Button btnClear;
	    
	    final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	    private BottleService bs = new BottleService();
		private BottleSizeService bss = new BottleSizeService();
		private Map<Long, Integer> bsMap;
		private FilteredList<TableViewBottle> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetBottleData();
			resetSizeData();
	    }
		
		public void resetBottleData() {
			tvBottle.getItems().clear();
			ObservableList<TableViewBottle> observableList = FXCollections.observableArrayList();
			List<Bottle> bottles = bs.getAll();
			for(int i = 0; i < bottles.size(); i ++){
				observableList.add(new TableViewBottle(
				               new SimpleLongProperty(bottles.get(i).getId()),
							   new SimpleIntegerProperty(bottles.get(i).getQuantity()),
							   new SimpleIntegerProperty(bottles.get(i).getSize().getSize())));
				}
			filteredData = new FilteredList<TableViewBottle>(observableList, e -> true);
			
			colID.setCellValueFactory(new PropertyValueFactory<>("id"));
			colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));	
			colSize.setCellValueFactory(new PropertyValueFactory<>("bottleSize"));
			
			tvBottle.setItems(observableList);
		}
		
		public void resetSizeData() {
			bsMap = new HashMap<>();
			bss.OLgetAll().forEach(bs ->{
				cmbSize.getItems().add(bs.getSize());
				try {bsMap.put(bs.bottleSizeID, bs.bottleSize);}
				catch (Exception e) {System.out.println(e);}
			});
		}
		
		@FXML
		public void tableViewItemSelect(MouseEvent event) {
			TableViewBottle sBottle = tvBottle.getSelectionModel().getSelectedItem();
			if(sBottle != null) {
				tfID.setText(sBottle.getId().toString());
				tfQuantity.setText(sBottle.getQuantity().toString());
				cmbSize.setValue(sBottle.getBottleSize());
			}
			tfSearch.clear();
		}
		
		public void clearInputData() {
			tfID.clear();
			tfQuantity.clear();
			cmbSize.getSelectionModel().clearSelection();
		}
		
		@FXML
		public void createBottle(MouseEvent event) {
			Alert alert;
			String quantityText = tfQuantity.getText();
	            if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbSize.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a bottle size!");
	                alert.show();
	            } else {
	            //logger.info(lUsernameWelcome + " attempting to create a new Bottle "+BOTTLE?);
	            	
            	Bottle newBottle = new Bottle();
            	newBottle.setQuantity(Integer.parseInt(quantityText));
            	newBottle.setSize(bss.getBySize(cmbSize.getValue()));
	            boolean isCreated = checkAddBottle(newBottle);
	                if (isCreated) {
	                    alert = new Alert(Alert.AlertType.INFORMATION, "Bottle created successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "Bottle already exists!");
	                }
	            alert.show();
	            resetBottleData();
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
		
		public boolean checkAddBottle(Bottle bottle) {
            try {
            	clearInputData();
            	bs.create(bottle);
                return true;
            }
            catch(Exception  e) {System.out.println(e); clearInputData();	return false;}
		}
		
		@FXML
		public void updateBottle(MouseEvent event) {
			Alert alert;
			String quantityText = tfQuantity.getText();
			TableViewBottle sBottle = tvBottle.getSelectionModel().getSelectedItem();
				if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
	            } else if (cmbSize.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a bottle size!");
	                alert.show();
	            } else if (tvBottle.getSelectionModel().getSelectedItem() == null){
	            	clearInputData();
	            	alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose an existing record from table!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to update Bottle "+nameText);
	            	
	            	Bottle newBottle = new Bottle();
	            	newBottle.setQuantity(Integer.parseInt(quantityText));
	            	newBottle.setSize(bss.getBySize(cmbSize.getValue()));
	            	newBottle.setID(sBottle.getId());
	            	
		            boolean isUpdated = checkUpdateBottle(newBottle);
		                if (isUpdated) {
		                    alert = new Alert(Alert.AlertType.INFORMATION, "Bottle "+sBottle.getId()+" updated successfully!");
		                } else {
		                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
		                }
		            alert.show();
		            resetBottleData();
	            }
        }		
			
		public boolean checkUpdateBottle(Bottle bottle) {
            try {
            	clearInputData();
            	bs.update(bottle);
            	return true;
            }
            catch(Exception  e) {System.out.println(e);    clearInputData();	return false;}
		}
		
		@FXML
		public void removeBottle(MouseEvent event) {
			Alert alert;
			String idText = tfID.getText();
			String quantityText = tfQuantity.getText();
			alert = new Alert(Alert.AlertType.WARNING, "Are you sure want to delete Bottle?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> btnType = alert.showAndWait();
			
			if (btnType.get() == ButtonType.YES) {
				if (quantityText.isEmpty() || isParsable(quantityText) == false)  {
	            	tfQuantity.clear();
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("The quantity record must be a number and must not be empty!");
	                alert.show();
				} else if (cmbSize.getSelectionModel().isEmpty()) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Choose a bottle size!");
	                alert.show();
	            } else if (bs.getById(Long.parseLong(idText)) == null) {
	                alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Type or select an existing record!");
	                alert.show();
	            } else {
	            	//logger.info(lUsernameWelcome + " attempting to delete User "+usernameText);
	            	Bottle newBottle = new Bottle();
	            	newBottle.setQuantity(Integer.parseInt(quantityText));
	            	newBottle.setSize(bss.getBySize(cmbSize.getValue()));
	            	newBottle.setID(Long.parseLong(idText));
	            boolean isDeleted = checkRemoveGrape(newBottle);
	                if (isDeleted) {
	                	alert = new Alert(Alert.AlertType.INFORMATION, "Bottle deleted successfully!");
	                } else {
	                    alert = new Alert(Alert.AlertType.ERROR, "The data you are looking for doesn`t exists. Change your search query or create new record!");
	                }
	            alert.show();
	            resetBottleData();
	            }
			}
		}
		
		public boolean checkRemoveGrape(Bottle bottle) {
            try {
            	clearInputData();
            	bs.delete(bottle.getId());
                return true;
            }
            catch(HibernateException  e) {System.out.println(e);	return false;}
		}
		
		@FXML
		public void onClearLabels(MouseEvent event) {
			clearInputData();
		}
		
		@FXML
	    void searchBottleByID(KeyEvent event) {
			tfSearch.textProperty().addListener((o, ov, nv) -> {
				filteredData.setPredicate( (TableViewBottle tvb) -> {
					String newVal = nv.toString();
					return tvb.getId().toString().contains(newVal);
				});
			});
			ObservableList<TableViewBottle> osData = FXCollections.observableArrayList(filteredData);
			tvBottle.setItems(osData);

	    }
}
