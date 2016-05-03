package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.ServiceFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class AgencyService implements ModelService<Agency> {
	public List<Agency> getAll() {
		return DAOFactory.getInstance(Agency.class).getAll();
	}
	public Agency get(int id) {
        return DAOFactory.getInstance(Agency.class).get(id);
	}
	public void create(Agency agency) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.AGENCY).checkCreate(agency);
		DAOFactory.getInstance(Agency.class).create(agency);
	}
	public void update(Agency agency) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.AGENCY).checkUpdate(agency);
        ServiceFactory.getModelFactory().getInstance(ServiceControl.LOCATION).update(agency.getLocation());
		DAOFactory.getInstance(Agency.class).update(agency);
	}
	public void delete(Agency agency) {
		DAOFactory.getInstance(Agency.class).delete(agency);
	}
}
