package com.carloan.business.model;


public class Car {
	private int id;
	private String model;
	private String licensePlate;
	private int km;
	private PriceCategory category;
	private Agency agency;
	private Status status;
	public Car() {
		super();
		category = new PriceCategory();
        agency = new Agency();
        status = new Status();
	}

	public int getId() {
		return id;
	}
	public String getModel() {
		return model;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public int getKm() {
		return km;
	}
	public PriceCategory getCategory() {
		return category;
	}
	public Agency getAgency() {
		return agency;
	}
	public Status getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public void setCategory(PriceCategory category) {
		this.category = category;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", licensePlate="
				+ licensePlate + ", km=" + km + ", category=" + category
				+ ", agency=" + agency + ", status=" + status + "]";
	}
	public String endUserString() {
		return model;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Car)) {
            return false;
        }
		Car other = (Car) obj;
		if (licensePlate == null) {
			if (other.licensePlate != null) {
                return false;
            }
		} else if (!licensePlate.equals(other.licensePlate)) {
            return false;
        }
		return true;
	}
}
