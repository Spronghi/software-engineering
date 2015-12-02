package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car implements Model{
	private static final long serialVersionUID = -5954620243816661437L;
	private IntegerProperty id;
	private StringProperty model;
	private StringProperty licensePlate;
	private IntegerProperty km;
	private ObjectProperty<PriceCategory> category;
	private ObjectProperty<Agency> agency;
	private ObjectProperty<Status> status;
	public Car() {
		super();
		this.id = new SimpleIntegerProperty(0);
		this.model = new SimpleStringProperty();
		this.licensePlate = new SimpleStringProperty();
		this.km = new SimpleIntegerProperty();
		this.category = new SimpleObjectProperty<>();
		this.agency = new SimpleObjectProperty<>();
		this.status = new SimpleObjectProperty<>();
	}
	public Car(String model, String licensePlate, Integer km, PriceCategory category,
			Agency agency, Status status) {
		this.id = new SimpleIntegerProperty(0);
		this.model = new SimpleStringProperty(model);
		this.licensePlate = new SimpleStringProperty(licensePlate);
		this.km = new SimpleIntegerProperty(km);
		this.category = new SimpleObjectProperty<>(category);
		this.agency = new SimpleObjectProperty<>(agency);
		this.status = new SimpleObjectProperty<>(status);
	}
	public Car(int id, String model, String licensePlate, Integer km, PriceCategory category,
			Agency agency, Status status) {
		this.id = new SimpleIntegerProperty(id);
		this.model = new SimpleStringProperty(model);
		this.licensePlate = new SimpleStringProperty(licensePlate);
		this.km = new SimpleIntegerProperty(km);
		this.category = new SimpleObjectProperty<>(category);
		this.agency = new SimpleObjectProperty<>(agency);
		this.status = new SimpleObjectProperty<>(status);
	}
	public Integer getId(){
		return id.get();
	}
	public String getModel() {
		return model.get();
	}
	public String getLicensePlate() {
		return licensePlate.get();
	}
	public int getKm() {
		return km.get();
	}
	public PriceCategory getCategory() {
		return category.get();
	}
	public Agency getAgency() {
		return agency.get();
	}
	public Status getStatus() {
		return status.get();
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public void setModel(String model) {
		this.model.set(model);
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate.set(licensePlate);
	}
	public void setKm(Integer km) {
		this.km.set(km);
	}
	public void setCategory(PriceCategory category) {
		this.category.set(category);
	}
	public void setAgency(Agency agency) {
		this.agency.set(agency);
	}
	public void setStatus(Status status) {
		this.status.set(status);
	}
	public boolean isAvaible(){
		return status.equals("avaible");
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public StringProperty modelProperty(){
		return model;
	}
	public StringProperty licensePlateProperty(){
		return licensePlate;
	}
	public IntegerProperty kmProperty(){
		return km;
	}
	public ObjectProperty<PriceCategory> categoryProperty(){
		return category;
	}
	public ObjectProperty<Agency> agencyProperty(){
		return agency;
	}
	public ObjectProperty<Status> statusProperty(){
		return status;
	}
	public StringProperty endUserStringProperty() {
		return model;
	}
	@Override
	public String toString() {
		return "Car [id=" + id.get() + ", model=" + model.get() + ", licensePlate="
				+ licensePlate.get() + ", km=" + km.get() + ", category=" + category.get()
				+ ", agency=" + agency.get() + ", status=" + status.get() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Car))
			return false;
		Car other = (Car) obj;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		return true;
	}
}
