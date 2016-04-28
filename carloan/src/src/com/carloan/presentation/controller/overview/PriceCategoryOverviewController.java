package com.carloan.presentation.controller.overview;


import com.carloan.presentation.control.ModelControl;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import com.carloan.business.model.PriceCategory;
import com.carloan.integration.util.DoubleFormatter;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXFactory;

public class PriceCategoryOverviewController extends OverviewController {
	@FXML
	private GridPane grid;
	@FXML
	private Button cancelButton;
	@FXML
	private Button operationButton;
	private PriceCategory category;

	@FXML
	public void initialize(){
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(e->stage.close());
	}
    @Override
    public void setModel(Object model) {
        category = (PriceCategory) model;
        operationButton.setText(operation);

        switch (operation) {
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
	private void openSession() {
		Label categoryLabel = new Label(category.get());
		Label brLimitedLabel = new Label("€"+DoubleFormatter.toString(category.getBaseRateLimit()));
		Label brUnlimitedLabel = new Label("€"+DoubleFormatter.toString(category.getBaseRateUnlimit()));
		Label kmRateLabel = new Label("€"+DoubleFormatter.toString(category.getKmRate()));
        grid.add(categoryLabel, 1, 0);
        grid.add(brLimitedLabel, 1, 2);
        grid.add(brUnlimitedLabel, 1, 4);
		grid.add(kmRateLabel, 1, 6);

		operationButton.setVisible(false);
	}
    private void createSession() {
        TextField brLimitedField = FXFactory.createTextField("[A-z]");
        TextField brUnlimitedField = FXFactory.createTextField("[A-z]");
        TextField kmRateField = FXFactory.createTextField("[A-z]");
        grid.add(brLimitedField, 1, 2);
        grid.add(brUnlimitedField, 1, 4);
        grid.add(kmRateField, 1, 6);

        operationButton.setOnAction(e->{
            try {
                category.setBaseRateLimit(Double.valueOf(brLimitedField.getText()));
                category.setBaseRateUnlimit(Double.valueOf(brUnlimitedField.getText()));
                category.setKmRate(Double.valueOf(kmRateField.getText()));
                ServiceFactory.getModelFactory().getInstance(ServiceControl.PRICE_CATEGORY).create(category);
                FXAlert.information("Price category created", "Price category created successfully");
                stage.close();
            } catch (CreateModelException e1) {
                FXAlert.warning(e1, "Error while creating price category");
            }
        });
    }
    private void editSession() {
        TextField brLimitedField = FXFactory.createTextField(DoubleFormatter.toString(category.getBaseRateLimit()),"[A-z]");
        TextField brUnlimitedField = FXFactory.createTextField(DoubleFormatter.toString(category.getBaseRateUnlimit()),"[A-z]");
        TextField kmRateField = FXFactory.createTextField(DoubleFormatter.toString(category.getKmRate()),"[A-z]");
        grid.add(brLimitedField, 1, 2);
        grid.add(brUnlimitedField, 1, 4);
        grid.add(kmRateField, 1, 6);

        operationButton.setOnAction(e->{
            ButtonType result = FXAlert.confirmAction("Update","Price Category");
            if (result == ButtonType.OK) {
                try {
                    category.setBaseRateLimit(Double.valueOf(brLimitedField.getText()));
                    category.setBaseRateUnlimit(Double.valueOf(brUnlimitedField.getText()));
                    category.setKmRate(Double.valueOf(kmRateField.getText()));
                    ServiceFactory.getModelFactory().getInstance(ServiceControl.PRICE_CATEGORY).update(category);
                    changed = true;
                    stage.close();
                } catch (UpdateModelException e1) {
                    FXAlert.warning(e1, "Error while updating price category");
                }
            }
        });
    }
	private void deleteSession(){
        Label categoryLabel = new Label(category.get());
        Label brLimitedLabel = new Label("€"+DoubleFormatter.toString(category.getBaseRateLimit()));
        Label brUnlimitedLabel = new Label("€"+DoubleFormatter.toString(category.getBaseRateUnlimit()));
        Label kmRateLabel = new Label("€"+DoubleFormatter.toString(category.getKmRate()));
        grid.add(categoryLabel, 1, 0);
        grid.add(brLimitedLabel, 1, 2);
        grid.add(brUnlimitedLabel, 1, 4);
        grid.add(kmRateLabel, 1, 6);

        operationButton.setOnAction(e->{
            ButtonType result = FXAlert.confirmAction("Delete","Price Category");
            if (result == ButtonType.OK) {
                ServiceFactory.getModelFactory().getInstance(ServiceControl.PRICE_CATEGORY).delete(category);
                stage.close();
            }
        });
    }
}
