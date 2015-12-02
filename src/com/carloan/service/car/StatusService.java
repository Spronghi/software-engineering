package com.carloan.service.car;

import java.util.List;

import com.carloan.business.model.Status;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

enum StatusService {
	INSTANCE;
	public static List<Status> getAll() {
		return DAOFactory.getInstance(Status.class).getAll();
	}
	public static Status get(int id) {
		return DAOFactory.getInstance(Status.class).get(id);
	}
	public static void create(Status status) throws CommonServiceException {
		DAOFactory.getInstance(Status.class).create(status);
	}
	public static void update(Status status) throws CommonServiceException {
		DAOFactory.getInstance(Status.class).update(status);
	}
	public static void delete(Status status) {
		DAOFactory.getInstance(Status.class).delete(status);
	}
}
