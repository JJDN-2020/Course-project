package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "BOTTLING")
public class Bottling {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bottling_id")
	@SequenceGenerator(name="bottling_id", sequenceName = "bottling_idSEQ", allocationSize=1)
	@Column(name = "bottling_id", updatable = true, nullable = false)
	public Long bottlingID;
	
	@Column(name = "bottle_quantity")
	public Integer bottleQuantity;
	
	@Column(name = "wine_quantity")
	public Double wineQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bottleSizeID", foreignKey = @ForeignKey(name = "FK_BOTTLE_SIZE_ID"))
    private BottleSize bottleSize;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipeID", foreignKey = @ForeignKey(name = "FK_WINE_RECIPE_ID"))
    private WineRecipe wineRecipe;
	
	public Bottling() {}
	
	public Bottling(Long id, Integer bq, Double wq, BottleSize b, WineRecipe w) {
		this.bottlingID = id;
		this.bottleQuantity = bq;
		this.wineQuantity = wq;
		this.bottleSize = b;
		this.wineRecipe = w;
	}
	
	public Bottling(Integer bq, Double wq, BottleSize b, WineRecipe w) {
		this.bottleQuantity = bq;
		this.wineQuantity = wq;
		this.bottleSize = b;
		this.wineRecipe = w;
	}
	
	public Long getId() {
	      return bottlingID;
	}
	
	public void setId(Long bid) {
		this.bottlingID = bid;
	}
	
	public Integer getBQuantity() {
		return bottleQuantity;
	}
	
	public void setBQuantity(Integer qnt) {
		this.bottleQuantity = qnt;
	}
	
	public Double getWQuantity() {
		return wineQuantity;
	}
	
	public void setWQuantity(Double qnt) {
		this.wineQuantity = qnt;
	}
	
	public BottleSize getSize() {
		return bottleSize;
	}
	
	public void setSize(BottleSize b) {
		this.bottleSize = b;
	}
	
	public WineRecipe getRecipe() {
		return wineRecipe;
	}
	
	public void setRecipe(WineRecipe w) {
		this.wineRecipe = w;
	}

	@Override
    public String toString() {
        return "Bottle: " + this.bottlingID + ", " + this.bottleQuantity + ", " + this.wineQuantity+ ", " + this.bottleSize.toString() + ", " + this.wineRecipe.toString();
    }	
}
