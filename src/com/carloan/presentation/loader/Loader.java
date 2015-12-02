package com.carloan.presentation.loader;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Loader {
	protected static final String VIEW_PATH = "/com/carloan/presentation/view/?.fxml";
	protected static Scene scene;
	protected static Stage stage;
	public Loader(){};
	public Loader(Scene scene){
		Loader.scene = scene;
	}
	public void initOwner(Stage stage){
		Loader.stage=stage;
	}
	public abstract void load();
}
