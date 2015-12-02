package com.carloan.service.car;

import com.carloan.business.model.Car;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateCarException;

enum CarChecker {
	INSTANCE;
	public static void checkCreate(Car car) throws CommonServiceException{
		String errorMessage="";
		if(car.getLicensePlate()==null || car.getLicensePlate().equals("")){
			errorMessage += "License plate field is empty\n";
		} else if(car.getLicensePlate().matches("([A-Z]){2,}([0-9]){3,}([A-Z]){2,}")){
			errorMessage += "Uncorrect license plate\n";
		} else if(CarService.getAll().stream().anyMatch(p->p.getLicensePlate().equals(car.getLicensePlate()))){
			errorMessage += "License plate already exists\n";
		}
		if(car.getModel()==null || car.getModel().equals(""))
			errorMessage += "Model field is empty\n";
		if(car.getKm()==0)
			errorMessage += "Km field is empty\n";
		if(car.getStatus()==null || car.getStatus().equals(""))
			errorMessage += "Status field is empty\n";
		if(car.getCategory() == null)
			errorMessage += "Category field is empty\n";
		if(!errorMessage.equals(""))
			throw new UpdateCarException(errorMessage);
	}
	public static void checkUpdate(Car car) throws CommonServiceException{
		String errorMessage="";
		if(car.getLicensePlate()==null || car.getLicensePlate().equals("")){
			errorMessage += "License plate field is empty\n";
		} else if(car.getLicensePlate().matches("([A-Z]){2,}([0-9]){3,}([A-Z]){2,}")){
			errorMessage += "Uncorrect license plate\n";
		}
		if(car.getModel()==null || car.getModel().equals(""))
			errorMessage += "Model field is empty\n";
		if(car.getKm()==0)
			errorMessage += "Km field is empty\n";
		if(car.getStatus()==null || car.getStatus().equals(""))
			errorMessage += "Status field is empty\n";
		if(car.getCategory() == null)
			errorMessage += "Category field is empty\n";
		if(!errorMessage.equals(""))
			throw new UpdateCarException(errorMessage);
	}
}
