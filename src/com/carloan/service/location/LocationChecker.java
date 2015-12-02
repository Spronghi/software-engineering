package com.carloan.service.location;

import com.carloan.business.model.Location;
import com.carloan.service.agency.AgencyService;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateLocationException;

public enum LocationChecker {
	INSTANCE;
	public static void check(Location location) throws CommonServiceException{
		String errorMessage = "";
		if(location.getCity()==null || location.getCity().equals("")){
			errorMessage += "City field is empty\n";
		} else if(location.getPostalNo()==null || location.getPostalNo().equals("")){
			errorMessage += "Postal number field is empty\n";
		} else if(location.getRoad()==null || location.getRoad().equals("")){
			errorMessage += "Road field is empty\n";
		} else if(AgencyService.getAll().stream()
				.anyMatch(p->p.getLocation().getCity().equalsIgnoreCase(location.getCity()) &&
						p.getLocation().getPostalNo().equalsIgnoreCase(location.getPostalNo()) &&
						p.getLocation().getRoad().equalsIgnoreCase(location.getRoad()))){
			errorMessage += "Location already exists\n";
		}
		if(!errorMessage.equals("")){
			throw new UpdateLocationException(errorMessage);
		}
	}
}
