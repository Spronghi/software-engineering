package com.carloan.presentation.controller.overview;

import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomerOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	private Customer customer;

	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model){
		customer = (Customer)model;
		operationButton.setText(operation);
		
		switch(operation){
		case ModelControl.OPEN:
			openSession();
			break;
		case ModelControl.NEW:
			createSession();
			break;
		case ModelControl.EDIT:
			editSession();
			break;
		case ModelControl.DELETE:
			deleteSession();
			break;
		}
	}
	private void openSession(){
		Label firstNameLabel = new Label(customer.getFirstName());
		Label lastNameLabel = new Label(customer.getLastName());
		Label emailLabel = new Label(customer.getEmail());
		Label telephoneLabel = new Label(customer.getTelephone());

		grid.add(firstNameLabel, 1, 0);
		grid.add(lastNameLabel, 1, 2);
		grid.add(emailLabel, 1, 4);
		grid.add(telephoneLabel, 1, 6);
		
		operationButton.setVisible(false);
	}
	private void createSession(){
		TextField firstNameField = new TextField();
		TextField lastNameField = new TextField();
		TextField emailField = new TextField();
		TextField telephoneField = FXFactory.createTextField("([A-z])");

		grid.add(firstNameField, 1, 0);
		grid.add(lastNameField, 1, 2);
		grid.add(emailField, 1, 4);
		grid.add(telephoneField, 1, 6);
		
		operationButton.setOnAction(e->{
			customer.setFirstName(firstNameField.getText());
			customer.setLastName(lastNameField.getText());
			customer.setEmail(emailField.getText());
			customer.setTelephone(telephoneField.getText());
			try {
                ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER).create(customer);
				FXAlert.information("Customer created", "Customer created successfully");
				changed = true;
				stage.close();
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while create customer");
			}
		});
	}
	private void editSession(){
		TextField firstNameField = new TextField(customer.getFirstName());
		TextField lastNameField = new TextField(customer.getLastName());
		TextField emailField = new TextField(customer.getEmail());
		TextField telephoneField = FXFactory.createTextField("([A-z])");
		
		telephoneField.setText(customer.getTelephone());
		
		grid.add(firstNameField, 1, 0);
		grid.add(lastNameField, 1, 2);
		grid.add(emailField, 1, 4);
		grid.add(telephoneField, 1, 6);
		
		operationButton.setOnAction(e->{
			customer.setFirstName(firstNameField.getText());
			customer.setLastName(lastNameField.getText());
			customer.setEmail(emailField.getText());
			customer.setTelephone(telephoneField.getText());
			try {
				ButtonType result = FXAlert.confirmAction("Update", "Customer");
				if (result == ButtonType.OK){
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER).update(customer);
					changed=true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while updating customer");
			}
		});
	}
	private void deleteSession(){
		openSession();

		operationButton.setVisible(true);
		operationButton.setOnAction(e->{
			ButtonType result = FXAlert.confirmAction("Delete", "Customer");
            if (result == ButtonType.OK) {
                if (ServiceFactory.getModelFactory().<Contract>getInstance(ServiceControl.CONTRACT).getAll().stream().noneMatch(c -> c.getCustomer().equals(customer))){
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER).delete(customer);
                    changed = true;
                } else {
                    FXAlert.warning("Can't delete customer", "He is contained in a contract");
                }
				stage.close();
			}
		});
	}
}
