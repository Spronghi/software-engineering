package interation.dao;

import java.util.List;

public abstract class GenericDAO<T> {
	public abstract List<T> getAll();
	public abstract void add(T model);
	public abstract void edit(T model);
	public abstract void delete(T model);
}
