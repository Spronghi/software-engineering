package com.carloan.presentation.loader;

import javafx.scene.Scene;

class MainViewLoader extends Loader{
	public MainViewLoader(Scene scene) {
		super(scene);
	}
	public void load(){
		LoaderFactory.getInstance("HomeView").load();
	}
}
