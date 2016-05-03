package com.carloan.presentation.factory;

import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Operator;
import com.carloan.presentation.control.ModelControl;
import com.carloan.presentation.controller.Controller;
import com.carloan.presentation.controller.overview.OverviewController;
import com.carloan.presentation.factory.internal.CategoryRegister;
import com.carloan.presentation.factory.internal.TableViewRegister;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public enum FXLoader {
    INSTANCE;
    private static TableViewRegister register;
    private static final String VIEW_PATH = "/com/carloan/presentation/view/";

    public static void loadView(String name, Stage stage){
        try {
            String path = VIEW_PATH + name + ".fxml";
            FXMLLoader loader = new FXMLLoader(FXLoader.class.getResource(path));
            Controller.setStage(stage);
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e){
            FXAlert.warning(e,"Loading Error");
        }
    }
    public static boolean loadOverview(Object model, String operation){
        try {
            String path;
            if (model instanceof Contract && operation.equals(ModelControl.CLOSE)) {
                path = VIEW_PATH + "CloseContractView.fxml";
            } else if (model instanceof Contract && operation.equals(ModelControl.NEW)){
                path = VIEW_PATH + "NewContractView.fxml";
            } else if(model instanceof Car && operation.equals(ModelControl.EDIT_STATUS)) {
                path = VIEW_PATH + "EditCarStatusView.fxml";
            } else if(model instanceof Operator && operation.equals(ModelControl.RESET_PASSWORD)) {
                path = VIEW_PATH + "ResetPasswordView.fxml";
            } else if(model instanceof Operator && operation.equals(ModelControl.SET_ADMINISTRATOR)) {
                path = VIEW_PATH + "SetAdministratorView.fxml";
            } else {
                path = VIEW_PATH + CategoryRegister.get(model.getClass()) + "Overview.fxml";
            }

            FXMLLoader loader = new FXMLLoader(FXLoader.class.getResource(path));
            Stage currentStage = new Stage();
            currentStage.setScene(new Scene(loader.load()));
            currentStage.setTitle(CategoryRegister.get(model.getClass())+" Overview");
            currentStage.initModality(Modality.WINDOW_MODAL);

            OverviewController controller = loader.getController();
            controller.setStage(currentStage);
            controller.setOperation(operation);
            controller.setModel(model);
            currentStage.showAndWait();
            return controller.isChanged();
        } catch (IOException e){
            FXAlert.warning(e,"Loading Error");
        }
        return false;
    }
    public static  <T> TableView<T> loadTable(String category){
        return register.getTable(category);
    }
    public static Set<String> loadTableCategories(){
        return register.getCategories();
    }
    public static void initializeTables(){
        register = new TableViewRegister();
    }
}
