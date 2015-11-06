package business.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Contract {
	private int contractNo;
	private LocalDate startDate;
	private LocalDate endDate;
	private int km;
	private boolean kmLimit;
	private boolean open;
	private Operator operator;
	private Customer customer;
	private Agency startAgency;
	private Agency endAgency;
	private Car car;
	private String type;
	private List<Payment> payments;
	private double amount;
	
	public Contract(LocalDate startDate, LocalDate endDate,
			int km, boolean kmLimit, boolean open, Operator operator,
			Customer customer, Agency startAgency, Agency endAgency, Car car,
			String type, List<Payment> payments, double amount) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.km = km;
		this.kmLimit = kmLimit;
		this.open = open;
		this.operator = operator;
		this.customer = customer;
		this.startAgency = startAgency;
		this.endAgency = endAgency;
		this.car = car;
		this.type = type;
		if(payments == null){
			this.payments = new ArrayList<>();
		} else {
			this.payments = payments;
		}
		this.amount=amount;
	}
	public int getContractNo(){
		return contractNo;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public int getKm() {
		return km;
	}
	public boolean isKmLimit() {
		return kmLimit;
	}
	public boolean isOpen() {
		return open;
	}
	public Operator getOperator() {
		return operator;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Agency getStartAgency() {
		return startAgency;
	}
	public Agency getEndAgency() {
		return endAgency;
	}
	public Car getCar() {
		return car;
	}
	public String getType() {
		return type;
	}
	public List<Payment> getPayments(){
		return payments;
	}
	public double getAmount(){
		return amount;
	}
	public double getTotalPayed() {
		double amount=0;
		for(Payment p : payments)
			amount += p.getAmount();
		return amount;	
	}
	public void setContractNo(int contractNo){
		this.contractNo=contractNo;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public void setKmLimit(boolean kmLimit) {
		this.kmLimit = kmLimit;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setStartAgency(Agency startAgency) {
		this.startAgency = startAgency;
	}
	public void setEndAgency(Agency endAgency) {
		this.endAgency = endAgency;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAmount(double amount){
		this.amount=amount;
	}
	@Override
	public String toString() {
		String str =  "Contract [contractNo=" + contractNo + ", startDate="
				+ startDate + ", endDate=" + endDate + ", km=" + km
				+ ", kmLimit=" + kmLimit + ", open=" + open + ", operator="
				+ operator + ", customer=" + customer + ", startAgency="
				+ startAgency + ", endAgency=" + endAgency + ", car=" + car
				+ ", type=" + type + ", amount="
				+ amount + ", totalPayed="+getTotalPayed()+"]\n";
		for(Payment p : payments){
			str += p.toString() + '\n'; 
		}
		return str;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Contract))
			return false;
		Contract other = (Contract) obj;
		if (contractNo == 0) {
			if (other.contractNo != 0)
				return false;
		} else if (!(this.contractNo==other.contractNo))
			return false;
		return true;
	}
	public void printPayments(){
		payments.stream().forEach(p -> System.out.println(p.toString()));
	}
}
