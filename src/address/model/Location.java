package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {
    private final StringProperty city;
    private final StringProperty cap;
    private final StringProperty address1;
    private final StringProperty address2;
    private final StringProperty address3;
    
    public Location(String city, String cap, String address1, 
            String address2, String address3) {
        this.city = new SimpleStringProperty(city);
        this.cap = new SimpleStringProperty(cap);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.address3 = new SimpleStringProperty(address3);
    }
    public String getCity(){
        return this.city.get();
    }
    public String getCap(){
        return this.cap.get();
    }
    public String getAddress1(){
        return this.address1.get();
    }
    public String getAddress2(){
        return this.address2.get();
    }
    public String getAddress3(){
        return this.address3.get();
    }
    public void setCity(String city){
        this.city.set(city);
    }
    public void setCap(String cap){
        this.cap.set(cap);
    }
    public void setAddress1(String address){
        this.address1.set(address);
    }
    public void setAddress2(String address){
        this.address2.set(address);
    }
    public void setAddress3(String address){
        this.address3.set(address);
    }
    public StringProperty cityProperty() {
        return city;
    }
    public StringProperty capProperty() {
        return cap;
    }
    public StringProperty address1Property() {
        return address1;
    }
    public StringProperty address2Property() {
        return address2;
    }
    public StringProperty address3Property() {
        return address3;
    }

}