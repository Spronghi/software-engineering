package com.anrc.integration.dao;

import java.util.List;

public interface DAO<T>{
	public List<T> getAll();
	public T get(int id);
	public void create(T model);
	public void update(T model);
	public void delete(T model);
}
