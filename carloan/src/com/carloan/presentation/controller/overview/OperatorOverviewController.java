package com.carloan.presentation.controller.overview;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Contract;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import com.carloan.business.model.Operator;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.exception.CommonServiceException;

public class OperatorOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	
	private Operator operator;
	
	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model) {
		operator = (Operator)model;
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
		Label firstNameLabel = new Label(operator.getFirstName());
		Label lastNameLabel = new Label(operator.getLastName());
		Label emailLabel = new Label(operator.getEmail());
        Hyperlink agencyLink = new Hyperlink(operator.getAgency().getName());
        Label usernameLabel = new Label(operator.getUsername());

        agencyLink.setOnAction(e->FXLoader.loadOverview(operator.getAgency(),ModelControl.OPEN));

		grid.add(firstNameLabel, 1, 0);
		grid.add(lastNameLabel, 1, 2);
		grid.add(emailLabel, 1, 4);
		grid.add(agencyLink, 1, 6);
        grid.add(usernameLabel, 1, 8);
		
		operationButton.setVisible(false);
	}
	private void createSession(){
		TextField firstNameField = new TextField();
		TextField lastNameField = new TextField();
		TextField emailField = new TextField();
        ModelMenuButton<Agency> agencyMenu = new ModelMenuButton<>("Select Agency");
        TextField usernameField = new TextField();
		Label passwordLabel = new Label("Password");
		Label repeatPasswordLabel = new Label("Repeat Password");
		PasswordField passwordField = new PasswordField();
		PasswordField repeatPasswordField = new PasswordField();

        ServiceFactory.getModelFactory().<Agency>getInstance(ServiceControl.AGENCY).getAll().stream().forEach(agency-> FXUtil.addMenuItem(agencyMenu,agency.getName(),agency));
        agencyMenu.setPrefWidth(200);

        grid.add(firstNameField, 1, 0);
		grid.add(lastNameField, 1, 2);
		grid.add(emailField, 1, 4);
        grid.add(agencyMenu, 1, 6);
		grid.add(usernameField, 1, 8);
        FXUtil.addGridRow(grid, passwordLabel, passwordField, true);
        FXUtil.addGridRow(grid, repeatPasswordLabel, repeatPasswordField, true);
		
		operationButton.setOnAction(e->{
			operator.setFirstName(firstNameField.getText());
			operator.setLastName(lastNameField.getText());
			operator.setEmail(emailField.getText());
			operator.setUsername(usernameField.getText());
			operator.setPassword(passwordField.getText());
            operator.setAgency(agencyMenu.getModel());
			try {
				if(!passwordField.getText().equals(repeatPasswordField.getText())) {
                    throw new CommonServiceException("Password doesn't match");
                }
                ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR).create(operator);
				FXAlert.information("Operator created", "Operator created successfully");
				changed = true;
                stage.close();
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while create operator");
			}
		});
	}
	private void editSession(){
		TextField firstNameField = new TextField(operator.getFirstName());
		TextField lastNameField = new TextField(operator.getLastName());
		TextField emailField = new TextField(operator.getEmail());
        ModelMenuButton<Agency> agencyMenu = new ModelMenuButton<>(operator.getAgency().getName(),operator.getAgency());
        TextField usernameField = new TextField(operator.getUsername());

        ServiceFactory.getModelFactory().<Agency>getInstance(ServiceControl.AGENCY).getAll().stream().forEach(agency->FXUtil.addMenuItem(agencyMenu,agency.getName(),agency));
        agencyMenu.setPrefWidth(200);

        grid.add(firstNameField, 1, 0);
		grid.add(lastNameField, 1, 2);
		grid.add(emailField, 1, 4);
        grid.add(agencyMenu, 1, 6);
		grid.add(usernameField, 1, 8);

		operationButton.setOnAction(e->{
			operator.setFirstName(firstNameField.getText());
			operator.setLastName(lastNameField.getText());
			operator.setEmail(emailField.getText());
			operator.setUsername(usernameField.getText());
            operator.setAgency(agencyMenu.getModel());
			try {
				ButtonType result = FXAlert.confirmAction("Update","Operator");
				if (result == ButtonType.OK){
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR).update(operator);
					changed = true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while update operator");
			}
		});
	}
	private void deleteSession(){
		openSession();
		
		operationButton.setVisible(true);
		operationButton.setOnAction(e->{
			ButtonType result = FXAlert.confirmAction("Delete","Operator");
            if (result == ButtonType.OK){
                if(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().equals(operator)) {
                    FXAlert.warning("Can't delete you", "We still need you!");
                } else if(!ServiceFactory.getModelFactory().<Contract>getInstance(ServiceControl.CONTRACT).getAll().stream().noneMatch(c->c.getOperator().equals(operator))){
                    FXAlert.warning("Can't delete operator", "He is contained in a contract");
                } else {
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR).delete(operator);
                    changed = true;
                }
				stage.close();
			}
		});
	}
}
