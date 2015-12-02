package com.carloan.presentation.loader;

import java.io.IOException;

import com.carloan.presentation.controller.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

class OpenContractViewLoader extends Loader {
	public OpenContractViewLoader() {}

	@Override
	public void load() {
		try {
			String path = VIEW_PATH.replaceFirst("[?]", "OpenContractView");
			FXMLLoader loader = new FXMLLoader(Loader.class.getResource(path));
			Parent page = (Parent) loader.load();

			Stage currentStage = new Stage();
			currentStage.setTitle("Overview");
			currentStage.initModality(Modality.WINDOW_MODAL);
			currentStage.initOwner(stage);
			currentStage.setScene(new Scene(page));

			Controller controller = loader.getController();
			controller.setStage(currentStage);
	        currentStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
