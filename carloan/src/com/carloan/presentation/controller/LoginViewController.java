package com.carloan.presentation.controller;

import com.carloan.presentation.control.LoaderControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.exception.LoginException;
import com.carloan.service.ServiceFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends Controller{
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
    private Button loginButton;

    public LoginViewController(){}

    @FXML
	public void initialize(){
        loginButton.setOnAction(e->{
            try {
                ServiceFactory.getUtilFactory().getLoginService().login(usernameField.getText(), passwordField.getText());
                if(ServiceFactory.getUtilFactory().getLoginService().isLogged()) {
                    FXLoader.initializeTables();
                    FXLoader.loadView(LoaderControl.HOME_VIEW, stage);
                }
            } catch (LoginException ex) {
                FXAlert.warning(ex);
            }
        });
        usernameField.setOnAction(loginButton.getOnAction());
        passwordField.setOnAction(loginButton.getOnAction());
    }
}
