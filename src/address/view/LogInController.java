package address.view;

import database.Data;
import address.MainApp;
import address.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LogInController {
	@FXML
    private TextField usernameField;
	@FXML
    private PasswordField passwordField;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private ProgressIndicator progressIndicator;
	
	Customer customer;
    private MainApp mainApp;
    public LogInController(){}
    
    public void initialize(){}
    @FXML
    private void handleLogIn(){
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	customer=Data.customer().log(username, password);
    	if(customer!=null){
    		asAdministrator();
    	} else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Invalid Field");
            alert.setHeaderText("Please insert a valid username/email or password");
            alert.setContentText("Wrong username/email or password");
            alert.showAndWait();
    	}
    }
    @FXML
    private void handleRegister(){
    	mainApp.showRegister();
    }
    @FXML
    private void handleMouseClick(){
    	passwordField.setText("");
    }
    private void asAdministrator(){
    	mainApp.showCarOverview();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
