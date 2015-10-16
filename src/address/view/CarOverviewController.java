package address.view;

import database.TableData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import address.MainApp;
import address.model.Car;

public class CarOverviewController {
    @FXML
    private TableView<Car> carStatusTable;
    @FXML
    private TableColumn<Car, String> nameColumn;
    @FXML
    private TableColumn<Car, String> licensePlateColumn;
    @FXML
    private TableColumn<Car, String> statusColumn;
    @FXML
    private Label licensePlateLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label kmLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label statusLabel;
    
    private MainApp mainApp;
    public CarOverviewController(){}
    
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	licensePlateColumn.setCellValueFactory(cellData -> cellData.getValue().licensePlateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        showCarDetails(null);
        carStatusTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCarDetails(newValue) );
    }
    private void showCarDetails(Car car){
        if(car != null){
        	nameLabel.setText(car.getName());
            licensePlateLabel.setText(car.getLicensePlate());
            kmLabel.setText(car.getLastKm().toString());
            categoryLabel.setText(car.getCategory());
            statusLabel.setText(car.getStatus());
        } else {
        	nameLabel.setText("");
            licensePlateLabel.setText("");
            kmLabel.setText("");
            categoryLabel.setText("");
            statusLabel.setText("");
        }
    }
    @FXML
    private void handleDeleteCar() {
        int selectedIndex = carStatusTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
        	carStatusTable.getItems().remove(selectedIndex);
        	new TableData().deleteCar(carStatusTable.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Car Selected");
            alert.setContentText("Please select a car in the table");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleNewCar() {
        Car tempCar = new Car();
        boolean okClicked = mainApp.showCarEditDialog(tempCar);
        if (okClicked) {
            mainApp.getCarData().add(tempCar);
            new TableData().newCar(tempCar);
        }
    }
    @FXML
    private void handleEditCar() {
        Car selectedCar = carStatusTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            boolean okClicked = mainApp.showCarEditDialog(selectedCar);
            if (okClicked) {
                showCarDetails(selectedCar);
                new TableData().editCar(selectedCar);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Car Selected");
            alert.setContentText("Please select a car in the table.");

            alert.showAndWait();
        }
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        carStatusTable.setItems(mainApp.getCarData());
    }
}