package com.carloan.service.customer;

import com.carloan.business.model.Customer;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.UpdateCustomerException;

enum CustomerChecker {
	INSTANCE;
	public static void checkCreate(Customer customer) throws CommonServiceException{
		String errorMessage="";
		if(customer.getFirstName() == null || customer.getFirstName().equals("")){
			errorMessage += "First name field is empty\n";
		} else if(customer.getLastName() == null || customer.getLastName().equals("")){
			errorMessage += "Last name field is empty\n";
		} else if(CustomerService.getAll().stream()
				.anyMatch(p->p.getFirstName().equalsIgnoreCase(customer.getFirstName()) &&
						p.getLastName().equalsIgnoreCase(customer.getLastName()))){
			errorMessage += "Customer "+customer.getLastName()+" "+customer.getFirstName()+" already present\n";
		}
		if(customer.getEmail().equals("") || customer.getEmail() == null){
			errorMessage += "E-mail field is empty\n";
		} else if(!customer.getEmail().matches(".*[A-Za-z0-9-]@.*[A-Za-z0-9-]\\..*[A-Za-z0-9-]")){
			errorMessage += "Uncorrect e-mail address\n";
		} else if(CustomerService.getAll().stream().anyMatch(p->p.getEmail().equalsIgnoreCase(customer.getEmail()))){
			errorMessage += "E-mail address already used\n";
		}
		if(customer.getTelephone() == null || customer.getTelephone().equals("")){
			errorMessage += "Telephone field is empty\n";
		} else if(!customer.getTelephone().matches("([?=0-9]).{9,}")){
			errorMessage += "Uncorrect telephone number\n";
		}
		if(!errorMessage.equals(""))
			throw new UpdateCustomerException(errorMessage);
	}
	public static void checkUpdate(Customer customer) throws CommonServiceException{
		String errorMessage="";
		if(customer.getFirstName() == null || customer.getFirstName().equals("")){
			errorMessage += "First name field is empty\n";
		} else if(customer.getLastName() == null || customer.getLastName().equals("")){
			errorMessage += "Last name field is empty\n";
		}
		if(customer.getEmail().equals("") || customer.getEmail() == null){
			errorMessage += "E-mail field is empty\n";
		} else if(!customer.getEmail().matches(".*[A-Za-z0-9-]@.*[A-Za-z0-9-]\\..*[A-Za-z0-9-]")){
			errorMessage += "Uncorrect e-mail address\n";
		}
		if(customer.getTelephone() == null || customer.getTelephone().equals("")){
			errorMessage += "Telephone field is empty\n";
		} else if(!customer.getTelephone().matches("([?=0-9]).{9,}")){
			errorMessage += "Uncorrect telephone number\n";
		}
		if(!errorMessage.equals(""))
			throw new UpdateCustomerException(errorMessage);
	}
}
