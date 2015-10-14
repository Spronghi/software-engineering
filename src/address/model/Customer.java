package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty telephone;
    private final StringProperty email;
    private final StringProperty username;
    private final StringProperty password;
    
    public Customer(String firstName, String lastName, String telephone, 
            String email, String username, String password) {
        this.firstName = new SimpleStringProperty(firstName);;
        this.lastName = new SimpleStringProperty(lastName);;
        this.telephone = new SimpleStringProperty(telephone);;
        this.email = new SimpleStringProperty(email);;
        this.username = new SimpleStringProperty(username);;
        this.password = new SimpleStringProperty(password);;
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getTelephone() {
        return telephone.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getUsername() {
        return username.get();
    }
    public String getPassword() {
        return password.get();
    }
    public void setFirstName(String firstName){
        this.firstName.set(firstName);
    }
    public void setLastName(String lastName){
        this.lastName.set(lastName);
    }
    public void setTelephone(String telephone){
        this.telephone.set(telephone);
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public void setUsername(String username){
        this.username.set(username);
    }
    public void setPassword(String password){
        this.password.set(password);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty passwordProperty() {
        return password;
    }
}