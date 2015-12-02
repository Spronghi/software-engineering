package com.carloan.presentation.controller.overview;

import javafx.stage.Stage;

public abstract class OverviewController {
	protected Stage stage;
	
	public abstract void setModel(Object model);

	public void setStage(Stage stage){
		this.stage=stage;
	}
}
