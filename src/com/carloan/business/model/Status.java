package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Status implements Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6742987071696511737L;
	private IntegerProperty id;
	private StringProperty status;
	
	public Status() {
		id = new SimpleIntegerProperty(0);
		status = new SimpleStringProperty();
	}
	public Status(String status){
		this.id = new SimpleIntegerProperty(0);
		this.status = new SimpleStringProperty(status);
	}
	public Status(int id, String status){
		this.id = new SimpleIntegerProperty(id);
		this.status = new SimpleStringProperty(status);
	}
	@Override
	public Integer getId() {
		return id.get();
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public String get(){
		return status.get();
	}
	public void set(String status){
		this.status.set(status);
	}
	public StringProperty endUserStringProperty() {
		return status;
	}
	@Override
	public String toString(){
		return status.get();
	}
}
