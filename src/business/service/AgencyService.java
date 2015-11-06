package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Agency;

class AgencyService extends GenericService<Agency> {
	GenericDAO<Agency> agencyDAO;
	public AgencyService() {
		agencyDAO = DAOFactory.getInstance("AgencyDAO");
	}

	@Override
	public List<Agency> getAll() {
		return agencyDAO.getAll();
	}

	@Override
	public void add(Agency model) {
		// TODO Auto-generated method stub
		agencyDAO.add(model);
	}

	@Override
	public void edit(Agency model) {
		// TODO Auto-generated method stub
		agencyDAO.edit(model);
	}

	@Override
	public void delete(Agency model) {
		// TODO Auto-generated method stub
		agencyDAO.delete(model);
	}

}
