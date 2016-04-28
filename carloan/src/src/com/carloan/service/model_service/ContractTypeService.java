package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.ContractType;
import com.carloan.integration.dao.DAOFactory;

class ContractTypeService implements ModelService<ContractType> {
    public List<ContractType> getAll() {
		return DAOFactory.getInstance(ContractType.class).getAll();
	}
	public ContractType get(int id) {
		return DAOFactory.getInstance(ContractType.class).get(id);
	}
	public void create(ContractType type) {
		DAOFactory.getInstance(ContractType.class).create(type);
	}

	public void update(ContractType type) {
		DAOFactory.getInstance(ContractType.class).update(type);
	}

	public void delete(ContractType type) {
		DAOFactory.getInstance(ContractType.class).delete(type);
	}
}
