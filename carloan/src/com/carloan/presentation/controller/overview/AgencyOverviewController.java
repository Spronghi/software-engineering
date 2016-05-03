package com.carloan.presentation.controller.overview;

import com.carloan.business.model.*;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;

import com.carloan.service.model_service.ModelService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;

public class AgencyOverviewController extends OverviewController{
	@FXML
	private AnchorPane center;
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	private Agency agency;
	private Location location;
	@FXML
	public void initialize(){
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		agency = (Agency) model;
		location = agency.getLocation();
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
        List<Car> cars = ServiceFactory.getModelFactory().<Car>getOrderedInstance(ServiceControl.CAR).getAllBy(agency.getId());
		Hyperlink carsLink = new Hyperlink(cars.size()+" cars");
        carsLink.setOnAction(e->{
            TableView<Car> carTable = FXLoader.loadTable("Car");
            carTable.setItems(FXCollections.observableArrayList(cars));
            FXUtil.setAnchors(carTable, 70, 50, 20, 20);

            operationButton.setText("Back");
            operationButton.setVisible(true);
            operationButton.setOnAction(event->{
                center.getChildren().remove(carTable);
                operationButton.setVisible(false);
            });

            center.getChildren().add(carTable);
        });
		
		grid.add(new Label(Integer.toString(agency.getId())), 1, 0);
		grid.add(new Label(agency.getName()), 1, 2);
		grid.add(new Label(location.getCity()), 1, 4);
		grid.add(new Label(location.getPostalNo()), 1, 6);
		grid.add(new Label(location.getRoad()), 1, 8);
        FXUtil.addGridRow(grid, new Label("Cars"), carsLink, true);
		
		operationButton.setVisible(false);
	}
	private void createSession(){
        ModelService<Location> locationService = ServiceFactory.getModelFactory().getInstance(ServiceControl.LOCATION);
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);
        TextField nameField = new TextField();
		TextField cityField = new TextField();
		TextField postalNoField = FXFactory.createTextField("[A-z]");
		TextField roadField = new TextField();

		grid.add(new Label("Not defined"), 1, 0);
		grid.add(nameField , 1, 2);
		grid.add(cityField, 1, 4);
		grid.add(postalNoField, 1, 6);
		grid.add(roadField, 1, 8);
		
		operationButton.setOnAction(e->{
			location = new Location();
			location.setCity(cityField.getText());
			location.setPostalNo(postalNoField.getText());
			location.setRoad(roadField.getText());
			agency.setName(nameField.getText());
			agency.setLocation(location);
			try {
                locationService.create(location);
				agencyService.create(agency);
				FXAlert.information("Agency created", "Agency created successfully");
				changed = true;
				stage.close();
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while create agency");
			}
		});
	}
	private void editSession(){
        ModelService<Location> locationService = ServiceFactory.getModelFactory().getInstance(ServiceControl.LOCATION);
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);

        TextField nameField = new TextField(agency.getName());
		TextField cityField = new TextField(agency.getLocation().getCity());
		TextField postalNoField = FXFactory.createTextField(agency.getLocation().getPostalNo(),"[A-z]");
		TextField roadField = new TextField(agency.getLocation().getRoad());

		grid.add(nameField , 1, 2);
		grid.add(cityField, 1, 4);
		grid.add(postalNoField, 1, 6);
		grid.add(roadField, 1, 8);
		
		operationButton.setOnAction(e->{
			location.setCity(cityField.getText());
			location.setPostalNo(postalNoField.getText());
			location.setRoad(roadField.getText());
			agency.setName(nameField.getText());
			try {
				ButtonType result = FXAlert.confirmAction("Update", "Agency");
				if (result == ButtonType.OK){
					locationService.update(location);
					agencyService.update(agency);
					changed = true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while update agency");
			}
		});
	}
	private void deleteSession(){
        ModelService<Location> locationService = ServiceFactory.getModelFactory().getInstance(ServiceControl.LOCATION);
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);

        openSession();

		operationButton.setVisible(true);
		operationButton.setOnAction(e->{
			ButtonType result = FXAlert.confirmAction("Delete", "Agency");
            if (result == ButtonType.OK){
                if (ServiceFactory.getModelFactory().<Car>getInstance(ServiceControl.CAR).getAll().stream().anyMatch(c->c.getAgency().equals(agency))){
                    FXAlert.warning("Can't delete agency", "It has cars inside");
                } else if(ServiceFactory.getModelFactory().<Operator>getInstance(ServiceControl.OPERATOR).getAll().stream().anyMatch(p->p.getAgency().equals(agency))) {
                    FXAlert.warning("Can't delete agency", "It has operators inside");
                } else {
                    agencyService.delete(agency);
                    locationService.delete(location);
                    changed = true;
                }
				stage.close();
			}
		});
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
