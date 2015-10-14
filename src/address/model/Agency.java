package address.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Agency {
    private final StringProperty name;
    private final ObjectProperty<Location> location;
    public Agency(String name, Location location){
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleObjectProperty<Location>(location);
    }
    public String getName(){
        return this.name.get();
    }
    public Location getLocation(){
        return this.location.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public StringProperty nameProperty(){
        return name;
    }
    public ObjectProperty<Location> locationProperty(){
        return location;
    }
}