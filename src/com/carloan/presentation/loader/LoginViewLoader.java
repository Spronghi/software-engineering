package com.carloan.presentation.loader;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

class LoginViewLoader extends Loader{
	public void load(){
		try {
			String path = VIEW_PATH.replaceFirst("[?]", "LoginView");
			FXMLLoader loader = new FXMLLoader(Loader.class.getResource(path));
			scene.setRoot((Parent) loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
