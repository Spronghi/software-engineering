package com.carloan.service.contract;

import java.util.List;

import com.carloan.business.model.Contract;
import com.carloan.business.model.ContractType;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

public enum ContractService{
	INSTANCE;
	public static List<Contract> getAll() {
		return DAOFactory.getInstance(Contract.class).getAll();
	}
	public static void create(Contract contract) throws CommonServiceException {
		ContractChecker.checkCreate(contract);
		DAOFactory.getInstance(Contract.class).create(contract);
	}
	public static void update(Contract contract) throws CommonServiceException {
		ContractChecker.checkUpdate(contract);
		DAOFactory.getInstance(Contract.class).update(contract);
	}
	public static void delete(Contract contract) {
		DAOFactory.getInstance(Contract.class).delete(contract);
	}
	public static List<ContractType> getTypes(){
		return TypeService.getAll();
	}
	public static ContractType getType(int id){
		return TypeService.get(id);
	}
}
