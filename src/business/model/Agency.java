package business.model;

public class Agency{
	private int number;
	private String name;
	private Location location;
	public Agency(String name, Location location) {
		super();
		this.name = name;
		this.location = location;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number= number;
	}
	public String getName() {
		return name;
	}
	public Location getLocation() {
		return location;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "Agency [number=" + number + ", name=" + name
				+ ", " + location.toString() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Agency))
			return false;
		Agency other = (Agency) obj;
		if (number != other.number)
			return false;
		return true;
	}
	
}
