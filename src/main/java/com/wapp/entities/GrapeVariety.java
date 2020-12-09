package com.wapp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
@Table(name = "GRAPE_VARIETY")
public class GrapeVariety {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variety_id")
	@SequenceGenerator(name="variety_id", sequenceName = "variety_idSEQ", allocationSize=1)
	@Column(name = "variety_id", updatable = true, nullable = false)
	public Long varietyID;
	
	@Column(name = "variety_name", length = 10)
	public String varietyName;
	
	public GrapeVariety() {}
	
	public GrapeVariety(String vn) {
		this.varietyName = vn;
	}
	
	public GrapeVariety(Long vid, String vn) {
		this.varietyID = vid;
		this.varietyName = vn;
	}
	
	public Long getId() {
	      return varietyID;
	}
	
	public void setID(Long vid) {
		this.varietyID = vid;
	}
	
	public String getVarietyName() {
		return varietyName;
	}
	
	public void setVarietyName(String vn) {
		this.varietyName = vn;
	}	
	
	@Override
    public String toString() {
        return varietyName;
    }
}
