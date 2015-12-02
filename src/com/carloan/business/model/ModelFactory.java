package com.carloan.business.model;

public interface ModelFactory {
	public <T> T getInstance(String clazz);
}
