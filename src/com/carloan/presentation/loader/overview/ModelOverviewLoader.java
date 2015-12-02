package com.carloan.presentation.loader.overview;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.carloan.presentation.controller.overview.OverviewController;
import com.carloan.presentation.controller.overview.internal.CategoryRegister;
import com.carloan.presentation.error_handle.ErrorHandler;

class ModelOverviewLoader extends OverviewLoader {
	private Stage currentStage;
	@Override
	public void load() {
		try {
			String fxmlName = CategoryRegister.get(model.getClass()) + "Overview";
			String path = VIEW_PATH.replaceFirst("[?]", fxmlName);
			FXMLLoader loader = new FXMLLoader(OverviewLoader.class.getResource(path));
			Parent page = (Parent) loader.load();

			currentStage = new Stage();
			currentStage.setTitle("Overview");
			currentStage.initModality(Modality.WINDOW_MODAL);
			currentStage.initOwner(stage);
			currentStage.setScene(new Scene(page));

			OverviewController controller = loader.getController();
			controller.setStage(currentStage);
			controller.setModel(model);

	        currentStage.showAndWait();
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}
}
