package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Agency implements Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4432262113650840282L;
	private IntegerProperty number;
	private StringProperty name;
	private ObjectProperty<Location> location;
	public Agency() {
		super();
		this.number = new SimpleIntegerProperty(0);
		this.name = new SimpleStringProperty();
		this.location = new SimpleObjectProperty<>();
	}
	public Agency(String name, Location location) {
		super();
		this.number = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleObjectProperty<>(location);
	}
	public Integer getId(){
		return number.get();
	}
	public int getNumber() {
		return number.get();
	}
	public String getName() {
		return name.get();
	}
	public Location getLocation() {
		return location.get();
	}
	public void setNumber(int number) {
		this.number.set(Integer.valueOf(number));
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public void setLocation(Location location) {
		this.location.set(location);
	}
	public IntegerProperty idProperty(){
		return number;
	}
	public StringProperty nameProperty() {
		return name;
	}
	public ObjectProperty<Location> locationProperty() {
		return location;
	}
	@Override
	public String toString() {
		return "Agency [number=" + number.get() + ", name=" + name.get() + ", location="
				+ location.get() + "]";
	}
	public StringProperty endUserStringProperty() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Agency))
			return false;
		Agency other = (Agency) obj;
		if (number != other.number)
			return false;
		return true;
	}
	
}
