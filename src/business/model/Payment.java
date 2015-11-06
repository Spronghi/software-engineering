package business.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Payment {
	private int id;
	private double amount;
	private LocalDate date;
	private LocalTime time;
	private String type;
	private String currency;
	private char symbol;

	public Payment(double amount,LocalDate date,LocalTime time,String type, String currency, char symbol) {
		this.amount = amount;
		this.date = date;
		this.time = time;
		this.type = type;
		this.currency = currency;
		this.symbol = symbol;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public char getSymbol() {
		return symbol;
	}
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", date=" + date.toString()
				+" "+time.toString()+ ", type=" + type + ", currency=" + currency +
				", symbol="+ symbol + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Payment))
			return false;
		Payment other = (Payment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
