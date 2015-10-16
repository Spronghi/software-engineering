package address.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
	private final int id;
    private final StringProperty name;
    private final StringProperty licensePlate;
    private final IntegerProperty lastKm;
    private final StringProperty category;
    private final StringProperty status;
    public Car(){
    	this(-1, null, null, 0, null, null);
    }
    public Car(int id, String name, String licensePlate, int lastKm, String category, String status) {
        this.id = id;
    	this.name = new SimpleStringProperty(name);
        this.licensePlate = new SimpleStringProperty(licensePlate);
        this.lastKm = new SimpleIntegerProperty(lastKm);
        this.category = new SimpleStringProperty(category);
        this.status = new SimpleStringProperty(status);
    }
    public Integer getId(){
    	return id;
    }
    public String getName(){
        return name.get();
    }
    public String getLicensePlate() {
        return licensePlate.get();
    }
    public Integer getLastKm() {
        return lastKm.get();
    }
    public String getCategory(){
        return category.get();
    }
    public String getStatus(){
        return status.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate.set(licensePlate);
    }
    public void setLastKm(int lastKm) {
        this.lastKm.set(lastKm);
    }
    public void setCategory(String category){
        this.category.set(category);
    }
    public void setStatus(String status){
        this.status.set(status);
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty licensePlateProperty() {
        return licensePlate;
    }
    public IntegerProperty lastKmProperty() {
        return lastKm;
    }
    public StringProperty categoryProperty(){
        return category;
    }
    public StringProperty statusProperty(){
        return status;
    }
}