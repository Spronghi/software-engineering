package com.carloan.business.model;

public class Agency {
	private int id;
	private String name;
	private Location location;
	public Agency() {
		super();
        this.location = new Location();
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Location getLocation() {
		return location;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "Agency [id=" + id + ", name=" + name + ", location=" + location
				+ "]";
	}
	public String endUserString() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj.getClass().equals(Agency.class))) {
            return false;
        }
		Agency other = (Agency) obj;
		if (id != other.id) {
            return false;
        }
		return true;
	}

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}
