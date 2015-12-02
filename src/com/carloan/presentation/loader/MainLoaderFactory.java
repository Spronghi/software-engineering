package com.carloan.presentation.loader;

import javafx.scene.Scene;

public enum MainLoaderFactory {
	INSTANCE;
	public static Loader getInstance(Scene scene){
		return new MainViewLoader(scene);
	}
}
