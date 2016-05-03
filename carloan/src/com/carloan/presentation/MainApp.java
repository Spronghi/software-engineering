package com.carloan.presentation;

import java.io.IOException;

import com.carloan.presentation.control.LoaderControl;
import com.carloan.presentation.factory.FXLoader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws IOException{
		Scene scene = new Scene(new AnchorPane());
		stage.setTitle("Carloan");
		stage.setScene(scene);
		stage.show();
        FXLoader.loadView(LoaderControl.LOGIN_VIEW, stage);
    }
	public static void main(String[] args){ launch(args); }
}
