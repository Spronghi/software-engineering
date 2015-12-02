package com.carloan.presentation.controller.internal;

import java.util.HashMap;
import java.util.Map;

import com.carloan.service.agency.AgencyService;
import com.carloan.service.car.CarService;
import com.carloan.service.contract.ContractService;
import com.carloan.service.customer.CustomerService;
import com.carloan.service.location.LocationService;
import com.carloan.service.operator.OperatorService;
import com.carloan.service.price_category.PriceCategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class ObservableDataRegister {
	
	private Map<String,ObservableList<?>> container;
	public ObservableDataRegister(){
		container = new HashMap<>();
		container.put("Agency", FXCollections.observableArrayList(AgencyService.getAll()));
		container.put("Car", FXCollections.observableArrayList(CarService.getAll()));
		container.put("Contract", FXCollections.observableArrayList(ContractService.getAll()));
		container.put("Customer", FXCollections.observableArrayList(CustomerService.getAll()));
		container.put("Location", FXCollections.observableArrayList(LocationService.getAll()));
		container.put("Operator", FXCollections.observableArrayList(OperatorService.getAll()));
		container.put("PriceCategory", FXCollections.observableArrayList(PriceCategoryService.getAll()));
	}
	@SuppressWarnings("unchecked")
	public <T> ObservableList<T> get(String key) {
		return (ObservableList<T>) container.get(key);
	}
}
