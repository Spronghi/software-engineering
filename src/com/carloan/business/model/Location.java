package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location implements Model{
	private static final long serialVersionUID = -7316426235866446341L;
	private IntegerProperty id;
	private StringProperty city;
	private StringProperty postalNo;
	private StringProperty road;
	public Location() {
		super();
		this.id = new SimpleIntegerProperty(0);
		this.city = new SimpleStringProperty();
		this.postalNo = new SimpleStringProperty();
		this.road = new SimpleStringProperty();
	}
	public Location(String city, String postalNo, String road) {
		super();
		this.id = new SimpleIntegerProperty();
		this.city = new SimpleStringProperty(city);
		this.postalNo = new SimpleStringProperty(postalNo);
		this.road = new SimpleStringProperty(road);
	}
	public Integer getId(){
		return id.get();
	}
	public String getCity() {
		return city.get();
	}
	public String getPostalNo() {
		return postalNo.get();
	}
	public String getRoad() {
		return road.get();
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public void setCity(String city) {
		this.city.set(city);
	}
	public void setPostalNo(String postalNo) {
		this.postalNo.set(postalNo);
	}
	public void setRoad(String road) {
		this.road.set(road);
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public StringProperty cityProperty() {
		return city;
	}
	public StringProperty postalNoProperty() {
		return postalNo;
	}
	public StringProperty roadProperty() {
		return road;
	}
	@Override
	public String toString() {
		return "Location [city=" + city.get() + ", postalNo=" + postalNo.get() + ", road="
				+ road.get() + "]";
	}
	public StringProperty endUserStringProperty() {
		return new SimpleStringProperty(getCity()+" "+getPostalNo()+" - "+getRoad());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Location))
			return false;
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
