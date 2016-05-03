package com.carloan.service.model_service;

import java.util.List;
import java.util.stream.Collectors;

import com.carloan.business.model.Contract;
import com.carloan.business.model.Payment;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.ServiceFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class PaymentService implements OrderedService<Payment> {
    public Payment get(int id) {
        return DAOFactory.getInstance(Payment.class).get(id);
    }
    public List<Payment> getAll() {
        return DAOFactory.getInstance(Payment.class).getAll();
    }
    public void create(Payment payment) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.PAYMENT).checkCreate(payment);
		DAOFactory.getInstance(Payment.class).create(payment);		
	}
	public void update(Payment payment) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.PAYMENT).checkUpdate(payment);
		DAOFactory.getInstance(Payment.class).update(payment);		
	}
	public void delete(Payment payment) {
		DAOFactory.getInstance(Payment.class).delete(payment);		
	}
	public List<Payment> getAllBy(int contractId){
        Contract contract = ServiceFactory.getModelFactory().<Contract>getInstance(ServiceControl.CONTRACT).get(contractId);
		return DAOFactory.getInstance(Payment.class).getAll().stream()
				.filter(p->p.getContract().getId()==contract.getId())
				.collect(Collectors.toList());
	}
}
