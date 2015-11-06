package business.model;

public class Operator extends Person{
	private int id;
	private String username;
	private String password;
	private boolean isAdmin;
	public Operator(String firstName, String lastName, String email,
			String username, String password){
		super(firstName, lastName, email);
		this.username = username;
		this.password = password;
		isAdmin=false;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin(){
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String toString() {
		return "Operator [firstName="+ firstName+", lastName="+lastName+
				", username=" + username +", password=" + password +", email="+email +"]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Operator))
			return false;
		Operator other = (Operator) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
