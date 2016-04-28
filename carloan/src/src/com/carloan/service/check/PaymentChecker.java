package com.carloan.service.check;

import com.carloan.business.model.Currency;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

import java.time.LocalDate;

class PaymentChecker implements Checker<Payment>{
    private String errorMessage = "";

    public void checkCreate(Payment payment) throws CreateModelException{
        errorMessage = "";
        checkAmount(payment.getAmount());
        checkCurrency(payment.getCurrency());
        checkDate(payment.getDate());
        checkType(payment.getType());
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }

	public void checkUpdate(Payment payment) throws UpdateModelException{
		errorMessage = "";
        checkAmount(payment.getAmount());
        checkCurrency(payment.getCurrency());
        checkDate(payment.getDate());
        checkType(payment.getType());
		if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
        }
	}
    private void checkAmount(double amount){
        if(amount==0.0) {
            errorMessage += "Amount field is empty\n";
        }
    }
    private void checkCurrency(Currency currency){
        if(currency== null) {
            errorMessage += "Select a currency\n";
        }
    }
    private void checkDate(LocalDate date){
        if(date==null) {
            errorMessage += "Select a date\n";
        }
    }
    private void checkType(PaymentType amount){
        if(amount== null) {
            errorMessage += "Select a payment type\n";
        }
    }
}
