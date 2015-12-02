package com.carloan.business.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.carloan.integration.util.DoubleFormatter;
import com.carloan.service.payment.PaymentService;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contract implements Model{
	private static final long serialVersionUID = 7514669843109072532L;
	private IntegerProperty contractNo;
	private ObjectProperty<LocalDate> startDate;
	private ObjectProperty<LocalDate> endDate;
	private IntegerProperty km;
	private BooleanProperty kmLimit;
	private BooleanProperty open;
	private ObjectProperty<Operator> operator;
	private ObjectProperty<Customer> customer;
	private ObjectProperty<Agency> startAgency;
	private ObjectProperty<Agency> endAgency;
	private ObjectProperty<Car> car;
	private ObjectProperty<ContractType> type;

	public Contract() {
		this.contractNo = new SimpleIntegerProperty(0);
		this.startDate = new SimpleObjectProperty<>();
		this.endDate = new SimpleObjectProperty<>();
		this.km = new SimpleIntegerProperty(0);
		this.kmLimit = new SimpleBooleanProperty();
		this.open = new SimpleBooleanProperty(true);
		this.operator = new SimpleObjectProperty<>();
		this.customer = new SimpleObjectProperty<>();
		this.startAgency = new SimpleObjectProperty<>();
		this.endAgency = new SimpleObjectProperty<>();
		this.car = new SimpleObjectProperty<>();
		this.type = new SimpleObjectProperty<>();
	}
	public Contract(LocalDate startDate, LocalDate endDate,
			int km, boolean kmLimit, boolean open, Operator operator,
			Customer customer, Agency startAgency, Agency endAgency, Car car,
			ContractType type) {
		this.contractNo = new SimpleIntegerProperty();
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.endDate = new SimpleObjectProperty<>(endDate);
		this.km = new SimpleIntegerProperty(km);
		this.kmLimit = new SimpleBooleanProperty(kmLimit);
		this.open = new SimpleBooleanProperty(open);
		this.operator = new SimpleObjectProperty<>(operator);
		this.customer = new SimpleObjectProperty<>(customer);
		this.startAgency = new SimpleObjectProperty<Agency>(startAgency);
		this.endAgency = new SimpleObjectProperty<Agency>(endAgency);
		this.car = new SimpleObjectProperty<>(car);
		this.type = new SimpleObjectProperty<>(type);
	}
	public Integer getId(){
		return contractNo.get();
	}
	public int getContractNo(){
		return contractNo.get();
	}
	public LocalDate getStartDate() {
		return startDate.get();
	}
	public LocalDate getEndDate() {
		return endDate.get();
	}
	public int getKm() {
		return km.get();
	}
	public boolean isKmLimit() {
		return kmLimit.get();
	}
	public boolean isOpen() {
		return open.get();
	}
	public Operator getOperator() {
		return operator.get();
	}
	public Customer getCustomer() {
		return customer.get();
	}
	public Agency getStartAgency() {
		return startAgency.get();
	}
	public Agency getEndAgency() {
		return endAgency.get();
	}
	public Car getCar() {
		return car.get();
	}
	public ContractType getType() {
		return type.get();
	}
	public double getAmount(){
		PriceCategory category = car.get().getCategory();
		double baseRate = isKmLimit() ? category.getBaseRateLimit() : category.getBaseRateUnlimit();
		baseRate = (getType().get().equals("one-day pass")) ? baseRate : (((baseRate/100)*80)*7);
		double km = isKmLimit() ? getKm() * category.getKmRate() : 0;
		LocalDate tmp = getStartDate();
		double result=0;
		while(tmp.isBefore(getEndDate())){
			tmp = getType().get().equals("one-day pass") ? tmp.plusDays(1) : tmp.plusWeeks(1);
			result += baseRate;
		}
		return result + km;
	}
	public double getAmountPaid(){
		double amountPaid = 0;
		for(Payment p : PaymentService.getPaymentsByContract(this))
			amountPaid = amountPaid + Currency.toEuro(p);
		return amountPaid;
	}
	public void setContractNo(int contractNo){
		this.contractNo.set(Integer.valueOf(contractNo));
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate.set(startDate);
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate.set(endDate);
	}
	public void setKm(int km) {
		this.km.set(km);
	}
	public void setKmLimit(boolean kmLimit) {
		this.kmLimit.set(kmLimit);
	}
	public void setOpen(boolean open) {
		this.open.set(open);
	}
	public void setOperator(Operator operator) {
		this.operator.set(operator);
	}
	public void setCustomer(Customer customer) {
		this.customer.set(customer);
	}
	public void setStartAgency(Agency startAgency) {
		this.startAgency.set(startAgency);
	}
	public void setEndAgency(Agency endAgency) {
		this.endAgency.set(endAgency);
	}
	public void setCar(Car car) {
		this.car.set(car);
	}
	public void setType(ContractType type) {
		this.type.set(type);
	}
	public IntegerProperty idProperty(){
		return contractNo;
	}
	public IntegerProperty contractNoProperty(){
		return contractNo;
	}
	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}
	public StringProperty formattedStartDateProperty() {
		return new SimpleStringProperty(startDate.get().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}
	public StringProperty formattedEndDateProperty() {
		return new SimpleStringProperty(endDate.get().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	public IntegerProperty kmProperty() {
		return km;
	}
	public BooleanProperty kmLimitProperty() {
		return kmLimit;
	}
	public BooleanProperty openProperty() {
		return open;
	}
	public ObjectProperty<Operator> operatorProperty() {
		return operator;
	}
	public ObjectProperty<Customer> customerProperty() {
		return customer;
	}
	public ObjectProperty<Agency> startAgencyProperty() {
		return startAgency;
	}
	public ObjectProperty<Agency> endAgencyProperty() {
		return endAgency;
	}
	public ObjectProperty<Car> carProperty(){
		return car;
	}
	public ObjectProperty<ContractType> typeProperty() {
		return type;
	}
	public StringProperty endUserAmountProperty(){
		return new SimpleStringProperty("€ "+DoubleFormatter.toString(getAmount()));
	}
	public StringProperty endUserOpenProperty() {
		return isOpen() ? new SimpleStringProperty("Open") : new SimpleStringProperty("Closed");
	}
	public StringProperty endUserStringProperty() {
		return new SimpleStringProperty(Integer.toString(getContractNo()));
	}
	@Override
	public String toString() {
		String str =  "Contract [contractNo=" + contractNo.get()+ ", startDate="
				+ startDate.get() + ", endDate=" + endDate.get() + ", km=" + km.get()
				+ ", kmLimit=" + kmLimit.get() + ", open=" + open.get() + ", operator="
				+ operator.get() + ", customer=" + customer.get() + ", startAgency="
				+ startAgency.get() + ", endAgency=" + endAgency.get() + ", car=" + car.get()
				+ ", type=" + type.get() + ", amount="
				+ getAmount()+"]\n";
		for(Payment p : PaymentService.getPaymentsByContract(this)){
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
		if (contractNo.get() == 0) {
			if (other.contractNo.get() != 0)
				return false;
		} else if (!(this.contractNo==other.contractNo))
			return false;
		return true;
	}
}
