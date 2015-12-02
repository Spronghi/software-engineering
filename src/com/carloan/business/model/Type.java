package com.carloan.business.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Type implements Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7917753818021184755L;
	private int id;
	private StringProperty type;
	
	public Type() {}
	
	public Type(int id, String type) {
		this.id=id;
		this.type = new SimpleStringProperty(type);
	}

	public Integer getId() {
		return id;
	}

	public String get() {
		return type.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void set(String type) {
		this.type.set(type);
	}
	public StringProperty endUserStringProperty() {
		return type;
	}
}
