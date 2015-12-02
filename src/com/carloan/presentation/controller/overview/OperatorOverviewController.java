package com.carloan.presentation.controller.overview;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

import com.carloan.business.model.Operator;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.login.LoginService;
import com.carloan.service.operator.OperatorService;

public class OperatorOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private Button cancelButton;
	@FXML
	private Button confirmButton;
	@FXML
	private Button deleteButton;
	
	private Label firstNameLabel;
	private Label lastNameLabel;
	private Label emailLabel;
	private Label usernameLabel;
	private Label passwordLabel;
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField emailField;
	private TextField usernameField;
	private PasswordField passwordField;
	private PasswordField repeatPasswordField;
	private Operator operator;
	
	@FXML
	public void initialize(){
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		operator = (Operator)model;
		if(operator.getId() == 0){
			firstNameField = new TextField();
			gridPane.add(firstNameField, 1, 0);

			lastNameField = new TextField();
			gridPane.add(lastNameField, 1, 2);
			
			emailField = new TextField();
			gridPane.add(emailField, 1, 4);
			
			usernameField = new TextField();
			gridPane.add(usernameField, 1, 6);

			passwordField = new PasswordField();
			gridPane.add(passwordField, 1, 8);

			gridPane.add(new Label("Repeat Password"), 0, 10);
			gridPane.getRowConstraints().get(10).setPrefHeight(30);
			repeatPasswordField = new PasswordField();
			gridPane.add(repeatPasswordField, 1, 10);
			
			gridPane.add(new Separator(), 0, 11);
			gridPane.add(new Separator(), 1, 11);

			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			
			deleteButton.setText("Clear");
			deleteButton.setOnAction(this::handleClear);
		} else {
			firstNameLabel = new Label(operator.getFirstName());
			gridPane.add(firstNameLabel, 1, 0);
			
			lastNameLabel = new Label(operator.getLastName());
			gridPane.add(lastNameLabel, 1, 2);

			emailLabel = new Label(operator.getEmail());
			gridPane.add(emailLabel, 1, 4);
			
			usernameLabel = new Label(operator.getUsername());
			gridPane.add(usernameLabel, 1, 6);
			
			passwordLabel = new Label(operator.getPassword());
			gridPane.add(passwordLabel, 1, 8);
			
			if(LoginService.getCurrentOperator().isAdmin()){
				confirmButton.setText("Edit");
				confirmButton.setOnAction(this::handleEdit);
				
				deleteButton.setText("Delete");
				deleteButton.setOnAction(this::handleDelete);
			}
		}
	}
	private void handleAdd(ActionEvent event){		
		operator.setFirstName(firstNameField.getText());
		operator.setLastName(lastNameField.getText());
		operator.setEmail(emailField.getText());
		operator.setUsername(usernameField.getText());
		operator.setPassword(passwordField.getText());
		try {
			if(!passwordField.getText().equals(repeatPasswordField.getText()))
				throw new CommonServiceException("Passwords don't match");

			OperatorService.create(operator);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while create operator");
		}
	}
	private void handleClear(ActionEvent event){
		firstNameField.setText("");
		lastNameField.setText("");
		emailField.setText("");
		usernameField.setText("");
		passwordField.setText("");
		repeatPasswordField.setText("");
	}
	private void handleEdit(ActionEvent event){
		firstNameField = new TextField(firstNameLabel.getText());
		gridPane.add(firstNameField, 1, 0);

		lastNameField = new TextField(lastNameLabel.getText());
		gridPane.add(lastNameField, 1, 2);
		
		emailField = new TextField(emailLabel.getText());
		gridPane.add(emailField, 1, 4);

		usernameField = new TextField(usernameLabel.getText());
		gridPane.add(usernameField, 1, 6);
		
		passwordField = new PasswordField();
		passwordField.setText(passwordLabel.getText());
		gridPane.add(passwordField, 1, 8);
		
		gridPane.getRowConstraints().get(10).setPrefHeight(30);
		repeatPasswordField = new PasswordField();
		repeatPasswordField.setText(passwordLabel.getText());
		gridPane.add(repeatPasswordField, 1, 10);
		
		gridPane.add(new Label("Repeat Password"), 0, 10);
		gridPane.add(new Separator(), 0, 11);
		gridPane.add(new Separator(), 1, 11);
		
		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOkUpdate);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleOkUpdate(ActionEvent event){
		operator.setFirstName(firstNameField.getText());
		operator.setLastName(lastNameField.getText());
		operator.setEmail(emailField.getText());
		operator.setUsername(usernameField.getText());
		operator.setPassword(passwordField.getText());
		try {
			if(!passwordField.getText().equals(repeatPasswordField.getText()))
				throw new CommonServiceException("Passwords don't match");
			
			OperatorService.update(operator);
			stage.close();	
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update operator");
		}
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Operator");
		alert.setHeaderText("Are you sure to delete this operator?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			OperatorService.delete(operator);
		}
		stage.close();	
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
