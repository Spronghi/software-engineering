package com.carloan.service.payment;

import com.carloan.business.model.Payment;
import com.carloan.service.exception.AddPaymentException;
import com.carloan.service.exception.CommonServiceException;

enum PaymentChecker {
	INSTANCE;
	public static void checkCreate(Payment payment) throws CommonServiceException{
		boolean check = true;
		if(payment.getAmount()==0.0)
			check = false;
		if(payment.getCurrency() == null)
			check = false;		
		if(payment.getDate()==null)
			check = false;		
		if(payment.getType() == null)
			check = false;		
		if(!check){
			throw new AddPaymentException("Can't add your payment\n");
		}
	}

}
