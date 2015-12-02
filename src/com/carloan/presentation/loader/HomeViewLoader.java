package com.carloan.presentation.loader;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomeViewLoader extends Loader{
	public HomeViewLoader(){};

	@Override
	public void load() {
		try {
			String path = VIEW_PATH.replaceFirst("[?]", "HomeView");
			FXMLLoader loader = new FXMLLoader(Loader.class.getResource(path));
			scene.setRoot((Parent) loader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
