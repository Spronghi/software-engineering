package com.carloan.service.location;

import java.util.List;

import com.carloan.business.model.Location;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

public enum LocationService {
	INSTANCE;
	public static List<Location> getAll() {
		return DAOFactory.getInstance(Location.class).getAll();
	}
	public static void create(Location location) throws CommonServiceException {
		LocationChecker.check(location);
		DAOFactory.getInstance(Location.class).create(location);
	}
	public static void update(Location location) throws CommonServiceException {
		LocationChecker.check(location);
		DAOFactory.getInstance(Location.class).update(location);
	}
	public static void delete(Location location) {
		DAOFactory.getInstance(Location.class).delete(location);
	}
}
