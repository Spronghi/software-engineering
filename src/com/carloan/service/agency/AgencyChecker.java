package com.carloan.service.agency;

import com.carloan.business.model.Agency;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateAgencyException;

enum AgencyChecker {
	INSTANCE;
	public static void check(Agency agency) throws CommonServiceException{
		String errorMessage = "";
		if(agency.getName()==null || agency.getName().equals("")){
			errorMessage += "Name field is empty\n";
		} else if(AgencyService.getAll().stream().anyMatch(p->p.getName().equalsIgnoreCase(agency.getName()))){
			errorMessage += "This name already exists\n";
		}
		if(!errorMessage.equals("")){
			throw new UpdateAgencyException(errorMessage);
		}
	}
}
