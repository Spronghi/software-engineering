package com.carloan.service.contract;

import java.time.LocalDate;

import com.carloan.business.model.Contract;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateContractException;

enum ContractChecker {
	INSTANCE;
	public static void checkCreate(Contract contract) throws CommonServiceException{
		String errorMessage="";
		if(contract.getStartDate()==null){
			errorMessage += "Start date field empty\n"; 
		} else if(contract.getStartDate().isBefore(LocalDate.now())){
			errorMessage += "Start date cannot be before today\n";
		}
		if(contract.getEndDate()==null){
			errorMessage += "End date field empty\n"; 
		} else if(contract.getStartDate().isBefore(LocalDate.now())){
			errorMessage += "Start date cannot be before today\n";
		}
		if(!errorMessage.equals(""))
			throw new UpdateContractException(errorMessage);
	}
	public static void checkUpdate(Contract contract) throws CommonServiceException{
		String errorMessage="";
		if(contract.getStartDate()==null){
			errorMessage += "Start date field empty\n"; 
		}
		if(contract.getEndDate()==null){
			errorMessage += "End date field empty\n"; 
		} else if(contract.getEndDate().isBefore(contract.getStartDate())){
			errorMessage += "End date canot be before start date\n";
		}
		
		if(!errorMessage.equals(""))
			throw new UpdateContractException(errorMessage);
	}
}
