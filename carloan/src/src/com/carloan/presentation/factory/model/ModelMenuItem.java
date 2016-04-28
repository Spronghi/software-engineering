package com.carloan.presentation.factory.model;

import javafx.scene.control.MenuItem;

public class ModelMenuItem<T> extends MenuItem {
	private T model;
	
	public ModelMenuItem(String text, T model){ 
		super(text); 
		this.model=model;
	}
	public T getModel(){ 
		return model; 
	}
}
