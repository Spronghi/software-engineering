package com.carloan.business.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Person{
	protected StringProperty firstName;
	protected StringProperty lastName;
	protected StringProperty email;
	public Person() {
		super();
		this.firstName = new SimpleStringProperty();
		this.lastName = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
	}
	public Person(String firstName, String lastName, String email) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
	}
	public String getFirstName() {
		return firstName.get();
	}
	public String getLastName() {
		return lastName.get();
	}
	public String getEmail() {
		return email.get();
	}
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty firstNameProperty() {
		return firstName;
	}
	public StringProperty lastNameProperty() {
		return lastName;
	}
	public StringProperty emailProperty() {
		return email;
	}
	@Override
	public String toString() {
		return firstName.get()+" "+lastName.get();
	}
}
