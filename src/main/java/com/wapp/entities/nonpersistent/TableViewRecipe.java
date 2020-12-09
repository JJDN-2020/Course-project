package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewRecipe {

	private SimpleStringProperty name;
    private SimpleStringProperty grapeNameFirst;
    private SimpleDoubleProperty quantityFirst;
    private SimpleStringProperty grapeNameSecond;
    private SimpleDoubleProperty quantitySecond;

    public TableViewRecipe() {}
	
	public TableViewRecipe(SimpleStringProperty ssp, SimpleStringProperty ssp2, SimpleDoubleProperty sdp, SimpleStringProperty ssp3, SimpleDoubleProperty sdp2) {
			this.setName(ssp);
			this.SetGrapeNameFirst(ssp2);
			this.setQuantityFirst(sdp);
			this.SetGrapeNameSecond(ssp3);
			this.setQuantitySecond(sdp2);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(SimpleStringProperty ssp) {
		this.name = ssp;
	}

	public Double getQuantityFirst() {
		return quantityFirst.get();
	}

	public void setQuantityFirst(SimpleDoubleProperty sdp) {
		this.quantityFirst = sdp;
	}
	
	public String getGrapeNameFirst() {
		return grapeNameFirst.get();
	}

	public void SetGrapeNameFirst(SimpleStringProperty ssp) {
		this.grapeNameFirst = ssp;
	}
	
	public Double getQuantitySecond() {
		return quantitySecond.get();
	}

	public void setQuantitySecond(SimpleDoubleProperty sdp) {
		this.quantitySecond = sdp;
	}
	
	public String getGrapeNameSecond() {
		return grapeNameSecond.get();
	}

	public void SetGrapeNameSecond(SimpleStringProperty ssp) {
		this.grapeNameSecond = ssp;
	}	
}
