package com.carloan.business.model;

public class Operator extends Person {
	private int id;
    private String username;
	private String password;
    private boolean admin;
    private Agency agency;

	public int getId(){
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
    public boolean isAdmin(){
        return admin;
    }
    public Agency getAgency() {return agency; }
	public final void setId(int id){
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    public void setAgency(Agency agency) { this.agency = agency; }
	public String endUserString() {
		return username;
	}
	@Override
	public String toString() {
		return "Operator [id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Operator)) {
            return false;
        }
		Operator other = (Operator) obj;
		if (username == null) {
			if (other.username != null) {
                return false;
            }
		} else if (!username.equals(other.username)) {
            return false;
        }
		return true;
	}
}
