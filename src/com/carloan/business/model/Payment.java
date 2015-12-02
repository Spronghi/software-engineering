package com.carloan.business.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment implements Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5283237159827834534L;
	private IntegerProperty id;
	private DoubleProperty amount;
	private ObjectProperty<LocalDate> date;
	private ObjectProperty<LocalTime> time;
	private ObjectProperty<PaymentType> type;
	private ObjectProperty<Currency> currency;
	private ObjectProperty<Contract> contract;

	public Payment() {
		this.id = new SimpleIntegerProperty(0);
		this.amount = new SimpleDoubleProperty();
		this.date = new  SimpleObjectProperty<>();
		this.time = new  SimpleObjectProperty<>();
		this.type = new SimpleObjectProperty<>();
		this.currency = new SimpleObjectProperty<>();
		this.contract = new SimpleObjectProperty<>();
	}
	public Payment(double amount,LocalDate date,LocalTime time,PaymentType type, Currency currency, Contract contract) {
		this.id = new SimpleIntegerProperty(0);
		this.amount = new SimpleDoubleProperty(amount);
		this.date = new  SimpleObjectProperty<>(date);
		this.time = new  SimpleObjectProperty<>(time);
		this.type = new SimpleObjectProperty<>(type);
		this.currency = new SimpleObjectProperty<>(currency);
		this.contract = new SimpleObjectProperty<>(contract);
	}
	public Integer getId(){
		return id.get();
	}
	public PaymentType getType() {
		return type.get();
	}
	public Currency getCurrency() {
		return currency.get();
	}
	public double getAmount() {
		return amount.get();
	}
	public LocalDate getDate() {
		return date.get();
	}
	public LocalTime getTime() {
		return time.get();
	}
	public Contract getContract(){
		return contract.get();
	}
	public void setId(int id){
		this.id.set(Integer.valueOf(id));
	}
	public void setType(PaymentType type) {
		this.type.set(type);
	}
	public void setCurrency(Currency currency) {
		this.currency.set(currency);
	}
	public void setAmount(double amount) {
		this.amount.set(amount);
	}
	public void setDate(LocalDate date) {
		this.date.set(date);
	}
	public void setTime(LocalTime time) {
		this.time.set(time);
	}
	public void setContract(Contract contract){
		this.contract.set(contract);
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public ObjectProperty<PaymentType> typeProperty() {
		return type;
	}
	public ObjectProperty<Currency> currencyProperty() {
		return currency;
	}
	public DoubleProperty amountProperty() {
		return amount;
	}
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
	public StringProperty formattedDateProperty() {
		return new SimpleStringProperty(date.get().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	public ObjectProperty<LocalTime> timeProperty() {
		return time;
	}
	public ObjectProperty<Contract> contractProperty(){
		return contract;
	}
	public StringProperty endUserStringProperty() {
		String string = getCurrency().getSymbol()+" "+Double.toString(getAmount());
		return new SimpleStringProperty(string);
	}
	@Override
	public String toString() {
		return "Payment [id=" + id.get() + ", amount=" + amount.get() + ", date=" + date.toString()
				+" "+time.toString()+ ", type=" + type.get().get() + ", " + currency.get() + "]";
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
