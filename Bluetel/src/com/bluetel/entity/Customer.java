package com.bluetel.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Customer implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String serialNumber;
	private String mpxn;
	private String readDate;
	
	@OneToMany(mappedBy="customer", orphanRemoval=true, fetch = FetchType.EAGER)
	@Cascade(value=CascadeType.ALL)
	@JsonManagedReference
	private List<Register> register;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMpxn() {
		return mpxn;
	}

	public void setMpxn(String mpxn) {
		this.mpxn = mpxn;
	}

	public String getReadDate() {
		return readDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

	public List<Register> getRegister() {
		return register;
	}

	public void setRegister(List<Register> register) {
		this.register = register;
	}
}
