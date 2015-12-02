package com.carloan.service.payment;

import java.util.List;
import java.util.stream.Collectors;

import com.carloan.business.model.Contract;
import com.carloan.business.model.Currency;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

public enum PaymentService {
	INSTANCE;
	public static void create(Payment payment) throws CommonServiceException {
		PaymentChecker.checkCreate(payment);
		DAOFactory.getInstance(Payment.class).create(payment);		
	}
	public static List<Payment> getPaymentsByContract(Contract contract){
		return DAOFactory.getInstance(Payment.class).getAll().stream()
				.filter(p->p.getContract().getContractNo()==contract.getContractNo())
				.collect(Collectors.toList());
	}
	public static List<Currency> getCurrencies(){
		return DAOFactory.getInstance(Currency.class).getAll();
	}
	public static Currency getCurrency(int id){
		return DAOFactory.getInstance(Currency.class).get(id);
	}
	public static List<PaymentType> getTypes(){
		return DAOFactory.getInstance(PaymentType.class).getAll();
	}
	public static PaymentType getType(int id){
		return DAOFactory.getInstance(PaymentType.class).get(id);
	}
}
