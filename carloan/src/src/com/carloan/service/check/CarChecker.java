package com.carloan.service.check;

import com.carloan.business.model.Car;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;
import com.carloan.service.model_service.ModelService;

class CarChecker implements Checker<Car>{
    private String errorMessage = "";

    public void checkCreate(Car car) throws CreateModelException {
        errorMessage = "";
        checkLicensePlate(car.getLicensePlate(), "create");
        checkModel(car.getModel());
        checkKm(car.getKm());
        checkCategory(car.getCategory().get());
        checkStatus(car.getStatus().get());
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }

    public void checkUpdate(Car car) throws UpdateModelException {
        errorMessage="";
        checkLicensePlate(car.getLicensePlate(), "update");
        checkModel(car.getModel());
        checkKm(car.getKm());
        checkCategory(car.getCategory().get());
        checkStatus(car.getStatus().get());
        if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkModel(String model){
        if(model==null || model.equals("")) {
            errorMessage += "Model field is empty\n";
        }
    }
    private void checkLicensePlate(String licensePlate, String operation){
        ModelService<Car> carService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CAR);

        if(licensePlate==null || licensePlate.equals("")){
            errorMessage += "License plate field is empty\n";
        } else if(licensePlate.matches("([A-Z]){2,}([0-9]){3,}([A-Z]){2,}")){
            errorMessage += "Incorrect license plate\n";
        } else if(operation.equals("create") && carService.getAll().stream().anyMatch(p->p.getLicensePlate().equals(licensePlate))){
            errorMessage += "License plate already exists\n";
        }
    }
    private void checkKm(int km){
        if(km==0) {
            errorMessage += "Km field is empty\n";
        }
    }
    private void checkStatus(String status){
        if(status==null || status.equals("")) {
            errorMessage += "Status field is empty\n";
        }
    }
    private void checkCategory(String category){
        if(category==null || category.equals("")) {
            errorMessage += "Category field is empty\n";
        }
    }
}
