package com.carloan.presentation.controller.overview;

import com.carloan.business.model.*;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.control.TableControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class NewContractViewController extends OverviewController{
	@FXML
	private AnchorPane center;
	@FXML
	private AnchorPane buttonPane;
	@FXML
	private Label titleLabel;
	@FXML
	private Label comandLabel;
	@FXML
	private Button continueButton;
	@FXML
	private Button backButton;
	@FXML
	private Button cancelButton;

    private Contract contract;
	
	public NewContractViewController(){}

	@FXML
	public void initialize(){
        cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model) {
		contract = (Contract) model;
		begin();
	}
	private void begin(){
		contract.setOperator(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator());
		comandLabel.setText("Start opening a new contract!");
        center.getChildren().clear();
		backButton.setDisable(true);
		continueButton.setOnAction(e-> askForCustomer());
	}
	private void askForCustomer(){
		center.getChildren().clear();
		titleLabel.setText("Select Customer");
		comandLabel.setText("Select a customer or create a new one");
		
		TableView<Customer> customerTable = FXLoader.loadTable(TableControl.CUSTOMER);
        FXUtil.setAnchors(customerTable,0);
        center.getChildren().add(customerTable);

		backButton.setDisable(false);
		backButton.setOnAction(e -> begin());
		continueButton.setOnAction(e -> {
			if(customerTable.getSelectionModel().getSelectedItem() != null){
				contract.setCustomer(customerTable.getSelectionModel().getSelectedItem());
                contract.setStartAgency(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().getAgency());
                askForEndAgency();
			}
		});
	}
	private void askForEndAgency(){
        center.getChildren().clear();
        titleLabel.setText("Select Agency");
        comandLabel.setText("Select end agency");

        TableView<Agency> agencyTable = FXLoader.loadTable(TableControl.AGENCY);
        FXUtil.setAnchors(agencyTable,0);
        center.getChildren().add(agencyTable);

        continueButton.setOnAction(e -> {
			if(agencyTable.getSelectionModel().getSelectedItem() != null){
                contract.setStartAgency(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().getAgency());
				contract.setEndAgency(agencyTable.getSelectionModel().getSelectedItem());
				askForDates();
			}
		});
	}
	private void askForDates(){
		center.getChildren().clear();
		titleLabel.setText("Select Date");
		comandLabel.setText("Select start and end dates");
		
		GridPane grid = new GridPane();
		center.getChildren().add(grid);
	
		Label startDateLabel = new Label("Choose start date: ");
		Label startDate = new Label(DateFormatter.toString("dd-MM-yyyy", java.time.LocalDate
        .now()));
		grid.add(startDateLabel, 0, 0);
		grid.add(startDate, 1, 0);
		GridPane.setMargin(startDateLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(startDate, new Insets(5, 10, 5, 10));
		
		Label endDateLabel = new Label("Choose end date: ");
		DatePicker endDatePicker = FXFactory.createDatePicker();
		FXUtil.setStartDate(endDatePicker, java.time.LocalDate.now());
		endDatePicker.setValue(java.time.LocalDate.now().plusDays(1));
		grid.add(endDateLabel, 0, 1);
		grid.add(endDatePicker, 1, 1);
		GridPane.setMargin(endDateLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(endDatePicker, new Insets(5, 10, 5, 10));
		
		if(contract.getEndDate()!=null) {
            endDatePicker.setValue(contract.getEndDate());
        }
		backButton.setOnAction(e-> askForEndAgency() );
		continueButton.setOnAction(e-> {
			contract.setStartDate(java.time.LocalDate.now());
			contract.setEndDate(endDatePicker.getValue());
			askForCar();
		});
	}
	private void askForCar(){
		center.getChildren().clear();
		
		titleLabel.setText("Select Car");
		comandLabel.setText("Select a car and click continue");

		TableView<Car> carTable = FXLoader.loadTable(TableControl.CAR);
        FXUtil.setAnchors(carTable,0);
		center.getChildren().add(carTable);

		backButton.setOnAction(e-> askForDates());
		continueButton.setOnAction(e-> {
			if(carTable.getSelectionModel().getSelectedItem() != null && carTable.getSelectionModel().getSelectedItem().getStatus().get().equals("avaible")){
				contract.setCar(carTable.getSelectionModel().getSelectedItem());
				askForDetails();
			}
		});
	}
	private void askForDetails(){
		center.getChildren().clear();
		
		titleLabel.setText("Select Details");
		
		GridPane grid = new GridPane();
		FXUtil.setAnchors(grid, 0, 0, 0, 0);
		center.getChildren().add(grid);
		
		comandLabel.setText("Select type and limit");

		Label typeLabel = new Label("Choose a contract type: ");
		ModelMenuButton<ContractType> typeMenu = new ModelMenuButton<>();
		typeMenu.setPrefWidth(180);
		for(ContractType type : ServiceFactory.getModelFactory().<ContractType>getInstance(ServiceControl.CONTRACT_TYPE).getAll()) {
            FXUtil.addMenuItem(typeMenu, type.get(), type);
        }
		if(contract.getType() != null){
			typeMenu.setModel(contract.getType());
			typeMenu.setText(contract.getType().get());
		}
		grid.add(typeLabel, 0, 0);
		grid.add(typeMenu, 1, 0);
		GridPane.setMargin(typeLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(typeMenu, new Insets(5, 10, 5, 10));
		
		Label kmLimitLabel = new Label("Km limit");
		CheckBox kmBox = new CheckBox();
		grid.add(kmLimitLabel, 0, 1);
		grid.add(kmBox, 1, 1);
		GridPane.setMargin(kmLimitLabel, new Insets(5, 10, 5, 10));
		GridPane.setMargin(kmBox, new Insets(5, 10, 5, 10));

		backButton.setOnAction(e-> askForCar() );
		continueButton.setOnAction(e->{
			if(typeMenu.getModel()!=null){
				contract.setType(typeMenu.getModel());
				contract.setKmLimit(kmBox.isSelected());
				createContract();
			}
		});
	}
	private void createContract(){
		center.getChildren().clear();
		
		titleLabel.setText("Contract Review");
		comandLabel.setText("Check contract");
		
		Label detailsLabel = new Label("Your contract is almost open! Please check your contract");
		center.getChildren().add(detailsLabel);
		AnchorPane.setTopAnchor(detailsLabel, 0.0);
		AnchorPane.setLeftAnchor(detailsLabel, 0.0);
		
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);
		center.getChildren().add(grid);
		FXUtil.setAnchors(grid, 30, 0, 0, 0);
		
	    Hyperlink operator = new Hyperlink(contract.getOperator().endUserString());
		operator.setOnAction(e->FXLoader.loadOverview(contract.getOperator(),ModelControl.OPEN));
		grid.add(new Label("Operator:"), 0, 0);
		grid.add(operator, 1, 0);
		
		Hyperlink customer = new Hyperlink(contract.getCustomer().endUserString());
		customer.setOnAction(e->FXLoader.loadOverview(contract.getCustomer(),ModelControl.OPEN));
		grid.add(new Label("Customer:"), 0, 1);
		grid.add(customer, 1, 1);
		
		Hyperlink car = new Hyperlink(contract.getCar().endUserString());
		car.setOnAction(e->FXLoader.loadOverview(contract.getCar(),ModelControl.OPEN));
		grid.add(new Label("Car:"), 0, 2);
		grid.add(car, 1, 2);
		
		Label startDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getStartDate()));
		startDate.setPadding(new Insets(0, 0, 0, 5));
		grid.add(new Label("Start date:"), 0, 3);
		grid.add(startDate, 1, 3);

		Label endDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getEndDate()));
		endDate.setPadding(new Insets(0, 0, 0, 5));
		grid.add(new Label("End date:"), 0, 4);
		grid.add(endDate, 1, 4);
		
		Hyperlink startAgency = new Hyperlink(contract.getStartAgency().endUserString());
		startAgency.setOnAction(e->FXLoader.loadOverview(contract.getStartAgency(),ModelControl.OPEN));
		grid.add(new Label("Start agency:"), 0, 5);
		grid.add(startAgency, 1, 5);
		
		Hyperlink endAgency = new Hyperlink(contract.getEndAgency().endUserString());
		endAgency.setOnAction(e-> FXLoader.loadOverview(contract.getEndAgency(),ModelControl.OPEN));
		grid.add(new Label("End agency:"), 0, 6);
		grid.add(endAgency, 1, 6);

        String kmLimitStr = "No";
        if(contract.isKmLimit()) {
            kmLimitStr = "Yes";
        }
		Label kmLimit = new Label(kmLimitStr);
		kmLimit.setPadding(new Insets(0, 0, 0, 5));
		grid.add(new Label("Km limit:"), 0, 7);
		grid.add(kmLimit, 1, 7);
		
		Label type = new Label(contract.getType().get());
		type.setPadding(new Insets(0, 0, 0, 5));
		grid.add(new Label("Type:"), 0, 8);
		grid.add(type, 1, 8);
		
		backButton.setOnAction(e->askForDetails());
		continueButton.setText("Ok");
		continueButton.setOnAction(event->{
			try {
                ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT).create(contract);
			} catch (Exception e1) {
				FXAlert.warning(e1, "Error while create contract");
			}
			askForPayment();
		});
	}
	private void askForPayment(){
		center.getChildren().clear();
		
		titleLabel.setText("Down Payment");
		comandLabel.setText("Pay the amount to finish");
		
		Payment payment = new Payment();
		GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
		FXUtil.setAnchors(grid, 0);
		center.getChildren().add(grid);
		
		grid.add(new Label("This is the amount:"), 0, 0);
		
		Label downPaymentAmount = new Label("€ " + DoubleFormatter.toString(contract.getDownAmount()));
		grid.add(downPaymentAmount, 1, 0);
		
		Label typeLabel = new Label("Choose a payment type");
		typeLabel.setPadding(new Insets(5));
		grid.add(typeLabel, 0, 1);
		
		ModelMenuButton<PaymentType> typeMenu = new ModelMenuButton<>("Select a type");
        ServiceFactory.getModelFactory().<PaymentType>getInstance(ServiceControl.PAYMENT_TYPE).getAll().stream().forEach(type->FXUtil.addMenuItem(typeMenu, type.get(),type));
		grid.add(typeMenu, 1, 1);
		
		Button addPaymentButton = new Button("Add Payment");
		addPaymentButton.setPadding(new Insets(5));
		grid.add(addPaymentButton, 1, 2);
		addPaymentButton.setOnAction(e->{
			try {
				payment.setDate(java.time.LocalDate.now());
				payment.setTime(java.time.LocalTime.now());
				payment.setContract(contract);
				payment.setAmount(contract.getDownAmount());
				payment.setCurrency(ServiceFactory.getModelFactory().<Currency>getInstance(ServiceControl.CURRENCY).getAll().stream().filter(p->p.get().equals("euro")).findFirst().get());
				payment.setType(typeMenu.getModel());
                ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT).create(payment);
				FXAlert.information("Payment added", "Payment added successfully");
				continueButton.setDisable(false);
			} catch (Exception e1) {
				FXAlert.warning(e1, "Error while create payment");
			}
		});
		
		backButton.setDisable(true);
		continueButton.setDisable(true);
		continueButton.setText("Create");
		continueButton.setOnAction(event->{
			try {
				FXAlert.information("Contract Created", "Contract created successfully");
				changed = true;
				contract.getCar().setStatus(ServiceFactory.getModelFactory().<Status>getInstance(ServiceControl.CAR_STATUS).getAll().stream().filter(s->s.get().equals("hired")).findAny().get());
                ServiceFactory.getModelFactory().<Car>getInstance(ServiceControl.CAR).update(contract.getCar());
                FXLoader.initializeTables();
				stage.close();
			} catch (Exception e1) {
				FXAlert.warning(e1,"Internal error");
			}
		});
		cancelButton.setOnAction(e->{
            ServiceFactory.getModelFactory().getOrderedInstance(ServiceControl.PAYMENT).getAllBy(contract.getId()).stream().forEach(p-> ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT).delete(p));
            ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT).delete(contract);
			stage.close();
		});
	}
}
