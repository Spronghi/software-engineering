package com.carloan.presentation.controller.overview;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import com.carloan.business.model.Payment;
import com.carloan.integration.util.DateFormatter;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.factory.FXUtil;

public class PaymentsOverviewController extends OverviewController {
	@FXML
	private AnchorPane center;
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	
	private Pagination pagination;
	private Label idLabel;
	private Label amountLabel;
	private Label currencyLabel;
	private Label typeLabel;
	private Label dateTimeLabel;
	
	public PaymentsOverviewController() {}
	@FXML
	public void initialize(){
		cancelButton.setOnAction(e->stage.close());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(Object model) {
		List<Payment> payments = (List<Payment>)model;
		pagination = new Pagination(payments.size());

		center.getChildren().add(pagination);
		FXUtil.setAnchors(pagination, 0, 40, 0, 0);
		
		idLabel = new Label();
		amountLabel = new Label();
		currencyLabel = new Label();
		dateTimeLabel = new Label();
		typeLabel = new Label();
		
		grid.add(idLabel, 1, 0);
		grid.add(amountLabel, 1, 2);
		grid.add(currencyLabel, 1, 4);
		grid.add(dateTimeLabel, 1, 6);
		grid.add(typeLabel, 1, 8);
		
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer index) {
				if(index<payments.size()){
					set(payments.get(index));
					return new Pane();
				}
				return null;
			}
		});
	}
	private void set(Payment payment){
		idLabel.setText(Integer.toString(payment.getId()));
		amountLabel.setText(payment.getCurrency().getSymbol()+" "+DoubleFormatter.toString(payment.getAmount()));
		currencyLabel.setText(payment.getCurrency().get());
		dateTimeLabel.setText(DateFormatter.toString("dd-MM-yyyy HH:mm:ss", payment.getDate(), payment.getTime()));
		typeLabel.setText(payment.getType().get());
	}
}
