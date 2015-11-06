package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Location;

class LocationService extends GenericService<Location> {
	GenericDAO<Location> locationDAO;
	public LocationService() {
		locationDAO = DAOFactory.getInstance("LocationDAO");
	}
	@Override
	public List<Location> getAll() {
		return locationDAO.getAll();
	}
	@Override
	public void add(Location model) {
		locationDAO.add(model);
	}
	@Override
	public void edit(Location model) {
		locationDAO.edit(model);
	}
	@Override
	public void delete(Location model) {
		locationDAO.delete(model);
	}
}
