package com.carloan.application;

import java.io.IOException;

import com.carloan.presentation.loader.MainLoaderFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws IOException{
		Scene scene = new Scene(new AnchorPane());
		MainLoaderFactory.getInstance(scene).load();
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) { launch(args); }
}
