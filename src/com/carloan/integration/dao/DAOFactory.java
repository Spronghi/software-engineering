package com.carloan.integration.dao;

public enum DAOFactory {
	INSTANCE;
	@SuppressWarnings("unchecked")
	public static <T> DAO<T> getInstance(Class<T> clazz){
		return (DAO<T>) RegisterService.getDAO(clazz);
	}
}
