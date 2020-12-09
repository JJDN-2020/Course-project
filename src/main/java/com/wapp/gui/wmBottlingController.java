package com.wapp.gui;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wapp.entities.Bottle;
import com.wapp.entities.Bottling;
import com.wapp.entities.nonpersistent.TableViewBottling;
import com.wapp.services.BottlingService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class wmBottlingController implements Initializable{
		
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
	
	    private BottlingService bs = new BottlingService();
		private FilteredList<TableViewBottling> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetBottlingData();
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
