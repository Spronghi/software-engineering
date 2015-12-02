package com.carloan.presentation.controller.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.TableView;

final public class TableViewAdminRegister extends TableViewRegister {
	private Map<String, TableView<?>> tableRegister;
	
	public TableViewAdminRegister(){
		tableRegister = new HashMap<>();
		tableRegister.put("Agency", setAgencyTable());
		tableRegister.put("Car", setCarTable());
		tableRegister.put("Contract", setContractTable());
		tableRegister.put("Customer", setCustomerTable());
		tableRegister.put("Operator", setOperatorTable());
		tableRegister.put("Location", setLocationTable());
		tableRegister.put("Price Category", setPriceCategoryTable());	
	}
	
	@SuppressWarnings("unchecked")
	public <T> TableView<T> getTable(String key){
		return (TableView<T>) tableRegister.get(key);
	}
	public Iterator<TableView<?>> iterator(){
		return tableRegister.values().iterator();
	}
	public Set<String> getCategories(){
		return tableRegister.keySet();
	}
}