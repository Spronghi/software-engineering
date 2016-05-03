package com.carloan.presentation.controller.overview;

import com.carloan.business.model.Operator;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.exception.CommonServiceException;
import com.carloan.service.ServiceFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ResetPasswordViewController extends OverviewController{
    @FXML
    private Label comandLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button resetPasswordButton;
    private Operator operator;
    @FXML
    public void initialize(){
        cancelButton.setOnAction(e->stage.close());
        resetPasswordButton.setOnAction(e->{
            try {
                if(!passwordField.getText().equals(repeatPasswordField.getText())) {
                    throw new CommonServiceException("Password doesn't match");
                }
                operator.setPassword(passwordField.getText());
                ServiceFactory.getUtilFactory().getPasswordService().updatePassword(operator);
                FXAlert.information("Reset password", "Password reset successfully");
                changed = true;
                stage.close();
            } catch (CommonServiceException ex) {
                FXAlert.warning(ex,"Error while reset password");
            }
        });
        passwordField.setOnAction(resetPasswordButton.getOnAction());
        repeatPasswordField.setOnAction(resetPasswordButton.getOnAction());
    }
    @Override
    public void setModel(Object model) {
        operator = (Operator) model;
        if(!(operator.equals(ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator()) || ServiceFactory.getUtilFactory().getLoginService().getCurrentOperator().isAdmin())) {
            comandLabel.setText("You can't reset another operator's password");
            passwordField.setDisable(true);
            repeatPasswordField.setDisable(true);
            resetPasswordButton.setDisable(true);
        }
    }
}
