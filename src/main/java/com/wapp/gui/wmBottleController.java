package com.wapp.gui;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wapp.entities.Bottle;
import com.wapp.entities.BottleSize;
import com.wapp.entities.nonpersistent.TableViewBottle;
import com.wapp.services.BottleService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class wmBottleController implements Initializable{
		
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
	    
	    private BottleService bs = new BottleService();
		private FilteredList<TableViewBottle> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetBottleData();
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
