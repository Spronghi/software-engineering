package com.carloan.presentation.controller.overview;

import java.util.Optional;

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

import com.carloan.business.model.PriceCategory;
import com.carloan.presentation.error_handle.ErrorHandler;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdatePriceCategoryException;
import com.carloan.service.login.LoginService;
import com.carloan.service.price_category.PriceCategoryService;

public class PriceCategoryOverviewController extends OverviewController {
	@FXML
	private GridPane gridPane;
	@FXML
	private Button cancelButton;
	@FXML
	private ButtonBar buttonBar;
	private Button confirmButton;
	private Button deleteButton;
	private Label categoryLabel;
	private Label brLimitedLabel;
	private Label brUnlimitedLabel;
	private Label kmRateLabel;
	private TextField brLimitedField;
	private TextField brUnlimitedField;
	private TextField kmRateField;
	private PriceCategory category;
	@FXML
	public void initialize(){
		cancelButton.setOnAction(this::handleCancel);
	}
	@Override
	public void setModel(Object model) {
		category = (PriceCategory)model;
		categoryLabel = new Label(Character.toString(category.getCategory()));
		gridPane.add(categoryLabel, 1, 0);

		brLimitedLabel = new Label(Double.toString(category.getBaseRateLimit())+"€");
		gridPane.add(brLimitedLabel, 1, 2);

		brUnlimitedLabel = new Label(Double.toString(category.getBaseRateUnlimit())+"€");
		gridPane.add(brUnlimitedLabel, 1, 4);

		kmRateLabel = new Label(Double.toString(category.getKmRate())+"€");
		gridPane.add(kmRateLabel, 1, 6);
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
	private void handleEdit(ActionEvent event){
		brLimitedField = new TextField();
		gridPane.add(brLimitedField, 1, 2);

		brUnlimitedField = new TextField();
		gridPane.add(brUnlimitedField, 1, 4);
		
		kmRateField = new TextField();
		gridPane.add(kmRateField, 1, 6);

		confirmButton.setText("Ok");
		confirmButton.setOnAction(this::handleOk);
		deleteButton.setText("Clear");
		deleteButton.setOnAction(this::handleClear);
	}
	private void handleClear(ActionEvent event){
		brLimitedField.setText("");
		brUnlimitedField.setText("");
		kmRateField.setText("");
	}
	private void handleDelete(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Operator");
		alert.setHeaderText("Are you sure to delete this location?");
		alert.setContentText("Press ok to confirm");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			PriceCategoryService.delete(category);
		}
		stage.close();	
	}
	private void handleOk(ActionEvent event){
		try {
			checkMenu();
			category.setBaseRateLimit(Double.parseDouble(brLimitedField.getText()));
			category.setBaseRateUnlimit(Double.parseDouble(brUnlimitedField.getText()));
			category.setKmRate(Double.parseDouble(kmRateField.getText()));
			PriceCategoryService.update(category);
			stage.close();
		} catch (CommonServiceException e) {
			ErrorHandler.handle(e, "Error while update price category");
		}
	}
	private void checkMenu() throws CommonServiceException{
		String errorMessage = "";
		if(brLimitedField.getText()==null || brLimitedField.getText().equals("")){
			errorMessage += "Base rate limited field is empty\n";
		} else if(!brLimitedField.getText().matches("([?=0-9])+(\\.)+([?=0-9]).{2,}")){
			//errorMessage += "Wrong parameter on base rate limited\n";
		}
		if(brUnlimitedField.getText()==null || brUnlimitedField.getText().equals("")){
			errorMessage += "Base rate limited field is empty\n";
		} else if(!brUnlimitedField.getText().matches("([?=0-9])+(\\.)+([?=0-9]).{2,}")){
			//errorMessage += "Wrong parameter on base rate unlimited\n";
		}
		if(kmRateField.getText()==null || kmRateField.getText().equals("")){
			errorMessage += "Km rate field is empty\n";
		} else if(!kmRateField.getText().matches("([?=0-9])+(\\.)+([?=0-9]).{2,}")){
			//errorMessage += "Wrong parameter on km rate limited\n";
		}
		if(!errorMessage.equals("")){
			throw new UpdatePriceCategoryException(errorMessage);
		}
	}
	private void handleCancel(ActionEvent event){
		stage.close();
	}
}
