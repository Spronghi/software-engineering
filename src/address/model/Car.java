package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
    private final StringProperty licensePlate;
    private final StringProperty lastKm;
    private final StringProperty category;
    private final StringProperty status;
    
    public Car(String licensePlate, String lastKm, String category, String status) {
        this.licensePlate = new SimpleStringProperty(licensePlate);
        this.lastKm = new SimpleStringProperty(lastKm);
        this.category = new SimpleStringProperty(category);
        this.status = new SimpleStringProperty(status);
    }
    public String getLicensePlate() {
        return licensePlate.get();
    }
    public String getLastKm() {
        return lastKm.get();
    }
    public String getCategory(){
        return category.get();
    }
    public String getStatus(){
        return status.get();
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate.set(licensePlate);
    }
    public void setLastKm(String lastKm) {
        this.licensePlate.set(lastKm);;
    }
    public void setCategory(String category){
        this.category.set(category);
    }
    public void setStatus(String status){
        this.status.set(status);
    }
    public StringProperty licensePlateProperty() {
        return licensePlate;
    }
    public StringProperty lastKmProperty() {
        return lastKm;
    }
    public StringProperty categoryProperty(){
        return category;
    }
    public StringProperty statusProperty(){
        return status;
    }
}