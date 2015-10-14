package address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import address.MainApp;
import address.model.Car;

public class CarOverviewController {
    @FXML
    private TableView<Car> carStatusTable;
    @FXML
    private TableColumn<Car, String> carColumn;
    @FXML
    private TableColumn<Car, String> statusColumn;
    @FXML
    private Label licensePlateLabel;
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
        carColumn.setCellValueFactory(cellData -> cellData.getValue().licensePlateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        showCarDetails(null);
        carStatusTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCarDetails(newValue) );
    }
    private void showCarDetails(Car car){
        if(car != null){
            licensePlateLabel.setText(car.getLicensePlate());
            kmLabel.setText(car.getLastKm());
            categoryLabel.setText(car.getCategory());
            statusLabel.setText(car.getStatus());
        } else {
            licensePlateLabel.setText("");
            kmLabel.setText("");
            categoryLabel.setText("");
            statusLabel.setText("");
        }
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        carStatusTable.setItems(mainApp.getCarData());
    }
}