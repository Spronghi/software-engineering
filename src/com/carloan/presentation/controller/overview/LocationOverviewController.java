package com.carloan.presentation.controller.overview;

import java.util.Optional;

import com.carloan.business.model.Location;
import com.carloan.presentation.error_handle.ErrorHandler;
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

public class LocationOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button cancelButton;
	
	private Button confirmButton;
	private Button deleteButton;
	
	private Label cityLabel;
	private Label roadLabel;
	private Label postalNoLabel;
	private TextField cityField;
	private TextField roadField;
	private TextField postalNoField;
	private Location location;
	@FXML
	public void initialize(){
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		location = (Location)model;
		if(location.getId()==0){
			cityField=new TextField();
			gridPane.add(cityField,1,0);

			roadField=new TextField();
			gridPane.add(roadField,1,2);

			postalNoField=new TextField();
			gridPane.add(postalNoField,1,4);
			
			confirmButton = new Button();
			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			buttonBar.getButtons().add(confirmButton);
			
			deleteButton = new Button();
			deleteButton.setText("Clear");
			buttonBar.getButtons().add(deleteButton);
			deleteButton.setOnAction(this::handleClear);
		} else {
			cityLabel = new Label(location.getCity());
			gridPane.add(cityLabel,1,0);

			roadLabel = new Label(location.getRoad());
			gridPane.add(roadLabel,1,2);

			postalNoLabel = new Label(location.getPostalNo());
			gridPane.add(postalNoLabel,1,4);
			
			if(LoginService.getCurrentOperator().isAdmin()){
				confirmButton = new Button();
				confirmButton.setText("Edit");
				confirmButton.setOnAction(this::handleEdit);
				buttonBar.getButtons().add(confirmButton);
				
				deleteButton = new Button();
				deleteButton.setText("Delete");
				buttonBar.getButtons().add(deleteButton);
				deleteButton.setOnAction(this::handleDelete);
			}
		}
	}
	private void handleAdd(ActionEvent event){
		location.setCity(cityField.getText());
		location.setPostalNo(postalNoField.getText());
		location.setRoad(roadField.getText());
		try {
			LocationService.create(location);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while create location");
		}
	}
	private void handleClear(ActionEvent event){
		cityField.setText("");
		postalNoField.setText("");
		roadField.setText("");
	}

	private void handleEdit(ActionEvent event){
		cityField = new TextField(cityLabel.getText());
		gridPane.add(cityField, 1, 0);

		postalNoField = new TextField(postalNoLabel.getText());
		gridPane.add(postalNoField, 1, 2);
		
		roadField = new TextField(roadLabel.getText());
		gridPane.add(roadField, 1, 4);

		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOkUpdate);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleOkUpdate(ActionEvent event){
		location.setCity(cityField.getText());
		location.setPostalNo(postalNoField.getText());
		location.setRoad(roadField.getText());
		try {
			LocationService.update(location);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update location");
		}
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Operator");
		alert.setHeaderText("Are you sure to delete this location?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			LocationService.delete(location);
		}
		stage.close();	
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
