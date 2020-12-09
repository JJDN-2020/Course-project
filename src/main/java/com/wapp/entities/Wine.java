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
@Table(name = "WINE")
public class Wine {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_id")
	@SequenceGenerator(name="wine_id", sequenceName = "wine_idSEQ", allocationSize=1)
	@Column(name = "wine_id", updatable = true, nullable = false)
	public Long wineID;
	
	@Column(name = "wine_quantity")
	public Double wineQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "FK_RECIPE_ID"))	
    private WineRecipe wineRecipe;
	
	public Wine() {}
	
	public Wine(Double wq, WineRecipe wr) {
		this.wineQuantity = wq;
		this.wineRecipe = wr;
	}
	
	public Long getId() {
	      return wineID;
	}
	
	public void setId(Long gid) {
		this.wineID = gid;
	}
	
	public Double getQuantity() {
		return wineQuantity;
	}
	
	public void setQuantity(Double qnt) {
		this.wineQuantity = qnt;
	}
	
	public WineRecipe getRecipe() {
		return wineRecipe;
	}
	
	public void setRecipe(WineRecipe wr) {
		this.wineRecipe = wr;
	}

	@Override
    public String toString() {
        return "Wine: " + this.wineID + ", " + this.wineQuantity + ", " + this.wineRecipe.toString();
    }	
}
