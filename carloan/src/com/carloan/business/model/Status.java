package com.carloan.business.model;

public class Status {
	private int id;
	private String status;
	
	public int getId() {
		return id;
	}
	public String get() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void set(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Status [id=" + id + ", status=" + status + "]";
	}
}
