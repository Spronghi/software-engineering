package com.carloan.business.model;

public abstract class Type {
	private int id;
	private String type;

    protected Type() {}
	public int getId() {
		return id;
	}

	public String get() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void set(String type) {
		this.type = type;
	}
	public String endUserString() {
		return type;
	}
}
