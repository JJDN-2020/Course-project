package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
@Table(name = "BOTTLE_SIZE")
public class BottleSize {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bottle_size_id")
	@SequenceGenerator(name="bottle_size_id", sequenceName = "bottle_size_idSEQ", allocationSize=1)
	@Column(name = "bottle_size_id", updatable = true, nullable = false)
	public Long bottleSizeID;
	
	@Column(name = "bottle_size", length = 10)
	public Integer bottleSize;
	
	public BottleSize() {}
	
	public BottleSize(Integer s) {
		this.bottleSize = s;
	}
	
	public BottleSize(Long sid, Integer s) {
		this.bottleSizeID = sid;
		this.bottleSize = s;
	}
	
	public Long getId() {
	      return bottleSizeID;
	}
	
	public void setID(Long sid) {
		this.bottleSizeID = sid;
	}
	
	public Integer getSize() {
		return bottleSize;
	}
	
	public void setSize(Integer s) {
		this.bottleSize = s;
	}	
	
	@Override
    public String toString() {
        return bottleSize.toString();
    }
}
