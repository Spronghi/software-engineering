package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.Location;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class LocationService implements ModelService<Location> {
    public Location get(int id){
		return DAOFactory.getInstance(Location.class).get(id);
	}
	public List<Location> getAll() {
		return DAOFactory.getInstance(Location.class).getAll();
	}
	public void create(Location location) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.LOCATION).checkCreate(location);
        DAOFactory.getInstance(Location.class).create(location);
	}
	public void update(Location location) throws UpdateModelException     {
        CheckerFactory.getInstance(ServiceControl.LOCATION).checkUpdate(location);
		DAOFactory.getInstance(Location.class).update(location);
	}
	public void delete(Location location) {
		DAOFactory.getInstance(Location.class).delete(location);
	}
}
