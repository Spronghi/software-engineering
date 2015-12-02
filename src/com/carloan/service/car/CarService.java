package com.carloan.service.car;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Status;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.contract.ContractService;
import com.carloan.service.exception.CommonServiceException;

public enum CarService{
	INSTANCE;
	public static List<Car> getAll() {
		return DAOFactory.getInstance(Car.class).getAll();
	}
	public static Car get(int id){
		return DAOFactory.getInstance(Car.class).get(id);	
	}
	public static void create(Car car) throws CommonServiceException {
		CarChecker.checkCreate(car);
		DAOFactory.getInstance(Car.class).create(car);
	}
	public static void update(Car car) throws CommonServiceException {
		CarChecker.checkUpdate(car);
		DAOFactory.getInstance(Car.class).update(car);
	}
	public static void delete(Car car) {
		DAOFactory.getInstance(Car.class).delete(car);
	}
	public static List<Status> getAllStatus(){
		return StatusService.getAll();
	}
	public static Status getStatus(int id){
		return StatusService.get(id);
	}
	public static List<Car> getAvaibleByAgency(int agencyID){
		return getAll().stream().filter(p->p.getAgency().getId().equals(agencyID) && p.getStatus().get().equals("avaible")).collect(Collectors.toList());
	}
	public static boolean isAvaible(Car car, LocalDate startDate, LocalDate endDate){
		List<Contract> contracts = ContractService.getAll().stream()
				.filter(p-> (p.getStartDate().isEqual(startDate) || p.getStartDate().isAfter(startDate)) &&
							(p.getEndDate().isEqual(endDate) || p.getEndDate().isBefore(endDate)))
				.collect(Collectors.toList());
		contracts.stream().forEach(p->System.out.println(p.toString()));
		for(Contract contract : contracts)
			if(contract.getCar().getId().equals(car.getId()))
				return false;
		return true;
	}
}
