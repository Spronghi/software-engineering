package com.carloan.service.check;

public class CheckerFactory {
    @SuppressWarnings("unchecked")
	public static <T> Checker<T> getInstance(String key){
        return (Checker<T>) CheckerRegister.getChecker(key);
    }
}
