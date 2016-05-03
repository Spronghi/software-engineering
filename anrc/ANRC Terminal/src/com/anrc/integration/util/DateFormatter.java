package com.anrc.integration.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum DateFormatter {
	INSTANCE;
	public static String toString(String pattern, LocalDate date, LocalTime time){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = date.atTime(time);
		return dateTime.format(formatter);
	}
	public static String toString(String pattern, LocalDate date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}
}
