package com.carloan.presentation.controller.overview;

import java.util.Optional;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.PriceCategory;
import com.carloan.business.model.Status;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.presentation.loader.overview.OverviewLoader;
import com.carloan.presentation.loader.overview.OverviewLoaderFactory;
import com.carloan.service.agency.AgencyService;
import com.carloan.service.car.CarService;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.login.LoginService;
import com.carloan.service.price_category.PriceCategoryService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class CarOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private ButtonBar buttonBar;
	@FXML
	private Button cancelButton;
	
	private Button confirmButton;
	private Button deleteButton;
	private Label modelLabel;
	private Label licensePlateLabel;
	private Label kmLabel;
	private Hyperlink agencyLink;
	private Hyperlink categoryLink;
	private Label statusLabel;
	private TextField modelField;
	private TextField licensePlateField;
	private TextField kmField;
	private MenuButton agencyMenu;
	private MenuButton categoryMenu;
	private MenuButton statusMenu;
	
	private Car car;
	
	@FXML
	public void initialize(){
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		car = (Car)model;
		
		if(car.getId() == 0){
			modelField = new TextField();
			gridPane.add(modelField,1,0);

			licensePlateField = new TextField();
			gridPane.add(licensePlateField,1,2);

			kmField = new TextField();
			gridPane.add(kmField,1,4);

			agencyMenu = new MenuButton("Select agency");
			gridPane.add(agencyMenu,1,6);
			for(Agency agency : AgencyService.getAll()){
				MenuItem item = new MenuItem();
				item.setText(agency.getName());
				item.setId(agency.getId().toString());
				agencyMenu.getItems().add(item);
				item.setOnAction(v -> {
							agencyMenu.setText(item.getText());
							agencyMenu.setId(item.getId());
						});
			}
			
			categoryMenu = new MenuButton("Select category");
			gridPane.add(categoryMenu,1,8);
			for(PriceCategory category : PriceCategoryService.getAll()){
				MenuItem item = new MenuItem();
				item.setText(Character.toString(category.getCategory()));
				item.setId(category.getId().toString());
				categoryMenu.getItems().add(item);
				item.setOnAction(v -> {
							categoryMenu.setText(item.getText());
							categoryMenu.setId(item.getId()); 
						});
			}
			
			statusMenu = new MenuButton("Select status");
			gridPane.add(statusMenu,1,10);
			for(Status status : CarService.getAllStatus()){
				MenuItem item = new MenuItem();
				item.setText(status.get());
				item.setId(status.getId().toString());
				statusMenu.getItems().add(item);
				item.setOnAction(v -> {
							statusMenu.setText(item.getText());
							statusMenu.setId(item.getId());
						});
			}
			
			confirmButton = new Button();
			confirmButton.setText("Add");
			confirmButton.setOnAction(this::handleAdd);
			buttonBar.getButtons().add(confirmButton);
			
			deleteButton = new Button();
			deleteButton.setText("Clear");
			deleteButton.setOnAction(this::handleClear);
			buttonBar.getButtons().add(deleteButton);
		} else {
			modelLabel = new Label(car.getModel());
			gridPane.add(modelLabel,1,0);
			
			licensePlateLabel = new Label(car.getLicensePlate());
			gridPane.add(licensePlateLabel,1,2);
			
			kmLabel = new Label(Integer.toString(car.getKm()));
			gridPane.add(kmLabel,1,4);
			
			agencyLink = new Hyperlink(car.getAgency().getName());
			agencyLink.setOnAction(this::handleAgency);
			gridPane.add(agencyLink,1,6);

			categoryLink = new Hyperlink(Character.toString(car.getCategory().getCategory()));
			categoryLink.setOnAction(this::handleCategory);
			gridPane.add(categoryLink,1,8);

			statusLabel = new Label(car.getStatus().get());
			gridPane.add(statusLabel,1,10);
			
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
		try {
			checkMenu();
			Agency agency = AgencyService.get(Integer.parseInt(agencyMenu.getId()));
			PriceCategory category = PriceCategoryService.get(Integer.parseInt(categoryMenu.getId()));
			Status status = CarService.getStatus(Integer.parseInt(statusMenu.getId()));
			
			car.setModel(modelField.getText());
			car.setLicensePlate(licensePlateField.getText());
			car.setAgency(agency);
			car.setCategory(category);
			car.setStatus(status);
			
			if(kmField.getText().equals(""))
				car.setKm(0);
			else
				car.setKm(Integer.parseInt(kmField.getText()));

			CarService.create(car);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while creating car");

		}
	}
	private void handleClear(ActionEvent event){
		kmField.setText("");
		licensePlateField.setText("");
		modelField.setText("");
	}
	private void handleEdit(ActionEvent event){
		modelField = new TextField(modelLabel.getText());
		gridPane.add(modelField , 1, 0);
		
		licensePlateField = new TextField(licensePlateLabel.getText());
		gridPane.add(licensePlateField, 1, 2);

		kmField = new TextField(kmLabel.getText());
		gridPane.add(kmField, 1, 4);
		
		agencyMenu = new MenuButton(agencyLink.getText());
		gridPane.add(agencyMenu,1,6);
		for(Agency agency : AgencyService.getAll()){
			MenuItem item = new MenuItem();
			item.setText(agency.getName());
			item.setId(agency.getId().toString());
			agencyMenu.getItems().add(item);
			item.setOnAction(v -> {
						agencyMenu.setText(item.getText());
						agencyMenu.setId(item.getId());
					});
		}
		
		categoryMenu = new MenuButton(categoryLink.getText());
		gridPane.add(categoryMenu,1,8);
		for(PriceCategory category : PriceCategoryService.getAll()){
			MenuItem item = new MenuItem();
			item.setText(Character.toString(category.getCategory()));
			item.setId(category.getId().toString());
			categoryMenu.getItems().add(item);
			item.setOnAction(v -> {
						categoryMenu.setText(item.getText());
						categoryMenu.setId(item.getId()); 
					});
		}
		
		statusMenu = new MenuButton(statusLabel.getText());
		gridPane.add(statusMenu,1,10);
		for(Status status : CarService.getAllStatus()){
			MenuItem item = new MenuItem();
			item.setText(status.get());
			item.setId(status.getId().toString());
			statusMenu.getItems().add(item);
			item.setOnAction(v -> {
						statusMenu.setText(item.getText());
						statusMenu.setId(item.getId());
					});
		}
		
		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOkUpdate);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void checkMenu() throws CommonServiceException{
		String error="";
		if(agencyMenu.getId()==null)
			error += "No agency selected\n";
		if(categoryMenu.getId()==null)
			error += "No category selected\n";
		if(statusMenu.getId()==null)
			error += "No status selected\n";
		
		if(!error.equals(""))
			throw new CommonServiceException(error);
		
	}
	private void handleOkUpdate(ActionEvent event){
		if(agencyMenu.getId()!=null){
			Agency agency = AgencyService.get(Integer.parseInt(agencyMenu.getId()));
			car.setAgency(agency);
		}
		if(categoryMenu.getId()!=null){
			PriceCategory category = PriceCategoryService.get(Integer.parseInt(categoryMenu.getId()));
			car.setCategory(category);
		}
		if(statusMenu.getId() != null){
			Status status = CarService.getStatus(Integer.parseInt(statusMenu.getId()));
			car.setStatus(status);
		}
		car.setModel(modelField.getText());
		car.setLicensePlate(licensePlateField.getText());
		
		try {
			CarService.update(car);
			stage.close();	
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update car");
		}
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Agency");
		alert.setHeaderText("Are you sure to delete this contract?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			CarService.delete(car);
		}
		stage.close();	
	}
	private void handleAgency(ActionEvent event){
		OverviewLoader loader = OverviewLoaderFactory.getInstance();
		loader.setModel(car.getAgency());
		loader.load();
	}
	private void handleCategory(ActionEvent event){
		OverviewLoader loader = OverviewLoaderFactory.getInstance();
		loader.setModel(car.getCategory());
		loader.load();
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
