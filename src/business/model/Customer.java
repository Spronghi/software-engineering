package business.model;

public class Customer extends Person {
	private String telephone;
	private int id;
	
	public Customer(String firstName,String lastName,String telephone,String email) {
		super(firstName, lastName, email);
		this.telephone = telephone;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Override
	public String toString() {
		return "Customer [firstName="+ firstName+", lastName="+lastName+
				", telephone=" + telephone +", email="+email+"]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
