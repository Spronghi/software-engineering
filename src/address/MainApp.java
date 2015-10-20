package address;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import address.model.Car;
import address.view.CarEditDialogController;
import address.view.CarOverviewController;
import address.view.CarPullBackController;
import address.view.LogInController;
import address.view.RegisterDialogController;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    public MainApp(){}

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CarLoan");
        
        initRootLayout();
        showLogIn();
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
    public boolean showRegister(){
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("/address/view/RegisterDialog.fxml"));
        AnchorPane page=null;
        try {
        	page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Register an account");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        RegisterDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
        return controller.isOkClicked();
    }
    public void showLogIn(){
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/address/view/LogIn.fxml"));
        AnchorPane page=null;
        try {
        	page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootLayout.setCenter(page);
        LogInController controller = loader.getController();
        controller.setMainApp(this);
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
	public boolean showCarEditDialog(Car car) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/address/view/CarEditDialog.fxml"));
		AnchorPane page = null;
		try {
			page = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Car");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        CarEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCar(car);
        dialogStage.showAndWait();
        return controller.isOkClicked();
	}
	public boolean showCarPullBackDialog(Car car) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/address/view/CarPullBackDialog.fxml"));
		AnchorPane page = null;
		try {
			page = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Pull Back Car");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        CarPullBackController controller = loader.getController();
        controller.setCar(car);
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();
        return controller.isOkClicked();
	}
    public static void main(String[] args) {
        launch(args);
    }

}
