package com.carloan.presentation.controller.overview;

import java.util.Optional;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Location;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.service.agency.AgencyService;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.location.LocationService;
import com.carloan.service.login.LoginService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AgencyOverviewController extends OverviewController{
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button cancelButton;
	private Button confirmButton;
	private Button deleteButton;
	
	private Label numberLabel;
	private Label nameLabel;
	private Label cityLabel;
	private Label postalNumberLabel;
	private Label roadLabel;
	private TextField nameField;
	private TextField cityField;
	private TextField postalNumberField;
	private TextField roadField;
	private Agency agency;
	private Location location;
	@FXML
	public void initialize(){
		numberLabel = new Label();
		nameLabel = new Label();
		cityLabel = new Label();
		postalNumberLabel = new Label();
		roadLabel = new Label();
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		agency = (Agency)model;
		location = agency.getLocation();

		if(agency.getNumber() == 0 ){
			numberLabel.setText("Not defined");
			gridPane.add(numberLabel, 1, 0);
			
			nameField = new TextField();
			gridPane.add(nameField, 1, 2);
			
			cityField = new TextField();
			gridPane.add(cityField, 1, 4);

			postalNumberField = new TextField();
			gridPane.add(postalNumberField, 1, 6);
			
			roadField = new TextField();
			gridPane.add(roadField, 1, 8);
			
			confirmButton = new Button();
			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			buttonBar.getButtons().add(confirmButton);
			
			deleteButton = new Button();
			deleteButton.setText("Clear");
			deleteButton.setOnAction(this::handleClear);
			buttonBar.getButtons().add(deleteButton);
		} else {
			numberLabel.setText(Integer.toString(agency.getNumber()));
			gridPane.add(numberLabel, 1, 0);

			nameLabel.setText(agency.getName());
			gridPane.add(nameLabel, 1, 2);

			cityLabel.setText(location.getCity());
			gridPane.add(cityLabel, 1, 4);

			postalNumberLabel.setText(location.getPostalNo());
			gridPane.add(postalNumberLabel, 1, 6);

			roadLabel.setText(location.getRoad());
			gridPane.add(roadLabel, 1, 8);
			if(LoginService.getCurrentOperator().isAdmin()){
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
		location = new Location(cityField.getText(),postalNumberField.getText(),roadField.getText());
		agency.setName(nameField.getText());
		agency.setLocation(location);
		try {
			LocationService.create(location);
			AgencyService.create(agency);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while create agency");
		}
	}
	private void handleClear(ActionEvent event){
		nameField.setText("");
		roadField.setText("");
		postalNumberField.setText("");
		cityField.setText("");
	}
	private void handleEdit(ActionEvent event){
		nameField = new TextField(nameLabel.getText());
		gridPane.add(nameField , 1, 2);
		
		cityField = new TextField(cityLabel.getText());
		gridPane.add(cityField, 1, 4);

		postalNumberField = new TextField(postalNumberLabel.getText());
		gridPane.add(postalNumberField, 1, 6);
		
		roadField = new TextField(roadLabel.getText());
		gridPane.add(roadField, 1, 8);
		
		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOkUpdate);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleOkUpdate(ActionEvent event){
		location.setCity(cityField.getText());
		location.setPostalNo(postalNumberField.getText());
		location.setRoad(roadField.getText());
		agency.setName(nameField.getText());
		try {
			LocationService.update(location);
			AgencyService.update(agency);
			stage.close();	
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update agency");

		}
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Agency");
		alert.setHeaderText("Are you sure to delete this agency?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			AgencyService.delete(agency);
			LocationService.delete(location);
		}
		stage.close();	
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}

}
