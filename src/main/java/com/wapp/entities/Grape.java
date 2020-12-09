package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "GRAPE")
public class Grape {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grape_id")
	@SequenceGenerator(name="grape_id", sequenceName = "grape_idSEQ", allocationSize=1)
	@Column(name = "grape_id", updatable = true, nullable = false)
	public Long grapeID;
	
	@Column(name = "grape_name", length = 48, unique=true)
	public String grapeName;
	
	@Column(name = "grape_quantity")
	public Double grapeQuantity;
	
	@Column(name = "wine_per_kg")
	public Double winePerKG;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variety_id", foreignKey = @ForeignKey(name = "FK_VARIETY_ID"))
    private GrapeVariety grapeVariety;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grapeFirst")
    private List<WineRecipe> wr;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grapeSecond")
    private List<WineRecipe> wr2;
	
	public Grape() {}
	
	public Grape(String gn, Double gq, Double wpk, GrapeVariety gv) {
		this.grapeName = gn;
		this.grapeQuantity = gq;
		this.winePerKG = wpk;
		this.grapeVariety = gv;
	}
	
	public Long getId() {
	      return grapeID;
	}
	
	public void setID(Long gid) {
		this.grapeID = gid;
	}
	
	public String getName() {
		return grapeName;
	}
	
	public void setName(String gn) {
		this.grapeName = gn;
	}
	
	public Double getQuantity() {
		return grapeQuantity;
	}
	
	public void setQuantity(Double qnt) {
		this.grapeQuantity = qnt;
	}
	
	public Double getWinePerKG() {
		return winePerKG;
	}
	
	public void setWinePerKG(Double wpk) {
		this.winePerKG = wpk;
	}
	
	public GrapeVariety getVariety() {
		return grapeVariety;
	}
	
	public void setVariety(GrapeVariety gv) {
		this.grapeVariety = gv;
	}
	
	/*
	public Long getIdBlack() {
	      return grapeID;
	}
	
	public Long getIdWhite() {
	      return grapeID;
	}
	
	public String getNameBlack() {
		return grapeName;
	}
	
	public String getNameWhite() {
		return grapeName;
	}
	*/
	
	@Override
    public String toString() {
        return "Grape: " + this.grapeID + ", " + this.grapeName + ", " + this.grapeQuantity + ", " + this.grapeVariety.toString();
    }	
}
