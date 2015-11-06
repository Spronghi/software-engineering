package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Contract;

class ContractService extends GenericService<Contract> {
	GenericDAO<Contract> contractDAO;
	public ContractService(){
		contractDAO = DAOFactory.getInstance("ContractDAO");
	}
	@Override
	public List<Contract> getAll() {
		// TODO Auto-generated method stub
		return contractDAO.getAll();
	}

	@Override
	public void add(Contract model) {
		// TODO Auto-generated method stub
		contractDAO.add(model);
	}

	@Override
	public void edit(Contract model) {
		// TODO Auto-generated method stub
		contractDAO.edit(model);
	}

	@Override
	public void delete(Contract model) {
		// TODO Auto-generated method stub
		contractDAO.delete(model);
	}

}
