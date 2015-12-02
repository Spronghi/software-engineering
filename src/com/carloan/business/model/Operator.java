package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Operator extends Person implements Model{
	private static final long serialVersionUID = -6155554449286497767L;
	private IntegerProperty id;
	private StringProperty username;
	private StringProperty password;
	public Operator(){
		super();
		this.id = new SimpleIntegerProperty(0);
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
	}
	public Operator(String firstName, String lastName, String email,
			String username, String password){
		super(firstName, lastName, email);
		this.id = new SimpleIntegerProperty();
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}
	public Integer getId(){
		return id.get();
	}
	public String getUsername() {
		return username.get();
	}
	public String getPassword() {
		return password.get();
	}
	public final void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public void setUsername(String username) {
		this.username.set(username);
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public boolean isAdmin(){
		return (username.get().equals("admin") && password.get().equals("admin"));
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public StringProperty usernameProperty() {
		return username;
	}
	public StringProperty passwordProperty() {
		return password;
	}
	public String toString() {
		return username.get();
	}
	public StringProperty endUserStringProperty() {
		return username;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Operator))
			return false;
		Operator other = (Operator) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
