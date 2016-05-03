package com.carloan.presentation.factory.model;

import javafx.scene.control.MenuButton;

public class ModelMenuButton<T> extends MenuButton {
	private T model;

	public ModelMenuButton() {}
	public ModelMenuButton(String text) {
		super(text);
	}
	public ModelMenuButton(String text,T model) {
		super(text);
		this.model = model;
	}
	public void setModel(T model){
		this.model=model;
	}
	public T getModel(){
		return model;
	}
}
