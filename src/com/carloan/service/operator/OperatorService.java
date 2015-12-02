package com.carloan.service.operator;

import java.util.List;

import com.carloan.business.model.Operator;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.exception.OperatorNotFoundException;

public enum OperatorService {
	INSTANCE;
	public static List<Operator> getAll() {
		return DAOFactory.getInstance(Operator.class).getAll();
	}
	public static Operator get(int id) {
		return DAOFactory.getInstance(Operator.class).get(id);
	}
	public static boolean create(Operator operator) throws CommonServiceException {
		OperatorChecker.checkUpdate(operator);
		DAOFactory.getInstance(Operator.class).create(operator);
		return true;
	}
	public static boolean update(Operator operator) throws CommonServiceException {
		OperatorChecker.checkUpdate(operator);
		DAOFactory.getInstance(Operator.class).update(operator);
		return true;
	}
	public static void delete(Operator operator) {
		DAOFactory.getInstance(Operator.class).delete(operator);
	}
	public static Operator get(String username, String password) throws CommonServiceException{
		OperatorChecker.checkLogin(username, password);
		return DAOFactory.getInstance(Operator.class).getAll().stream()
				.filter(p->p.getUsername().equals(username) && p.getPassword().equals(password))
				.findFirst().orElseThrow(() -> new OperatorNotFoundException("Operator not Found"));
	}
}
