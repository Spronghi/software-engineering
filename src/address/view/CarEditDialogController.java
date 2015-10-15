package address.view;

import address.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CarEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField lastKmField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField statusField;
    
    private Stage dialogStage;
    private Car car;
    private boolean okClicked = false;
    
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setCar(Car car) {
        this.car = car;

        nameField.setText(car.getName());
        licensePlateField.setText(car.getLicensePlate());
        lastKmField.setText(car.getLastKm().toString());
        categoryField.setText(car.getCategory());
        statusField.setText(car.getStatus());
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    private boolean isInputValid() {
    	String errorMessage = "";
		if(nameField.getText() == null || nameField.getText().length() == 0)
			errorMessage += "No valid name!\n";
		if(licensePlateField.getText() == null || licensePlateField.getText().length() == 0)
			errorMessage += "No valid license plate!\n";
    	if(lastKmField.getText() == null || lastKmField.getText().length() == 0){
			errorMessage += "No valid km!\n";
    	}else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(lastKmField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid km (must be a number)!\n"; 
            }
        }
		if(categoryField.getText() == null || categoryField.getText().length() == 0)
    		errorMessage += "No valid category!\n";
    	if(statusField.getText() == null || statusField.getText().length() == 0)
    		errorMessage += "No valid status!\n";
    
    	if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    @FXML
    private void handleOk() {
        if(isInputValid()){
            car.setName(nameField.getText());
            car.setLicensePlate(licensePlateField.getText());
            car.setLastKm(Integer.parseInt(lastKmField.getText()));
            car.setCategory(categoryField.getText());
            car.setStatus(statusField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}











