package com.carloan.presentation.controller.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.TableView;

final public class TableViewUserRegister extends TableViewRegister {
	private Map<String,TableView<?>> register;
	
	public TableViewUserRegister() {
		register = new HashMap<>();
		register.put("Car", setCarTable());
		register.put("Customer", setCustomerTable());
		register.put("Contract", setContractTable());
		register.put("Price Category", setPriceCategoryTable());
	}
	public <T> TableView<T> getTable(String key){
		return (TableView<T>) register.get(key);
	}
	public Iterator<TableView<?>> iterator(){
		return register.values().iterator();
	}
	public Set<String> getCategories(){
		return register.keySet();
	}
}
