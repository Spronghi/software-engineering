package com.carloan.business.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Payment {
	private int id;
	private double amount;
	private LocalDate date;
	private LocalTime time;
	private PaymentType type;
	private Currency currency;
	private Contract contract;

	public Payment() {
		this.type = new PaymentType();
		this.currency = new Currency();
		this.contract = new Contract();
	}
	
	public int getId() {
		return id;
	}
	public double getAmount() {
		return amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public LocalTime getTime() {
		return time;
	}
	public PaymentType getType() {
		return type;
	}
	public Currency getCurrency() {
		return currency;
	}
	public Contract getContract() {
		return contract;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String endUserString() {
		return getCurrency().getSymbol()+" "+getAmount();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Payment)) {
            return false;
        }
		Payment other = (Payment) obj;
		if (id != other.id) {
            return false;
        }
		return true;
	}
}
