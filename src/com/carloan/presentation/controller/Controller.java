package com.carloan.presentation.controller;

import javafx.stage.Stage;

public abstract class Controller {
	protected static final String VIEW_PATH = "/com/carloan/presentation/view/?.fxml";
	protected Stage stage;
	
	public Controller(){}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
}
