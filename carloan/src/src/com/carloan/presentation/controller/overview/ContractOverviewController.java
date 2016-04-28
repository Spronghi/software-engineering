package com.carloan.presentation.controller.overview;

import java.time.LocalDate;
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
import com.carloan.service.model_service.OrderedService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.control.ModelControl;
import com.carloan.exception.CommonServiceException;

public class ContractOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Label openLabel;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	@FXML
	private Button closeButton;
	private Contract contract;
	
	public ContractOverviewController() {}
	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model) {
		contract = (Contract) model;
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
		Hyperlink operatorLink = new Hyperlink(contract.getOperator().endUserString());
		Hyperlink carLink = new Hyperlink(contract.getCar().getModel());
		Hyperlink startAgencyLink = new Hyperlink(contract.getStartAgency().getName());
		Hyperlink endAgencyLink = new Hyperlink(contract.getEndAgency().getName());
		Hyperlink customerLink = new Hyperlink(contract.getCustomer().endUserString());
		Label paidLabel = new Label("€" + DoubleFormatter.toString(contract.getAmountPaid()));
		Label toPayLabel = new Label("€" + DoubleFormatter.toString(contract.getAmount()-contract.getAmountPaid()));

		List<Payment> paymentList = ServiceFactory.getModelFactory().<Payment>getOrderedInstance(ServiceControl.PAYMENT).getAllBy(contract.getId());

        String paymentStr;
        if(paymentList.size()==0) {
            paymentStr = "No payments";
        } else {
            paymentStr = Integer.toString(paymentList.size()) + " payments";
        }
		Hyperlink paymentLink = new Hyperlink(paymentStr);
		paymentLink.setOnAction(e -> {
			List<Payment> contractPayments = ServiceFactory.getModelFactory().<Payment>getOrderedInstance(ServiceControl.PAYMENT).getAllBy(contract.getId());
			if(contractPayments.size()>0) {
                FXLoader.loadOverview(contractPayments, ModelControl.OPEN);
            }
		});

        if(contract.isOpen()) {
            openLabel.setText("open");
        } else {
            openLabel.setText("closed");
        }

		operatorLink.setOnAction(e->FXLoader.loadOverview(contract.getOperator(),ModelControl.OPEN));
		carLink.setOnAction(e->FXLoader.loadOverview(contract.getCar(),ModelControl.OPEN));
		startAgencyLink.setOnAction(e->FXLoader.loadOverview(contract.getStartAgency(),ModelControl.OPEN));
		endAgencyLink.setOnAction(e->FXLoader.loadOverview(contract.getEndAgency(),ModelControl.OPEN));
		customerLink.setOnAction(e->FXLoader.loadOverview(contract.getCustomer(),ModelControl.OPEN));
		
		grid.add(new Label(Integer.toString(contract.getId())), 1, 0);
		grid.add(new Label(DateFormatter.toString("dd-MM-yyyy",contract.getStartDate())), 1, 2);
		grid.add(new Label(DateFormatter.toString("dd-MM-yyyy",contract.getEndDate())), 1, 4);
		grid.add(operatorLink, 1, 6);
        grid.add(carLink, 1, 8);
        grid.add(new Label(Integer.toString(contract.getKm())), 1, 10);

        if(contract.isKmLimit()) {
            grid.add(new Label("yes"), 7, 0);
        } else {
            grid.add(new Label("no"), 7, 0);
        }
		grid.add(startAgencyLink, 7, 2);
		grid.add(endAgencyLink, 7, 4);
		grid.add(customerLink, 7, 6);
        grid.add(new Label(contract.getType().get()), 7, 8);
        grid.add(paymentLink, 7, 10);
		grid.add(new Label("€" + DoubleFormatter.toString(contract.getAmount())), 7, 12);
		grid.add(paidLabel, 7, 13);
		grid.add(toPayLabel, 7, 14);
		
		operationButton.setText("Add Payment");
		operationButton.setDisable(contract.getAmountPaid()==contract.getAmount());
		operationButton.setOnAction(e->{
            Payment payment = new Payment();
            payment.setContract(contract);
			if(FXLoader.loadOverview(payment, ModelControl.NEW)){
                paymentList.add(payment);
				paymentLink.setText(Integer.toString(paymentList.size()) +" payments");
				paidLabel.setText("€" + DoubleFormatter.toString(contract.getAmountPaid()));
				toPayLabel.setText("€" + DoubleFormatter.toString(contract.getAmount()-contract.getAmountPaid()));
				operationButton.setDisable(contract.getAmountPaid()==contract.getAmount());
			}
		});
		
		closeButton.setVisible(contract.isOpen());
		closeButton.setOnAction(e->{
            FXLoader.loadOverview(contract, ModelControl.CLOSE);
			if(!contract.isOpen()){
				openLabel.setText("closed");
				paidLabel.setText("€" + DoubleFormatter.toString(contract.getAmountPaid()));
				toPayLabel.setText("€" + DoubleFormatter.toString(contract.getAmount()-contract.getAmountPaid()));
				closeButton.setVisible(false);
			}
		});
	}
	private void createSession(){}
	private void editSession(){
        ModelService<Contract> contractService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT);
        ModelService<ContractType> contractTypeService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT_TYPE);
        ModelService<Operator> operatorService = ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR);
        ModelService<Customer> customerService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER);
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);
        OrderedService<Car> carService  = ServiceFactory.getModelFactory().<Car>getOrderedInstance(ServiceControl.CAR);
        OrderedService<Payment> paymentService = ServiceFactory.getModelFactory().getOrderedInstance(ServiceControl.PAYMENT);

        DatePicker startDatePicker = FXFactory.createDatePicker();
		DatePicker endDatePicker = FXFactory.createDatePicker();
		TextField kmField = FXFactory.createTextField(Integer.toString(contract.getKm()),"[A-z]");
		CheckBox kmLimitBox = new CheckBox();
		ModelMenuButton<Operator> operatorMenu = new ModelMenuButton<>(contract.getOperator().getUsername(),contract.getOperator());
		ModelMenuButton<ContractType> typeMenu = new ModelMenuButton<>(contract.getType().get(),contract.getType());
		ModelMenuButton<Agency> startAgencyMenu = new ModelMenuButton<>(contract.getStartAgency().getName(),contract.getStartAgency());
		ModelMenuButton<Agency> endAgencyMenu = new ModelMenuButton<>(contract.getEndAgency().getName(),contract.getEndAgency());
		ModelMenuButton<Car> carMenu = new ModelMenuButton<>(contract.getCar().getModel(),contract.getCar());
		ModelMenuButton<Customer> customerMenu = new ModelMenuButton<>(contract.getCustomer().endUserString(),contract.getCustomer());
		Label paidLabel = new Label("€" + DoubleFormatter.toString(contract.getAmountPaid()));
		Label toPayLabel = new Label("€" + DoubleFormatter.toString(contract.getAmount()-contract.getAmountPaid()));
		
		FXUtil.setStartDate(startDatePicker, LocalDate.now());
		FXUtil.setStartDate(endDatePicker, startDatePicker);
		kmLimitBox.setSelected(contract.isKmLimit());

		typeMenu.setPrefWidth(200);
		startAgencyMenu.setPrefWidth(200);
		endAgencyMenu.setPrefWidth(200);
		carMenu.setPrefWidth(200);
		operatorMenu.setPrefWidth(200);
		customerMenu.setPrefWidth(200);

		endDatePicker.setValue(contract.getEndDate());
		startDatePicker.setValue(contract.getStartDate());
		startDatePicker.setOnAction(e->endDatePicker.setValue(startDatePicker.getValue().plusDays(1)));

		contractTypeService.getAll().stream().forEach(type->FXUtil.addMenuItem(typeMenu,type.get(), type));
		carService.getAllBy(startAgencyMenu.getModel().getId()).stream().forEach(car->FXUtil.addMenuItem(carMenu, car.getModel(), car));
		operatorService.getAll().stream().filter(operator->!operator.isAdmin()).forEach(operator->FXUtil.addMenuItem(operatorMenu,operator.getUsername(), operator));
		customerService.getAll().stream().forEach(customer->FXUtil.addMenuItem(customerMenu,customer.endUserString(), customer));
		agencyService.getAll().stream().forEach(agency->{
            FXUtil.addMenuItem(startAgencyMenu,agency.getName(),agency);
            FXUtil.addMenuItem(endAgencyMenu,agency.getName(),agency);
		});

		List<Payment> paymentList = paymentService.getAllBy(contract.getId());
		String paymentStr;
		if(paymentList.size()==0) {
            paymentStr = "No payments";
        } else {
            paymentStr = Integer.toString(paymentList.size()) + " payments";
        }
        Hyperlink paymentLink = new Hyperlink(paymentStr);
		paymentLink.setOnAction(e -> {
			List<Payment> contractPayments = paymentService.getAllBy(contract.getId());
			if(contractPayments.size()>0) {
                FXLoader.loadOverview(contractPayments, ModelControl.OPEN);
            }
		});
		
		grid.add(new Label(Integer.toString(contract.getId())), 1, 0);
		grid.add(startDatePicker, 1, 2);
		grid.add(endDatePicker, 1, 4);
		grid.add(operatorMenu, 1, 6);
        grid.add(carMenu, 1, 8);
        grid.add(kmField, 1, 10);

        grid.add(kmLimitBox, 7, 0);
		grid.add(startAgencyMenu, 7, 2);
		grid.add(endAgencyMenu, 7, 4);
		grid.add(customerMenu, 7, 6);
        grid.add(typeMenu, 7, 8);
		grid.add(paymentLink, 7, 10);
		grid.add(new Label("€" + DoubleFormatter.toString(contract.getAmount())), 7, 12);
		grid.add(paidLabel, 7, 13);
		grid.add(toPayLabel, 7, 14);

        operationButton.setDisable(false);
		operationButton.setOnAction(e->{
			try {
				contract.setStartDate(startDatePicker.getValue());
				contract.setEndDate(endDatePicker.getValue());
				contract.setKm(Integer.parseInt(kmField.getText()));
				contract.setKmLimit(kmLimitBox.isSelected());
				contract.setType(typeMenu.getModel());
				contract.setStartAgency(startAgencyMenu.getModel());
				contract.setEndAgency(endAgencyMenu.getModel());
				contract.setOperator(operatorMenu.getModel());
				contract.setCustomer(customerMenu.getModel());
				contract.setCar(carMenu.getModel());
				ButtonType result = FXAlert.confirmAction("Update","Contract");
				if (result == ButtonType.OK){
                    contractService.update(contract);
					changed = true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while updating contract");
			}
		});
	}
	private void deleteSession(){
        ModelService<Contract> contractService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CONTRACT);
        ModelService<Status> statusService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR_STATUS);
        OrderedService<Car> carService  = ServiceFactory.getModelFactory().getOrderedInstance(ServiceControl.CAR);
        OrderedService<Payment> paymentService = ServiceFactory.getModelFactory().getOrderedInstance(ServiceControl.PAYMENT);

        openSession();
		
		closeButton.setVisible(false);
		operationButton.setVisible(true);
        operationButton.setDisable(false);
		operationButton.setText(operation);
		operationButton.setOnAction(e->{
			try {
				ButtonType result = FXAlert.confirmAction("Delete", "Contract");
                if (result == ButtonType.OK){
                    if(contract.isOpen()) {
                        contract.getCar().setStatus(statusService.getAll().stream().filter(p -> p.get().equals("avaible")).findFirst().get());
                        carService.update(contract.getCar());
                    }
                    paymentService.getAllBy(contract.getId()).stream().forEach(p->paymentService.delete(p));
                    contractService.delete(contract);
                    changed = true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while deleting contract");
			}
        });
	}
}
