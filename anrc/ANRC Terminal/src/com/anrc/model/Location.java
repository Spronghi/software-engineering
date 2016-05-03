package com.anrc.model;

public class Location {
	private int id;
	private String city;
	private String road;
	private String postalNo;
	
	public Location(){}
	public Location(int id, String city, String road, String postalNo) {
		super();
		this.id = id;
		this.city = city;
		this.road = road;
		this.postalNo = postalNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getPostalNo() {
		return postalNo;
	}
	public void setPostalNo(String postalNo) {
		this.postalNo = postalNo;
	}
}
