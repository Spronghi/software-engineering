package address;


import java.io.IOException;

import database.TableData;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import address.model.Car;
import address.view.CarOverviewController;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Car> carData;
    public MainApp(){
        TableData tableData = new TableData();
        carData = tableData.getAvaibleCar();
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CarLoan");
        
        initRootLayout();
        showCarOverview();
    }
    public void initRootLayout(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/address/view/RootLayout.fxml"));
        try {
            rootLayout = (BorderPane)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void showCarOverview(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/address/view/CarOverview.fxml"));
        AnchorPane carOverview=null;
        try {
            carOverview = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootLayout.setCenter(carOverview);
        CarOverviewController controller = loader.getController();
        controller.setMainApp(this);
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public ObservableList<Car> getCarData() {
        return carData;
    }
    public static void main(String[] args) {
        launch(args);
    }

}