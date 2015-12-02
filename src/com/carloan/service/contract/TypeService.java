package com.carloan.service.contract;

import java.util.List;

import com.carloan.business.model.ContractType;
import com.carloan.integration.dao.DAOFactory;

enum TypeService {
	INSTANCE;
	public static List<ContractType> getAll() {
		return DAOFactory.getInstance(ContractType.class).getAll();
	}
	public static ContractType get(int id) {
		return DAOFactory.getInstance(ContractType.class).get(id);
	}
	public static void create(ContractType type) {
		DAOFactory.getInstance(ContractType.class).create(type);
	}

	public static void update(ContractType type) {
		DAOFactory.getInstance(ContractType.class).update(type);
	}

	public static void delete(ContractType type) {
		DAOFactory.getInstance(ContractType.class).delete(type);
	}
}
