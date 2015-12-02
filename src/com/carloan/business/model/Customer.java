package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer extends Person implements Model{

	private static final long serialVersionUID = 7170316622063984747L;
	private StringProperty telephone;
	private IntegerProperty id;
	public Customer() {
		super();
		this.id = new SimpleIntegerProperty(0);
		this.telephone = new SimpleStringProperty();
	}
	public Customer(String firstName,String lastName,String telephone,String email) {
		super(firstName, lastName, email);
		this.id = new SimpleIntegerProperty();
		this.telephone = new SimpleStringProperty(telephone);
	}
	public Integer getId(){
		return id.get();
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public String getTelephone() {
		return telephone.get();
	}
	public void setTelephone(String telephone) {
		this.telephone.set(telephone);
	}
	public StringProperty telephoneProperty() {
		return telephone;
	}
	public StringProperty endUserStringProperty() {
		StringProperty endUser = new SimpleStringProperty(getFirstName()+" "+getLastName());
		return endUser;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
