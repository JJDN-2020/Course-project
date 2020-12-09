package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewBottling {

	private SimpleLongProperty id;
    private SimpleIntegerProperty bottleSize;
    private SimpleIntegerProperty quantityBottles;
    private SimpleStringProperty wineName;
    private SimpleDoubleProperty quantityWine;

    public TableViewBottling() {}
	
	public TableViewBottling(SimpleLongProperty slp, SimpleIntegerProperty sip, SimpleIntegerProperty sip2, SimpleStringProperty ssp, SimpleDoubleProperty sdp) {
			this.setId(slp);
			this.SetBottleSize(sip);
			this.setQuantityBottles(sip2);
			this.setWineName(ssp);
			this.setQuantityWine(sdp);
	}
	
	public Long getId() {
		return id.get();
	}

	public void setId(SimpleLongProperty slp) {
		this.id = slp;
	}
	
	public Integer getBottleSize() {
		return bottleSize.get();
	}

	public void SetBottleSize(SimpleIntegerProperty sip) {
		this.bottleSize = sip;
	}
	
	public Integer getQuantityBottles() {
		return quantityBottles.get();
	}

	public void setQuantityBottles(SimpleIntegerProperty sip) {
		this.quantityBottles = sip;
	}
	
	public String getWineName() {
		return wineName.get();
	}

	public void setWineName(SimpleStringProperty ssp) {
		this.wineName = ssp;
	}
	
	public Double getQuantityWine() {
		return quantityWine.get();
	}

	public void setQuantityWine(SimpleDoubleProperty sdp) {
		this.quantityWine = sdp;
	}
}
