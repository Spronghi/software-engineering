package com.carloan.presentation.controller.overview;

import com.carloan.business.model.Operator;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class SetAdministratorViewController extends OverviewController{
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private Operator operator;

    @FXML
    public void initialize(){
        cancelButton.setOnAction(e->stage.close());
    }
    @Override
    public void setModel(Object model) {
        operator = (Operator) model;

        checkBox.setSelected(operator.isAdmin());
        okButton.setOnAction(e->{
            try {
                operator.setAdmin(checkBox.isSelected());
                ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR).update(operator);
                FXAlert.information("Operator updated", operator.getUsername() + " is now an administrator");
                changed = true;
                stage.close();
            } catch (CommonServiceException e1) {
                FXAlert.warning(e1, "Error while updating operator");
            }
        });
    }
}
