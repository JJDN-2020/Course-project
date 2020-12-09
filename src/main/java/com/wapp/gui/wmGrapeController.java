package com.wapp.gui;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wapp.entities.Grape;
import com.wapp.entities.GrapeVariety;
import com.wapp.entities.nonpersistent.TableViewGrape;
import com.wapp.services.GrapeService;

import javafx.beans.property.SimpleDoubleProperty;
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

public class wmGrapeController implements Initializable{
		
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
	    
	    private GrapeService gs = new GrapeService();
		private FilteredList<TableViewGrape> filteredData;
		
		@Override
	    public void initialize(URL url, ResourceBundle rb) {
			resetGrapeData();
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
