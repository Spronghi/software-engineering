package com.carloan.service.model_service;

import java.util.List;
import java.util.stream.Collectors;

import com.carloan.business.model.Contract;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class ContractService implements OrderedService<Contract> {
    public Contract get(int id) {
        return DAOFactory.getInstance(Contract.class).get(id);
    }
    public List<Contract> getAll() {
		return DAOFactory.getInstance(Contract.class).getAll();
	}
	public void create(Contract contract) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.CONTRACT).checkCreate(contract);
		DAOFactory.getInstance(Contract.class).create(contract);
	}
	public void update(Contract contract) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.CONTRACT).checkUpdate(contract);
		DAOFactory.getInstance(Contract.class).update(contract);
	}
	public void delete(Contract contract) {
		DAOFactory.getInstance(Contract.class).delete(contract);
	}
    public List<Contract> getAllBy(int agencyId){
        AgencyService agencyService = new AgencyService();
        return DAOFactory.getInstance(Contract.class).getAll().stream().filter(p->p.getStartAgency().equals(agencyService.get(agencyId)) || p.getEndAgency().equals(agencyService.get(agencyId))).collect(Collectors.toList());
    }
}
