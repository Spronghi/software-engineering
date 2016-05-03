package com.anrc.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Survey {
	private int id;
	private float value;
	private LocalDate date;
	private LocalTime time;
	private Room room;
	
	public Survey(){}
	public Survey(int id, float value, LocalDate date, LocalTime time, Room room) {
		super();
		this.id = id;
		this.value = value;
		this.date = date;
		this.time = time;
		this.room = room;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
}
