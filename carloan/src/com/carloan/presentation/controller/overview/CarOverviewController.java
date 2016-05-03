package com.carloan.presentation.controller.overview;


import com.carloan.business.model.*;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;
import com.carloan.service.model_service.ModelService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CarOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	private Car car;
	
	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model) {
		car = (Car)model;
		operationButton.setText(operation);

		switch(operation){
		case ModelControl.OPEN:
			openSession();
			break;
		case ModelControl.NEW:
			addSession();
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
		Hyperlink agency = new Hyperlink(car.getAgency().getName());
		Hyperlink category = new Hyperlink(car.getCategory().get());
		Label status = new Label(car.getStatus().get());
		
		agency.setOnAction(e-> FXLoader.loadOverview(car.getAgency(),ModelControl.OPEN));
		category.setOnAction(e-> FXLoader.loadOverview(car.getCategory(),ModelControl.OPEN));

		grid.add(new Label(car.getModel()),1,0);
		grid.add(new Label(car.getLicensePlate()),1,2);
		grid.add(new Label(Integer.toString(car.getKm())),1,4);
		grid.add(agency,1,6);
		grid.add(category,1,8);
		grid.add(status,1,10);
		
		operationButton.setVisible(false);
	}
	private void addSession(){
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);
        ModelService<Car> carService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR);
        ModelService<Status> carStatusService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR_STATUS);
        ModelService<PriceCategory> categoryService = ServiceFactory.getModelFactory().getInstance(ServiceControl.PRICE_CATEGORY);

        TextField modelField = new TextField();
		TextField licensePlateField = FXFactory.createTextField("([a-z])");
		TextField kmField = FXFactory.createTextField("([A-z])");
		ModelMenuButton<Agency> agencyMenu = new ModelMenuButton<>("Select agency");
		ModelMenuButton<PriceCategory> categoryMenu = new ModelMenuButton<>("Select category");
		ModelMenuButton<Status> statusMenu = new ModelMenuButton<>("Select status");

		agencyService.getAll().stream().forEach(agency->FXUtil.addMenuItem(agencyMenu,agency.getName(),agency));
		categoryService.getAll().stream().forEach(category->FXUtil.addMenuItem(categoryMenu,category.get(),category));
		carStatusService.getAll().stream().forEach(status->FXUtil.addMenuItem(statusMenu,status.get(),status));
		
		grid.add(modelField,1,0);
		grid.add(licensePlateField,1,2);
		grid.add(kmField,1,4);
		grid.add(agencyMenu,1,6);
		grid.add(categoryMenu,1,8);
		grid.add(statusMenu,1,10);
		
		operationButton.setOnAction(e->{
			try {
				car.setModel(modelField.getText());
				car.setLicensePlate(licensePlateField.getText());
				car.setAgency(agencyMenu.getModel());
				car.setCategory(categoryMenu.getModel());
				car.setStatus(statusMenu.getModel());
				car.setKm(Integer.parseInt(kmField.getText()));

				carService.create(car);
				FXAlert.information("Car created", "Car created successfully");
				changed = true;
				stage.close();
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while creating car");
			}
		});
	}
	private void editSession(){
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);
        ModelService<Car> carService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR);
        ModelService<Status> carStatusService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR_STATUS);
        ModelService<PriceCategory> categoryService = ServiceFactory.getModelFactory().getInstance(ServiceControl.PRICE_CATEGORY);

		TextField modelField = new TextField(car.getModel());
		TextField licensePlateField = FXFactory.createTextField("([a-z])");
		TextField kmField = FXFactory.createTextField("([A-z])");
		ModelMenuButton<Agency> agencyMenu = new ModelMenuButton<>(car.getAgency().getName(),car.getAgency());
		ModelMenuButton<PriceCategory> categoryMenu = new ModelMenuButton<>(car.getCategory().get(),car.getCategory());
		ModelMenuButton<Status> statusMenu = new ModelMenuButton<>(car.getStatus().get(),car.getStatus());
		
		licensePlateField.setText(car.getLicensePlate());
		kmField.setText(Integer.toString(car.getKm()));

		agencyService.getAll().stream().forEach(agency-> FXUtil.addMenuItem(agencyMenu,agency.getName(),agency));
		categoryService.getAll().stream().forEach(category->FXUtil.addMenuItem(categoryMenu,category.get(),category));
		carStatusService.getAll().stream().forEach(status->FXUtil.addMenuItem(statusMenu,status.get(),status));
		
		agencyMenu.setPrefWidth(200);
		categoryMenu.setPrefWidth(200);
		statusMenu.setPrefWidth(200);
		
		grid.add(modelField , 1, 0);
		grid.add(licensePlateField, 1, 2);
		grid.add(kmField, 1, 4);
		grid.add(agencyMenu,1,6);
		grid.add(categoryMenu,1,8);
		grid.add(statusMenu,1,10);

		operationButton.setOnAction(e->{
			car.setAgency(agencyMenu.getModel());
			car.setCategory(categoryMenu.getModel());
			car.setStatus(statusMenu.getModel());
			car.setModel(modelField.getText());
			car.setLicensePlate(licensePlateField.getText());
			try {
				ButtonType result = FXAlert.confirmAction("Update", "Car");
				if (result == ButtonType.OK){
					carService.update(car);
					changed=true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while update car");
			}
		});
	}
	private void deleteSession(){
        ModelService<Car> carService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR);

        openSession();
		
		operationButton.setVisible(true);
		operationButton.setOnAction(e->{
			ButtonType result = FXAlert.confirmAction("Delete", "Car");
            if (result == ButtonType.OK){
                if(ServiceFactory.getModelFactory().<Contract>getInstance(ServiceControl.CONTRACT).getAll().stream().anyMatch(c -> c.getCar().equals(car))){
                    FXAlert.warning("Can't delete car", "It is contained in a contract");
                } else {
                    carService.delete(car);
                    changed = true;
                }
				stage.close();
			}
		});
	}
}
