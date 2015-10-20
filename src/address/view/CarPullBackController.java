package address.view;

import database.Data;
import address.model.Car;
import address.model.Contract;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CarPullBackController extends CarEditDialogController{
	@FXML
	private Label kmLabel;
	@FXML
	private Label travLabel;
	@FXML
	private Label ammountLabel;
	@FXML
	private Label agencyNameLabel;
	@FXML
	private Label agencyCityLabel;
	@FXML
	private Label agencyCapLabel;
	@FXML
	private Label agencyAddressLabel;
	@FXML
	private Label startDateLabel;
	@FXML
	private Label limitDateLabel;
	@FXML
	private Label contractTypeLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label telephoneLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label carNameLabel;
	@FXML
	private Label licensePlateLabel;
	@FXML
	private Label statusLabel;
	
	@FXML
	private TextField kmField;
	
 	private Stage dialogStage;
 	private Contract contract;
 	private Car car;
    private boolean payClicked = false;

    @FXML
    private void initialize() {

    }
    
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
       	contract = Data.contract().get(car);
    	firstNameLabel.setText(contract.getCustomer().getFirstName());
    	lastNameLabel.setText(contract.getCustomer().getLastName());
    	telephoneLabel.setText(contract.getCustomer().getTelephone());
    	emailLabel.setText(contract.getCustomer().getEmail());
    	carNameLabel.setText(contract.getCar().getName());
    	licensePlateLabel.setText(contract.getCar().getLicensePlate());
    	statusLabel.setText(contract.getCar().getStatus());
    	agencyNameLabel.setText(contract.getAgency().getName());
    	agencyCityLabel.setText(contract.getAgency().getLocation().getCity());
    	agencyCapLabel.setText(contract.getAgency().getLocation().getCap());
    	agencyAddressLabel.setText(contract.getAgency().getLocation().getAddress1());
    	startDateLabel.setText(contract.getStartDate());
    	limitDateLabel.setText(contract.getReturnLimitDate());
    	contractTypeLabel.setText(contract.getContractType());
    }
	public void setCar(Car car){
		this.car = car;
	}
	public boolean isPayClicked(){
		return payClicked;
	}
	@FXML
	private void handleCancel() {
        dialogStage.close();
    }
    private boolean isInputValid() {
    	String errorMessage="";
    	if(kmField.getText() == null || kmField.getText().length() == 0){
			errorMessage += "No valid km!\n";
    	} else {
    		try {
            	if( Integer.parseInt(kmField.getText())>99999){
       				errorMessage += "Km not valid (must be <99999)";
            	}
            } catch (NumberFormatException e) {
                errorMessage += "No valid km (must be a number)!\n"; 
            }
        }
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
	private void handleCalculate(){
		if(isInputValid()){
			int currentKm = Integer.parseInt(kmField.getText());
			int lastKm = this.car.getLastKm();
			if(lastKm < currentKm)
				travLabel.setText(Integer.toString(currentKm - this.car.getLastKm()));
			else if(lastKm > currentKm){
				int result = -(lastKm - 999999);
				result = result + currentKm;
				travLabel.setText(Integer.toString(result));
			}
		}
	}
	
	@FXML
	private void handlePay(){
		if(isInputValid()){
			this.car.setLastKm(Integer.parseInt(kmLabel.getText()));
		}
	}
}
