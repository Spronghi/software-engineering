package com.carloan.presentation.controller.internal;

import java.util.Set;

import javafx.scene.control.TableView;

public interface RegisterService extends Iterable<TableView<?>>{
	public <T> TableView<T> getTable(String key);
	public Set<String> getCategories();
}
