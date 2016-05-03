package com.anrc.model;

public class Detector {
	private int id;
	private String name;
	private DetectorType type;
	private Room room;
	
	public Detector(){}
	public Detector(int id, String name, DetectorType type, Room room) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.room = room;
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
	public DetectorType getType() {
		return type;
	}
	public void setType(DetectorType type) {
		this.type = type;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
}
