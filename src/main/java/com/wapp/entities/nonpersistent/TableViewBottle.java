package com.wapp.entities.nonpersistent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

public class TableViewBottle {

	private SimpleLongProperty id;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty bottleSize;

    public TableViewBottle() {}
	
	public TableViewBottle(SimpleLongProperty slp, SimpleIntegerProperty sip, SimpleIntegerProperty sip2) {
			this.setId(slp);
			this.setQuantity(sip);
			this.SetBottleSize(sip2);
	}
	
	public Long getId() {
		return id.get();
	}

	public void setId(SimpleLongProperty slp) {
		this.id = slp;
	}

	public Integer getQuantity() {
		return quantity.get();
	}

	public void setQuantity(SimpleIntegerProperty sip) {
		this.quantity = sip;
	}
	
	public Integer getBottleSize() {
		return bottleSize.get();
	}

	public void SetBottleSize(SimpleIntegerProperty sip) {
		this.bottleSize = sip;
	}
}
