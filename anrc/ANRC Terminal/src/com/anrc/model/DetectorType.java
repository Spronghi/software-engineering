package com.anrc.model;

public class DetectorType {
	private int id;
	private String type;
	private float minValue;
	private float maxValue;
	
	public DetectorType(){}
	public DetectorType(int id, String type, float minValue, float maxValue) {
		super();
		this.id = id;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getMinValue() {
		return minValue;
	}
	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}
	public float getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	
	
}
