package business.service;

import java.util.List;

public abstract class GenericService<T> {
	public abstract List<T> getAll();
	public abstract void add(T model);
	public abstract void edit(T model);
	public abstract void delete(T model);
}
