package com.carloan.presentation.loader.overview;

import javafx.stage.Stage;

public abstract class OverviewLoader {
	protected static final String VIEW_PATH = "/com/carloan/presentation/view/?.fxml";
	protected Stage stage;
	protected Object model;

	public OverviewLoader(){};

	public void setModel(Object model){
		this.model=model;
	}
	public void initOwner(Stage stage){
		this.stage=stage;
	}
	public abstract void load();
}
