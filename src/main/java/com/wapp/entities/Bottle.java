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
@Table(name = "BOTTLE")
public class Bottle {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bottle_id")
	@SequenceGenerator(name="bottle_id", sequenceName = "bottle_idSEQ", allocationSize=1)
	@Column(name = "bottle_id", updatable = true, nullable = false)
	public Long bottleID;
	
	@Column(name = "bottle_quantity")
	public Integer bottleQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bottleSizeID", foreignKey = @ForeignKey(name = "FK_SIZE_ID"))
    public BottleSize bottleSize;
	
	public Bottle() {}
	
	public Bottle(Long id, Integer bq, BottleSize bs) {
		this.bottleID = id;
		this.bottleQuantity = bq;
		this.bottleSize = bs;
	}
	
	public Bottle(Integer bq, BottleSize bs) {
		this.bottleQuantity = bq;
		this.bottleSize = bs;
	}
	
	public Long getId() {
	      return bottleID;
	}
	
	public void setID(Long gid) {
		this.bottleID = gid;
	}
	
	public Integer getQuantity() {
		return bottleQuantity;
	}
	
	public void setQuantity(Integer qnt) {
		this.bottleQuantity = qnt;
	}
	
	public BottleSize getSize() {
		return bottleSize;
	}
	
	public void setSize(BottleSize bs) {
		this.bottleSize = bs;
	}

	@Override
    public String toString() {
        return "Bottle: " + this.bottleID + ", " + this.bottleQuantity + ", " + this.bottleSize.toString();
    }	
}
