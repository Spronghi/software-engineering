package com.carloan.presentation.controller.overview;

import javafx.stage.Stage;

public abstract class OverviewController {
	protected Stage stage;
	protected String operation;
	protected boolean changed;
	
	public abstract void setModel(Object model);
	
	public void setOperation(String operation){
		this.operation = operation;
	}
	public boolean isChanged(){
		return changed;
	}
	public void setStage(Stage stage){
		this.stage=stage;
	}
}
