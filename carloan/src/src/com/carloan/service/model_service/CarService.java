package com.carloan.service.model_service;

import java.util.List;
import java.util.stream.Collectors;

import com.carloan.business.model.Car;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class CarService implements OrderedService<Car> {
	public List<Car> getAll() {
		return DAOFactory.getInstance(Car.class).getAll();
	}
	public Car get(int id){
		return DAOFactory.getInstance(Car.class).get(id);	
	}
	public void create(Car car) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.CAR).checkCreate(car);
		DAOFactory.getInstance(Car.class).create(car);
	}
	public void update(Car car) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.CAR).checkUpdate(car);
		DAOFactory.getInstance(Car.class).update(car);
	}
	public void delete(Car car) {
		DAOFactory.getInstance(Car.class).delete(car);
	}
	public List<Car> getAllBy(int agencyID){
		return getAll().stream().filter(p->p.getAgency().getId() == agencyID).collect(Collectors.toList());
	}
}
