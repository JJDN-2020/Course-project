package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewWine {

	private SimpleLongProperty id;
    private SimpleDoubleProperty quantity;
    private SimpleStringProperty wineRecipe;

    public TableViewWine() {}
	
	public TableViewWine(SimpleLongProperty slp, SimpleDoubleProperty sdp, SimpleStringProperty ssp) {
			this.setId(slp);
			this.setQuantity(sdp);
			this.setWineRecipe(ssp);
	}
	
	public Long getId() {
		return id.get();
	}

	public void setId(SimpleLongProperty slp) {
		this.id = slp;
	}

	public Double getQuantity() {
		return quantity.get();
	}

	public void setQuantity(SimpleDoubleProperty sdp) {
		this.quantity = sdp;
	}
	
	public String getWineRecipe() {
		return wineRecipe.get();
	}

	public void setWineRecipe(SimpleStringProperty ssp) {
		this.wineRecipe = ssp;
	}
}
