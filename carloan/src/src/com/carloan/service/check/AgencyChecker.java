package com.carloan.service.check;

import com.carloan.business.model.Agency;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;
import com.carloan.service.model_service.ModelService;

class AgencyChecker implements Checker<Agency>{
    private String errorMessage = "";
    public void checkCreate(Agency agency) throws CreateModelException {
        errorMessage = "";
        checkName(agency.getName(), "create");
        if(!errorMessage.equals("")){
            throw new CreateModelException(errorMessage);
        }
    }
    public void checkUpdate(Agency agency) throws UpdateModelException {
        errorMessage = "";
        checkName(agency.getName(), "update");
        if(!errorMessage.equals("")){
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkName(String name, String operation){
        ModelService<Agency> agencyService = ServiceFactory.getModelFactory().getInstance(ServiceControl.AGENCY);

        if(name==null || name.equals("")){
            errorMessage += "Name field is empty\n";
        } else if(operation.equals("create") && agencyService.getAll().stream().anyMatch(p->p.getName().equalsIgnoreCase(name))){
            errorMessage += "This name already exists\n";
        }
    }
}