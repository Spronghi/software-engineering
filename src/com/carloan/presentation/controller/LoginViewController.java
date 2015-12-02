package com.carloan.presentation.controller;

import com.carloan.presentation.loader.LoaderFactory;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.login.LoginService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends Controller{
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	public LoginViewController(){}
	@FXML
	private void initialize(){
		usernameField.setText("");
		passwordField.setText("");
	}
	@FXML
	private void handleLogin(){
		try {
			LoginService.login(usernameField.getText(), passwordField.getText());
			if(LoginService.isLogged() && LoginService.getCurrentOperator().isAdmin())
				LoaderFactory.getInstance("HomeLoaderView").load();
		} catch (CommonServiceException e) {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(stage);
	        alert.setTitle("Warning!");
	        alert.setContentText(e.getLocalizedMessage());
	        alert.showAndWait();
        }
	}
}
