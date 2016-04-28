package com.carloan.business.model;

public class Location {
	private int id;
	private String city;
	private String postalNo;
	private String road;

	public int getId() {
		return id;
	}
	public String getCity() {
		return city;
	}
	public String getPostalNo() {
		return postalNo;
	}
	public String getRoad() {
		return road;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setPostalNo(String postalNo) {
		this.postalNo = postalNo;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	@Override
	public String toString() {
		return "Location [id=" + id + ", city=" + city + ", postalNo="
				+ postalNo + ", road=" + road + "]";
	}
	public String endUserString() {
		return getCity()+" "+getPostalNo()+" - "+getRoad();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Location)) {
            return false;
        }
		Location other = (Location) obj;
		if (id != other.id) {
            return false;
        }
		return true;
	}
}
