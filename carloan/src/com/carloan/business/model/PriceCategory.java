package com.carloan.business.model;

public class PriceCategory {
	private int id;
	private String category;
	private double baseRateLimit;
	private double baseRateUnlimit;
	private double kmRate;
	

	public int getId() {
		return id;
	}
	public String get() {
		return category;
	}
	public double getBaseRateLimit() {
		return baseRateLimit;
	}
	public double getBaseRateUnlimit() {
		return baseRateUnlimit;
	}
	public double getKmRate() {
		return kmRate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void set(String category) {
		this.category = category;
	}
	public void setBaseRateLimit(double baseRateLimit) {
		this.baseRateLimit = baseRateLimit;
	}
	public void setBaseRateUnlimit(double baseRateUnlimit) {
		this.baseRateUnlimit = baseRateUnlimit;
	}
	public void setKmRate(double kmRate) {
		this.kmRate = kmRate;
	}
    @Override
	public String toString() {
		return "PriceCategory [id=" + id + ", category=" + category
				+ ", baseRateLimit=" + baseRateLimit + ", baseRateUnlimit="
				+ baseRateUnlimit + ", kmRate=" + kmRate + "]";
	}
	public String endUserString() {
		return category;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof PriceCategory)) {
            return false;
        }
		PriceCategory other = (PriceCategory) obj;
		if (category != other.category) {
            return false;
        }
		return true;
	}
}
