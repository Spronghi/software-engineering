package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Operator;

class OperatorService extends GenericService<Operator> {
	GenericDAO<Operator> operatorDAO;
	public OperatorService() {
		operatorDAO = DAOFactory.getInstance("OperatorDAO");
	}

	@Override
	public List<Operator> getAll() {
		// TODO Auto-generated method stub
		return operatorDAO.getAll();
	}

	@Override
	public void add(Operator model) {
		// TODO Auto-generated method stub
		operatorDAO.add(model);
	}

	@Override
	public void edit(Operator model) {
		// TODO Auto-generated method stub
		operatorDAO.edit(model);
	}

	@Override
	public void delete(Operator model) {
		// TODO Auto-generated method stub
		operatorDAO.delete(model);
	}

}
