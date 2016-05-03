package com.carloan.business.model;

public class Customer extends Person {
	private String telephone;
	private int id;

	public String getTelephone() {
		return telephone;
	}
	public int getId() {
		return id;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Customer [telephone=" + telephone + ", id=" + id + "]";
	}
	public String endUserString(){
		return getFirstName()+" "+getLastName();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Customer)) {
            return false;
        }
		Customer other = (Customer) obj;
		if (id != other.id) {
            return false;
        }
		return true;
	}
	
}
