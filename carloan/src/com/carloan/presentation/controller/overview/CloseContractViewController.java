package com.carloan.presentation.controller.overview;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.carloan.business.model.*;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXLoader;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.service.model_service.ModelService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.control.ModelControl;
import com.carloan.exception.CommonServiceException;

public class CloseContractViewController extends OverviewController{
	@FXML
	private AnchorPane center;
	@FXML
	private Label titleLabel;
	@FXML
	private Label comandLabel;
	@FXML
	private ButtonBar buttonBar;
	private Button backButton;
	private Button continueButton;
	private Button cancelButton;
	private Contract contract;
	private int traveledKm;
	private int currentKm;
	
	@FXML
	public void initialize() {
        backButton = new Button("Back");
        buttonBar.getButtons().add(backButton);

        continueButton = new Button("Continue");
        buttonBar.getButtons().add(continueButton);

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());
        buttonBar.getButtons().add(cancelButton);
    }
	@Override
	public void setModel(Object model) {
        contract = (Contract) model;
        if (contract.isOpen()) {
            begin();
        } else if(!contract.getEndAgency().equals(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().getAgency())) {
            wrongAgency();
        } else {
            isClosed();
        }

	}
    private void isClosed(){
        titleLabel.setText("Close Contract");
        comandLabel.setText("Contract is already closed");
        backButton.setDisable(true);
        continueButton.setDisable(true);
    }
    private void wrongAgency(){
        center.getChildren().clear();
        titleLabel.setText("Wrong Agency");
        comandLabel.setText("Wrong agency");

        Label notation = new Label("You are not in "+ contract.getEndAgency().getName());
        FXUtil.setTopLeftAnchors(notation, 0,0);
        center.getChildren().add(notation);

        backButton.setDisable(true);
        continueButton.setDisable(true);
    }
	private void begin(){
		titleLabel.setText("Close Contract");
		comandLabel.setText("Start closing contract!");
        center.getChildren().clear();
		backButton.setDisable(true);
		continueButton.setOnAction(e-> insertKm());
	}
	private void insertKm(){
		center.getChildren().clear();

		titleLabel.setText("Insert Km");
		comandLabel.setText("Insert current km of "+contract.getCar().getModel());

		GridPane grid = new GridPane();
		center.getChildren().add(grid);
		FXUtil.setAnchors(grid, 0);
		
		TextField kmField = FXFactory.createTextField("[A-z]");
		kmField.setText(Integer.toString(contract.getCar().getKm()));
		kmField.setPadding(new Insets(5));
		grid.add(new Label("Insert km: "), 0, 0);
		grid.add(kmField, 1, 0);
        
		backButton.setDisable(false);
		backButton.setOnAction(e->begin());
		continueButton.setOnAction(e->{
	    	if(!kmField.getText().equals("")){
	    		currentKm = Integer.parseInt(kmField.getText());
	    		traveledKm = currentKm - contract.getCar().getKm();
                check();
	    	}
    	});
	}

	private void check(){
		center.getChildren().clear();
		
		titleLabel.setText("Contract Review");
		comandLabel.setText("Check contract");
		
		GridPane grid = new GridPane();
		center.getChildren().add(grid);
		FXUtil.setAnchors(grid, 0);
		
	    Hyperlink operator = new Hyperlink(contract.getOperator().endUserString());
		operator.setOnAction(e-> FXLoader.loadOverview(contract.getOperator(),ModelControl.OPEN));
		grid.add(new Label("Operator:"), 0, 0);
		grid.add(operator, 1, 0);
		
		Hyperlink customer = new Hyperlink(contract.getCustomer().endUserString());
		customer.setOnAction(e->FXLoader.loadOverview(contract.getCustomer(),ModelControl.OPEN));
		grid.add(new Label("Customer:"), 0, 1);
		grid.add(customer, 1, 1);
		
		Hyperlink car = new Hyperlink(contract.getCar().endUserString());
		car.setOnAction(e->FXLoader.loadOverview(contract.getCar(),ModelControl.OPEN) );
		grid.add(new Label("Car:"), 0, 2);
		grid.add(car, 1, 2);
		
		Label startDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getStartDate()));
		startDate.setPadding(new Insets(5));
		grid.add(new Label("Start date:"), 0, 3);
		grid.add(startDate, 1, 3);

		Label endDate = new Label(DateFormatter.toString("dd-MM-yyyy",contract.getEndDate()));
		endDate.setPadding(new Insets(5));
		grid.add(new Label("End date:"), 0, 4);
		grid.add(endDate, 1, 4);
		
		Hyperlink startAgency = new Hyperlink(contract.getStartAgency().endUserString());
		startAgency.setOnAction(e-> FXLoader.loadOverview(contract.getStartAgency(),ModelControl.OPEN));
		grid.add(new Label("Start agency:"), 0, 5);
		grid.add(startAgency, 1, 5);
		
		Hyperlink endAgency = new Hyperlink(contract.getEndAgency().endUserString());
		endAgency.setOnAction(e-> FXLoader.loadOverview(contract.getEndAgency(),ModelControl.OPEN) );
		grid.add(new Label("End agency:"), 0, 6);
		grid.add(endAgency, 1, 6);
		
		Label km = new Label(Integer.toString(currentKm));
		km.setPadding(new Insets(5));
		grid.add(new Label("Km:"), 0, 7);
		grid.add(km, 1, 7);
		
		Label traveledKmLabel = new Label(Integer.toString(traveledKm));
		traveledKmLabel.setPadding(new Insets(5));
		grid.add(new Label("Traveled km:"), 0, 8);
		grid.add(traveledKmLabel, 1, 8);

        String kmLimitStr = "No";
        if(contract.isKmLimit()) {
            kmLimitStr = "Yes";
        }

		Label kmLimit = new Label(kmLimitStr);
		kmLimit.setPadding(new Insets(5));
		grid.add(new Label("Km limit:"), 0, 9);
		grid.add(kmLimit, 1, 9);
		
		Label type = new Label(contract.getType().get());
		type.setPadding(new Insets(5));
		grid.add(new Label("Type:"), 0, 10);
		grid.add(type, 1, 10);
		
		backButton.setText("Back");
		backButton.setOnAction(e->insertKm());

        continueButton.setDisable(false);
		continueButton.setText("Continue");
		continueButton.setOnAction(e->pay());
	}
	private void pay(){
        ModelService<Payment> paymentService = ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT);
        ModelService<Contract> contractService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT);
        ModelService<Car> carService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR);

        List<Status> carStatuses = ServiceFactory.getModelFactory().<Status>getInstance(ServiceControl.CAR_STATUS).getAll();
        List<Currency> currencies = ServiceFactory.getModelFactory().<Currency>getInstance(ServiceControl.CURRENCY).getAll();
        List<PaymentType> paymentTypes = ServiceFactory.getModelFactory().<PaymentType>getInstance(ServiceControl.PAYMENT_TYPE).getAll();

        center.getChildren().clear();
		
		titleLabel.setText("Close Payment");
		comandLabel.setText("Pay the amount to close the contract");
		
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);
		FXUtil.setAnchors(grid, 0);
		center.getChildren().add(grid);

		grid.add(new Label("The amount is:"), 0, 0);
		Label paymentLabel = new Label("€ " + DoubleFormatter.toString(Contract.getAmountByKm(contract,traveledKm)-contract.getAmountPaid()));
		grid.add(paymentLabel,1,0);
		
		Label typeLabel = new Label("Choose a payment type");
		typeLabel.setPadding(new Insets(5));
		grid.add(typeLabel, 0, 1);
		
		ModelMenuButton<PaymentType> typeMenu = new ModelMenuButton<>("Select a type");
        paymentTypes.stream().forEach(type->FXUtil.addMenuItem(typeMenu, type.get(),type));
		grid.add(typeMenu, 1, 1);
		
		Button addPaymentButton = new Button("Add Payment");
		grid.add(addPaymentButton, 1, 2);
		
		Payment payment = new Payment();
		payment.setDate(LocalDate.now());
		payment.setContract(contract);
		payment.setAmount(Contract.getAmountByKm(contract,traveledKm)-contract.getAmountPaid());
		payment.setCurrency(currencies.stream().filter(p->p.get().equals("euro")).findFirst().get());
		
		addPaymentButton.setOnAction(e->{
			try {
				payment.setDate(LocalDate.now());
				payment.setTime(LocalTime.now());
				payment.setContract(contract);
				payment.setAmount(Contract.getAmountByKm(contract,traveledKm)-contract.getAmountPaid());
				payment.setCurrency(currencies.stream().filter(p->p.get().equals("euro")).findFirst().get());
				payment.setType(typeMenu.getModel());
                paymentService.create(payment);
				FXAlert.information("Payment added", "Payment added successfully");
				paymentLabel.setText("€ " + DoubleFormatter.toString(Contract.getAmountByKm(contract,traveledKm)-contract.getAmountPaid()));
				continueButton.setDisable(!(Contract.getAmountByKm(contract,traveledKm)==contract.getAmountPaid()));
			} catch (Exception e1) {
				FXAlert.warning(e1, "Error while create payment");
			}
		});
		
		backButton.setOnAction(e->check());
		continueButton.setDisable(!(Contract.getAmountByKm(contract,traveledKm)==contract.getAmountPaid()));
		continueButton.setText("Finish");
		continueButton.setOnAction(event->{
			try {
				if(payment.getId()!=0 || payment.getAmount() == 0){
					contract.setKm(traveledKm);
					contract.setOpen(false);
					contractService.update(contract);

                    contract.getCar().setKm(currentKm);
					contract.getCar().setAgency(contract.getEndAgency());
					contract.getCar().setStatus(carStatuses.stream().filter(s->s.get().equals("avaible")).findAny().get());
					carService.update(contract.getCar());
					
					changed=true;
					FXAlert.information("Contract Closed", "Contract closed successfully");
                    FXLoader.initializeTables();
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while closing contract");
			}
		});
	}
}
