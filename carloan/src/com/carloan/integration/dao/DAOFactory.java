package com.carloan.integration.dao;

public class DAOFactory {
	@SuppressWarnings("unchecked")
	public static <T> DAO<T> getInstance(Class<T> clazz){
		return (DAO<T>) DAORegisterService.getDAO(clazz);
	}
}
