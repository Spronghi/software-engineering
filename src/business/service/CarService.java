package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Car;

class CarService extends GenericService<Car> {
	GenericDAO<Car> carDAO;
	public CarService() {
		// TODO Auto-generated constructor stub
		carDAO= DAOFactory.getInstance("CarDAO");
	}

	@Override
	public List<Car> getAll() {
		// TODO Auto-generated method stub
		return carDAO.getAll();
	}

	@Override
	public void add(Car model) {
		// TODO Auto-generated method stub
		carDAO.add(model);
	}

	@Override
	public void edit(Car model) {
		// TODO Auto-generated method stub
		carDAO.edit(model);
	}

	@Override
	public void delete(Car model) {
		// TODO Auto-generated method stub
		carDAO.delete(model);
	}

}
