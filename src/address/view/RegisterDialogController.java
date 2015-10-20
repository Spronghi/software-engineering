package address.view;

import database.Data;
import address.MainApp;
import address.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterDialogController {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField telephoneField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField repeatPasswordField;
	
	private Stage dialogStage;
    private Customer customer;
    private boolean okClicked = false;
    
    @FXML
    private void initialize() {}
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private void getCustomer(){
    	customer = new Customer(firstNameField.getText(), lastNameField.getText(), telephoneField.getText(), 
    			emailField.getText(), usernameField.getText(), passwordField.getText());
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    private boolean isInputValid() {
		return true;
    }
    @FXML
    private void handleOk() {
        if(isInputValid()){
        	getCustomer();
        	Data.customer().add(customer);
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
