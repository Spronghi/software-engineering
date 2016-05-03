package com.anrc.model;

public class Room {
	private int id;
	private String name;
	private Place place;
	
	public Room(){}
	public Room(int id, String name, Place place) {
		super();
		this.id = id;
		this.name = name;
		this.place = place;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	
	
}
