package com.carloan.service.check;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Location;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;
import com.carloan.service.model_service.ModelService;

class LocationChecker implements Checker<Location>{
    private String errorMessage = "";
    public void checkCreate(Location location) throws CreateModelException {
        errorMessage = "";
        checkCity(location.getCity());
        checkPostalNo(location.getPostalNo());
        checkRoad(location.getRoad());
        checkExists(location);
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }
    public void checkUpdate(Location location) throws UpdateModelException {
        errorMessage = "";
        checkCity(location.getCity());
        checkPostalNo(location.getPostalNo());
        checkRoad(location.getRoad());
        if(!errorMessage.equals("")){
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkCity(String city){
        if(city==null || city.equals("")) {
            errorMessage += "City field is empty\n";
        }
    }
    private void checkPostalNo(String postalNo){
        if(postalNo==null || postalNo.equals("")) {
            errorMessage += "Postal number field is empty\n";
        } else if(postalNo.length()>5) {
            errorMessage += "Postal number field is too long\n";
        } else if(postalNo.length()<5) {
            errorMessage += "Postal number field is too short\n";
        }
    }
    private void checkRoad(String road){
        if(road==null || road.equals("")) {
            errorMessage += "Road field is empty\n";
        }
    }
    private void checkExists(Location location){
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);
        if(agencyService.getAll().stream()
                .anyMatch(p->p.getLocation().getCity().equalsIgnoreCase(location.getCity()) &&
                        p.getLocation().getPostalNo().equalsIgnoreCase(location.getPostalNo()) &&
                        p.getLocation().getRoad().equalsIgnoreCase(location.getRoad()))){
            errorMessage += "Location already exists\n";
        }
    }
}
