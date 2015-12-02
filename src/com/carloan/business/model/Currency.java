package com.carloan.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Currency implements Model{
	private static final long serialVersionUID = -5161085850116598027L;
	private IntegerProperty id;
	private StringProperty currency;
	private StringProperty symbol;
	public Currency(){
		this.id = new SimpleIntegerProperty(0);
		this.currency=new SimpleStringProperty();
		this.symbol=new SimpleStringProperty();
	}
	public Currency(String currency, String symbol){
		this.id = new SimpleIntegerProperty();
		this.currency=new SimpleStringProperty(currency);
		this.symbol=new SimpleStringProperty(symbol);
	}
	public Currency(int id, String currency, String symbol){
		this.id=new SimpleIntegerProperty(id);
		this.currency=new SimpleStringProperty(currency);
		this.symbol=new SimpleStringProperty(symbol);
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public Integer getId(){
		return id.get();
	}
	public String get(){
		return currency.get();
	}
	public String getSymbol(){
		return symbol.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public StringProperty currencyProperty(){
		return currency;
	}
	public StringProperty symbolProperty(){
		return symbol;
	}
	public StringProperty endUserStringProperty() {
		return currency;
	}
	public static double toEuro(Payment payment){
		if(payment.getCurrency().get().equals("dollar")){
			return payment.getAmount() * 0.9433;
		} else if (payment.getCurrency().get().equals("dollar")){
			return payment.getAmount() * 1.4187;
		} else {
			return payment.getAmount();
		}
	}
}
