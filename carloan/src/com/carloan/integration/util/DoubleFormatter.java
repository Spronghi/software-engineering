package com.carloan.integration.util;

public enum DoubleFormatter {
	INSTANCE;
	public static String toString(double num){
		return String.format("%.2f", num);
	}
}
