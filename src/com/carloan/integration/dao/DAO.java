package com.carloan.integration.dao;

import java.util.List;

public abstract class DAO<T> {
	public abstract List<T> getAll();
	public abstract void create(T model);
	public abstract void update(T model);
	public abstract void delete(T model);
	public abstract T get(int id);
}
