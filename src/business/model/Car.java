package business.model;

public class Car{
	private int id;
	private String model;
	private String licensePlate;
	private int km;
	private char category;
	private Agency agency;
	private String status;

	public Car(String model, String licensePlate, int km, char category,
			Agency agency, String status) {
		super();
		this.model = model;
		this.licensePlate = licensePlate;
		this.km = km;
		this.category = category;
		this.agency = agency;
		this.status = status;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
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
	public char getCategory() {
		return category;
	}
	public Agency getAgency() {
		return agency;
	}
	public String getStatus() {
		return status;
	}
	public void setModel(String name) {
		this.model = name;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public void setCategory(char category) {
		this.category = category;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isAvaible(){
		return status.equals("avaible");
	}
	@Override
	public String toString() {
		return "Car [model=" + model + ", licensePlate="
				+ licensePlate + ", km=" + km + ", category=" + category
				+ ", agency=" + agency.toString() + ", status=" + status + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Car))
			return false;
		Car other = (Car) obj;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		return true;
	}
}
