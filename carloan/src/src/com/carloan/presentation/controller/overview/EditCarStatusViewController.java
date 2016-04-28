package com.carloan.presentation.controller.overview;

import com.carloan.business.model.Car;
import com.carloan.business.model.Status;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.presentation.factory.FXUtil;
import com.carloan.presentation.factory.model.ModelMenuButton;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CommonServiceException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class EditCarStatusViewController extends OverviewController{
    @FXML
    private GridPane grid;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    private ModelMenuButton<Status> statusMenu;
    private Car car;

    @FXML
    public void initialize(){
        cancelButton.setOnAction(e->stage.close());
    }
    @Override
    public void setModel(Object model) {
        car = (Car) model;

        statusMenu = new ModelMenuButton<>(car.getStatus().get(),car.getStatus());
        ServiceFactory.getModelFactory().<Status>getInstance(ServiceControl.CAR_STATUS).getAll()
        		.stream().forEach(status->{
        			if(!status.get().equals("hired"))
        				FXUtil.addMenuItem(statusMenu,status.get(),status);
        		});
        statusMenu.setPrefWidth(200);
        grid.add(statusMenu,1,0);

        okButton.setOnAction(e->{
            try {
                car.setStatus(statusMenu.getModel());
                ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR).update(car);
                FXAlert.information("Status Update", "Status updated successfully");
                changed = true;
                stage.close();
            } catch (CommonServiceException e1) {
                FXAlert.warning(e1, "Error while updating status");
            }
        });
    }
}
