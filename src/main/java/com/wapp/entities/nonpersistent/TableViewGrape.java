package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewGrape {

	private SimpleStringProperty name;
	private SimpleStringProperty variety;
    private SimpleDoubleProperty quantity;
    private SimpleDoubleProperty ammountWine;

    public TableViewGrape() {}
	
	public TableViewGrape(SimpleStringProperty ssp, SimpleStringProperty ssp2, SimpleDoubleProperty sdp, SimpleDoubleProperty sdp2) {
			this.setName(ssp);
			this.setVariety(ssp2);
			this.setQuantity(sdp);
			this.setAmmountWine(sdp2);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(SimpleStringProperty ssp) {
		this.name = ssp;
	}

	public String getVariety() {
		return variety.get();
	}

	public void setVariety(SimpleStringProperty ssp) {
		this.variety = ssp;
	}

	public Double getQuantity() {
		return quantity.get();
	}

	public void setQuantity(SimpleDoubleProperty sdp) {
		this.quantity = sdp;
	}
	
	public Double getAmmountWine() {
		return ammountWine.get();
	}

	public void setAmmountWine(SimpleDoubleProperty sdp) {
		this.ammountWine = sdp;
	}
}
