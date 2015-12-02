package com.carloan.presentation.controller.overview;

import java.util.Optional;

import com.carloan.business.model.Customer;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.service.customer.CustomerService;
import com.carloan.service.exception.CommonServiceException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomerOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button cancelButton;
	
	private Button confirmButton;
	private Button deleteButton;
	private Label firstNameLabel;
	private Label lastNameLabel;
	private Label telephoneLabel;
	private Label emailLabel;
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField emailField;
	private TextField telephoneField;
	
	private Customer customer;

	@FXML
	public void initialize(){
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		customer = (Customer)model;
		if(customer.getId() == 0){
			firstNameField = new TextField();
			gridPane.add(firstNameField, 1, 0);

			lastNameField = new TextField();
			gridPane.add(lastNameField, 1, 2);
			
			emailField = new TextField();
			gridPane.add(emailField, 1, 4);

			telephoneField = new TextField();
			gridPane.add(telephoneField, 1, 6);
			
			confirmButton = new Button();
			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			buttonBar.getButtons().add(confirmButton);
			
			deleteButton = new Button();
			deleteButton.setText("Clear");
			deleteButton.setOnAction(this::handleClear);
			buttonBar.getButtons().add(deleteButton);
		} else {
			firstNameLabel = new Label(customer.getFirstName());
			gridPane.add(firstNameLabel, 1, 0);
			
			lastNameLabel = new Label(customer.getLastName());
			gridPane.add(lastNameLabel, 1, 2);

			emailLabel = new Label(customer.getEmail());
			gridPane.add(emailLabel, 1, 4);
			
			telephoneLabel = new Label(customer.getTelephone());
			gridPane.add(telephoneLabel, 1, 6);
			if(true){
				confirmButton = new Button();
				confirmButton.setText("Edit");
				confirmButton.setOnAction(this::handleEdit);
				buttonBar.getButtons().add(confirmButton);
				
				deleteButton = new Button();
				deleteButton.setText("Delete");
				deleteButton.setOnAction(this::handleDelete);
				buttonBar.getButtons().add(deleteButton);
			}
		}
	}
	private void handleAdd(ActionEvent event){
		customer.setFirstName(firstNameField.getText());
		customer.setLastName(lastNameField.getText());
		customer.setEmail(emailField.getText());
		customer.setTelephone(telephoneField.getText());
		try {
			CustomerService.create(customer);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while create customer");

		}
	}
	private void handleClear(ActionEvent event){
		firstNameField.setText("");
		lastNameField.setText("");
		telephoneField.setText("");
		emailField.setText("");
	}
	private void handleEdit(ActionEvent event){
		firstNameField = new TextField(firstNameLabel.getText());
		gridPane.add(firstNameField, 1, 0);

		lastNameField = new TextField(lastNameLabel.getText());
		gridPane.add(lastNameField, 1, 2);
		
		emailField = new TextField(emailLabel.getText());
		gridPane.add(emailField, 1, 4);

		telephoneField = new TextField(telephoneLabel.getText());
		gridPane.add(telephoneField, 1, 6);
		
		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOkUpdate);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleOkUpdate(ActionEvent event){
		customer.setFirstName(firstNameField.getText());
		customer.setLastName(lastNameField.getText());
		customer.setEmail(emailField.getText());
		customer.setTelephone(telephoneField.getText());
		try {
			CustomerService.update(customer);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update customer");
		}
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Customer");
		alert.setHeaderText("Are you sure to delete this customer?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			CustomerService.delete(customer);
		}
		stage.close();	
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
