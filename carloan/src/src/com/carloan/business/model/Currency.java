package com.carloan.business.model;

public class Currency {
	private static final double DOLLAR_CURRENCY = 0.9433;
	private static final double POUND_CURRENCY = 1.4187;
	
	private int id;
	private String currency;
	private String symbol;
	public int getId() {
		return id;
	}
	public String get() {
		return currency;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void set(String currency) {
		this.currency = currency;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public static double toEuro(Payment payment){
		if(payment.getCurrency().get().equals("dollar")){
			return payment.getAmount() * DOLLAR_CURRENCY;
		} else if (payment.getCurrency().get().equals("pound")){
			return payment.getAmount() * POUND_CURRENCY;
		} else {
			return payment.getAmount();
		}
	}
}
