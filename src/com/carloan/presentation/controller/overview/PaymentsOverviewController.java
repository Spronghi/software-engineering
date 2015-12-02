package com.carloan.presentation.controller.overview;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import com.carloan.business.model.Payment;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;

public class PaymentsOverviewController extends OverviewController {
	@FXML
	private Pagination pagination;
	@FXML
	private Label idLabel;
	@FXML
	private Label amountLabel;
	@FXML
	private Label currencyLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label dateTimeLabel;
	
	private List<Payment> payments;
	
	public PaymentsOverviewController() {}
	@FXML
	public void initialize(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setModel(Object model) {
		payments = (List<Payment>)model;
		pagination.setMaxPageIndicatorCount(payments.size());
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer index) {
				if(index<payments.size()) {
					set(payments.get(index));
				} else {
					pagination.setCurrentPageIndex(payments.size()-1);
					Alert errorAlert = new Alert(AlertType.WARNING);
					errorAlert.initOwner(stage);
					errorAlert.setTitle("Payments error");
					errorAlert.setContentText("No more payments");
					errorAlert.showAndWait();
				}
				return new Pane();
			}
		});
		
	}
	private void set(Payment payment){
		idLabel.setText(payment.getId().toString());
		amountLabel.setText(payment.getCurrency().getSymbol()+" "+DoubleFormatter.toString(payment.getAmount()));
		currencyLabel.setText(payment.getCurrency().get());
		dateTimeLabel.setText(DateFormatter.toString("dd-MM-yyyy HH:mm:ss", payment.getDate(), payment.getTime()));
		typeLabel.setText(payment.getType().get());
	}
}
