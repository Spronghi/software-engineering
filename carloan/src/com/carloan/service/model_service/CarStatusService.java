package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.Status;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class CarStatusService implements ModelService<Status> {
	public List<Status> getAll() {
		return DAOFactory.getInstance(Status.class).getAll();
	}
	public Status get(int id) {
		return DAOFactory.getInstance(Status.class).get(id);
	}
	public void create(Status status) throws CreateModelException {
		DAOFactory.getInstance(Status.class).create(status);
	}
	public void update(Status status) throws UpdateModelException {
		DAOFactory.getInstance(Status.class).update(status);
	}
	public void delete(Status status) {
		DAOFactory.getInstance(Status.class).delete(status);
	}
}
