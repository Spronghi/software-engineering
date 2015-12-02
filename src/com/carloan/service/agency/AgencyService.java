package com.carloan.service.agency;

import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

public enum AgencyService{
	INSTANCE;
	public static List<Agency> getAll() {
		return DAOFactory.getInstance(Agency.class).getAll();
	}
	public static Agency get(int id) {
		return DAOFactory.getInstance(Agency.class).get(id);
	}
	public static void create(Agency agency) throws CommonServiceException {
		AgencyChecker.check(agency);
		DAOFactory.getInstance(Agency.class).create(agency);
	}
	public static void update(Agency agency) throws CommonServiceException {
		AgencyChecker.check(agency);
		DAOFactory.getInstance(Agency.class).update(agency);
	}
	public static void delete(Agency agency) {
		DAOFactory.getInstance(Agency.class).delete(agency);
	}
}
