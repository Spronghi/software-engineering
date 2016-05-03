package com.carloan.service.check;

import com.carloan.business.model.Customer;
import com.carloan.service.ServiceFactory;
import com.carloan.service.model_service.ModelService;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

 class CustomerChecker implements Checker<Customer>{
    private String errorMessage = "";
    public void checkCreate(Customer customer) throws CreateModelException {
        errorMessage="";
        checkName(customer.getFirstName(),customer.getLastName(),"create");
        checkEmail(customer.getEmail(), "create");
        checkTelephone(customer.getTelephone());
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }
     public void checkUpdate(Customer customer) throws UpdateModelException {
        errorMessage="";
        checkName(customer.getFirstName(),customer.getLastName(),"update");
        checkEmail(customer.getEmail(), "update");
        checkTelephone(customer.getTelephone());
        if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkName(String firstName, String lastName, String operation){
        ModelService<Customer> customerService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER);
        if(firstName == null || firstName.equals("")){
            errorMessage += "First name field is empty\n";
        } else if(lastName == null || lastName.equals("")){
            errorMessage += "Last name field is empty\n";
        } else if(operation.equals("create") && customerService.getAll().stream()
                .anyMatch(p->p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName))){
            errorMessage += "Customer "+lastName+" "+firstName+" already present\n";
        }
    }
    private void checkEmail(String email, String operation){
        ModelService<Customer> customerService = ServiceFactory.getModelFactory().getInstance(ServiceControl.CUSTOMER);
        if(email.equals("") || email == null){
            errorMessage += "E-mail field is empty\n";
        } else if(!email.matches(".*[A-Za-z0-9-]@.*[A-Za-z0-9-]\\..*[A-Za-z0-9-]")){
            errorMessage += "Incorrect e-mail address\n";
        } else if(operation.equals("create") && customerService.getAll().stream().anyMatch(p->p.getEmail().equalsIgnoreCase(email))){
            errorMessage += "E-mail address already used\n";
        }
    }
    private void checkTelephone(String telephone){
        if(telephone == null || telephone.equals("")){
            errorMessage += "Telephone field is empty\n";
        } else if(!telephone.matches("([?=0-9]).{9,}")){
            errorMessage += "Incorrect telephone number\n";
        }
    }
}