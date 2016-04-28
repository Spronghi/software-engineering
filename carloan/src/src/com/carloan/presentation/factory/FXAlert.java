package com.carloan.presentation.factory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public enum FXAlert {
	INSTANCE;
	public static ButtonType confirmation(String title, String header, String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait().get();
	}
	public static ButtonType confirmAction(String action,String model){
		String title = "? ?".replaceFirst("[?]", action).replaceFirst("[?]", model);
		String header = "Are you sure to ? this ??".replaceFirst("[?]", action.toLowerCase())
				.replaceFirst("[?]", model.toLowerCase());
		String content = "Press ok to confirm";
		return FXAlert.confirmation(title,header,content);
	}
	public static void information(String title, String header, String content){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	public static void information(String titleAndHeader, String content){
		information(titleAndHeader,titleAndHeader,content);
	}
    public static void warning(String title,String content){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
	public static void warning(Exception e, String title){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(e.getLocalizedMessage());
		alert.showAndWait();
	}
	public static void warning(Exception e){
		warning(e,"Warning!");
	}
}
