package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.Operator;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.ServiceFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.*;

class OperatorService implements ModelService<Operator> {
    public List<Operator> getAll() {
		return DAOFactory.getInstance(Operator.class).getAll();
	}
	public void create(Operator operator) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.OPERATOR).checkCreate(operator);
		operator.setPassword(ServiceFactory.getUtilFactory().getPasswordService().generatePasswordHash(operator.getPassword()));
		DAOFactory.getInstance(Operator.class).create(operator);
	}
	public void update(Operator operator) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.OPERATOR).checkUpdate(operator);
		DAOFactory.getInstance(Operator.class).update(operator);
	}
	public void delete(Operator operator) {
		DAOFactory.getInstance(Operator.class).delete(operator);
	}
	public Operator get(int id) {
		return DAOFactory.getInstance(Operator.class).get(id);
	}

}
