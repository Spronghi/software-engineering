package com.carloan.service.check;

import com.carloan.business.model.Contract;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

import java.time.LocalDate;

class ContractChecker implements Checker<Contract>{
    private String errorMessage = "";
    public void checkCreate(Contract contract) throws CreateModelException {
        errorMessage="";
        checkStartDate(contract.getStartDate(), "create");
        checkEndDate(contract.getEndDate(),contract.getStartDate(),"create");
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }
    public void checkUpdate(Contract contract) throws UpdateModelException {
        errorMessage="";
        checkStartDate(contract.getStartDate(), "update");
        checkEndDate(contract.getEndDate(),contract.getStartDate(),"update");
        if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkStartDate(LocalDate startDate, String operation){
        if(startDate==null){
            errorMessage += "Start date field empty\n";
        } else if(operation.equals("create") && startDate.isBefore(LocalDate.now())){
            errorMessage += "Start date cannot be before today\n";
        }
    }
    private void checkEndDate(LocalDate endDate, LocalDate startDate, String operation){
        if(endDate==null){
            errorMessage += "End date field empty\n";
        } else if(operation.equals("create") && endDate.isBefore(LocalDate.now())){
            errorMessage += "End date cannot be before today\n";
        } else if(operation.equals("update") && endDate.isBefore(startDate)){
            errorMessage += "End date cannot be before start date\n";
        }
    }
}
