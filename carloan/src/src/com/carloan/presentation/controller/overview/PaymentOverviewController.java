package com.carloan.presentation.controller.overview;

import java.time.LocalTime;

import com.carloan.business.model.Currency;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PaymentOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	private Payment payment;
	
	public PaymentOverviewController() {}
	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}
	@Override
	public void setModel(Object model){
		payment = (Payment) model;
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
		Label idLabel = new Label(Integer.toString(payment.getId()));
		Label amountLabel = new Label(Double.toString(payment.getAmount())+" "+payment.getCurrency().getSymbol());
		Label currencyLabel = new Label(payment.getCurrency().get());
		Label dateTimeLabel = new Label(DateFormatter.toString("dd-MM-yyyy HH:mm:ss", payment.getDate(), payment.getTime()));
		Label typeLabel = new Label(payment.getType().get());
		
		grid.add(idLabel, 1, 0);
		grid.add(amountLabel, 1,2);
		grid.add(currencyLabel, 1, 4);
		grid.add(dateTimeLabel, 1, 6);
		grid.add(typeLabel, 1, 8);
		
		operationButton.setVisible(false);
	}
	private void createSession(){
		Label idLabel = new Label("Not defined");
		TextField amountField = new TextField("0");
		DatePicker datePicker = FXFactory.createDatePicker();
		ModelMenuButton<PaymentType> typeMenu = new ModelMenuButton<>("Select a type");
		ModelMenuButton<Currency> currencyMenu = new ModelMenuButton<>("Select a currency");
		
		ServiceFactory.getModelFactory().<Currency>getInstance(ServiceControl.CURRENCY).getAll().stream().forEach(currency-> FXUtil.addMenuItem(currencyMenu,currency.get(),currency));
        ServiceFactory.getModelFactory().<PaymentType>getInstance(ServiceControl.PAYMENT_TYPE).getAll().stream().forEach(type->FXUtil.addMenuItem(typeMenu, type.get(),type));
		
		grid.add(idLabel,1,0);
		grid.add(amountField,1,2);
		grid.add(currencyMenu, 1, 4);
		grid.add(datePicker, 1, 6);
		grid.add(typeMenu, 1, 8);
		
		operationButton.setOnAction(e->{
			try {
				payment.setAmount(Double.parseDouble(amountField.getText()));
				payment.setDate(datePicker.getValue());
				payment.setTime(LocalTime.now());
				payment.setCurrency(currencyMenu.getModel());
				payment.setType(typeMenu.getModel());
                ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT).create(payment);
				FXAlert.information("Payment added", "Payment added successfully");
				changed = true;
				stage.close();
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while creating payment");
			}
		});
	}
	private void editSession(){
		Label idLabel = new Label("Not defined");
		TextField amountField = new TextField(DoubleFormatter.toString(payment.getAmount()));
		DatePicker datePicker = FXFactory.createDatePicker();
		ModelMenuButton<PaymentType> typeMenu = new ModelMenuButton<>(payment.getType().get(),payment.getType());
		ModelMenuButton<Currency> currencyMenu = new ModelMenuButton<>(payment.getCurrency().get(), payment.getCurrency());
		
		datePicker.setValue(payment.getDate());
        ServiceFactory.getModelFactory().<Currency>getInstance(ServiceControl.CURRENCY).getAll().stream().forEach(currency-> FXUtil.addMenuItem(currencyMenu,currency.get(),currency));
        ServiceFactory.getModelFactory().<PaymentType>getInstance(ServiceControl.PAYMENT_TYPE).getAll().stream().forEach(type->FXUtil.addMenuItem(typeMenu, type.get(),type));

        grid.add(idLabel,1,0);
		grid.add(amountField,1,2);
		grid.add(currencyMenu, 1, 4);
		grid.add(datePicker, 1, 6);
		grid.add(typeMenu, 1, 8);
		
		operationButton.setOnAction(e->{
			try {
				payment.setAmount(Double.parseDouble(amountField.getText()));
				payment.setDate(datePicker.getValue());
				payment.setTime(LocalTime.now());
				payment.setCurrency(currencyMenu.getModel());
				payment.setType(typeMenu.getModel());
				ButtonType result = FXAlert.confirmAction("Update", "Payment");
				if (result == ButtonType.OK){
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT).update(payment);
					changed=true;
					stage.close();
				}
			} catch (CommonServiceException ex) {
				FXAlert.warning(ex, "Error while creating payment");
			}
		});
	}
	private void deleteSession(){
		openSession();

		operationButton.setVisible(true);
		operationButton.setOnAction(e->{
			ButtonType result = FXAlert.confirmAction("Delete", "Payment");
            changed = result == ButtonType.OK;
            if (changed){
                ServiceFactory.getModelFactory().getInstance(ServiceControl.PAYMENT).delete(payment);
				stage.close();
			}
		});
	}
}
