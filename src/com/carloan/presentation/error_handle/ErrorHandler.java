package com.carloan.presentation.error_handle;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.carloan.service.exception.CommonServiceException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public enum ErrorHandler {
	INSTANCE;
	public static void handle(CommonServiceException e){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning!");
		alert.setContentText(e.getLocalizedMessage());
		alert.showAndWait();
	}
	public static void handle(CommonServiceException e, String title){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(e.getLocalizedMessage());
		alert.showAndWait();
	}
	public static void handle(Exception ex){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Java Exception");
		alert.setHeaderText(ex.toString());
		alert.setResizable(true);
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace();
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
