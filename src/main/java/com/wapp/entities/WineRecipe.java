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
@Table(name = "WINE_RECIPE")
public class WineRecipe {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id")
	@SequenceGenerator(name="recipe_id", sequenceName = "recipe_idSEQ", allocationSize=1)
	@Column(name = "recipe_id", updatable = true, nullable = false)
	public Long recipeID;
	
	@Column(name = "recipe_name", length = 48, unique=true)
	public String recipeName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_grape_id", referencedColumnName = "grape_id", foreignKey = @ForeignKey(name = "FK_GRAPE_BL_ID"), unique=true)
	private Grape grapeFirst;
	
	@Column(name = "recipe_quantity_bl")
	public Double quantityFirst;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "s_grape_id", referencedColumnName = "grape_id", foreignKey = @ForeignKey(name = "FK_GRAPE_WH_ID"), unique=true)
	private Grape grapeSecond;
	
	@Column(name = "recipe_quantity_wh")
	public Double quantitySecond;
	
	public WineRecipe() {}
	
	public WineRecipe(String rn, Grape gb, Double qb, Grape gw, Double qw) {
		this.recipeName = rn;
		this.grapeFirst = gb;
		this.quantityFirst = qb;
		this.grapeSecond = gw;
		this.quantitySecond = qw;
	}
	
	public Long getId() {
	      return recipeID;
	}
	
	public void setID(Long gid) {
		this.recipeID = gid;
	}
	
	public String getName() {
		return recipeName;
	}
	
	public void setName(String gn) {
		this.recipeName = gn;
	}
	
	public Grape getGrapeF() {
		return grapeFirst;
	}
	
	public void setGrapeF(Grape gb) {
		this.grapeFirst = gb;
	}
	
	public Double getQuantityF() {
		return quantityFirst;
	}
	
	public void setQuantityF(Double qntb) {
		this.quantityFirst = qntb;
	}
	
	public Grape getGrapeS() {
		return grapeSecond;
	}
	
	public void setGrapeS(Grape gw) {
		this.grapeSecond = gw;
	}
	
	public Double getQuantityS() {
		return quantitySecond;
	}
	
	public void setQuantityS(Double qntw) {
		this.quantitySecond = qntw;
	}
	
	@Override
    public String toString() {
        return "recipe: " + this.recipeID + ", " + this.recipeName + ", " + this.grapeFirst + ", " + this.quantityFirst + ", " + this.grapeSecond + ", " + this.quantitySecond;
    }	
}
