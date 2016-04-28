package com.carloan.business.model;

import java.time.LocalDate;

import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;


public class Contract {
	private int id;
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
	private ContractType type;

	public Contract() {
        open=true;
        this.operator = new Operator();
        this.customer = new Customer();
        this.startAgency = new Agency();
        this.endAgency = new Agency();
        this.car = new Car();
        this.type = new ContractType();
	}

	public int getId() { return id; }
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
	public String getOpen(){
        if(isOpen()) {
            return "Open";
        }else {
            return "Closed";
        }
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
	public ContractType getType() {
		return type;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setType(ContractType type) {
		this.type = type;
	}
	public double getDownAmount(){
        return getAmount() / 3;
	}
    public double getAmount() {
        return getAmountByKm(this, km);
    }
    public static double getAmountByKm(Contract contract, int km){
        PriceCategory category = contract.getCar().getCategory();
        double baseRate = category.getBaseRateUnlimit();
        if(contract.isKmLimit()) {
            baseRate = category.getBaseRateLimit();
        }

        if(!contract.getType().get().equals("one-day pass")) {
            baseRate = ((baseRate / 100) * 80) * 7;
        }
        double kmMoney=0;
        if(contract.isKmLimit()) {
            kmMoney = km * category.getKmRate();
        }
        LocalDate startDate = contract.getStartDate();
        double result=0;
        while(startDate.isBefore(contract.getEndDate())){
            if(contract.getType().get().equals("one-day pass")) {
                startDate = startDate.plusDays(1);
            } else if(contract.getType().get().equals("one-week pass")) {
                startDate = startDate.plusWeeks(1);
            }
            result += baseRate;
        }
        return result + kmMoney;
    }
	public double getAmountPaid(){
		double amountPaid = 0;
		for(Payment p : ServiceFactory.getModelFactory().<Payment>getOrderedInstance(ServiceControl.PAYMENT).getAllBy(getId())) {
            amountPaid = amountPaid + Currency.toEuro(p);
        }
		return amountPaid;
	}

    @Override
    public String toString() {
        return "Contract{" + Double.toString(getAmountByKm(this,getKm()))+" "+
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", km=" + km +
                ", kmLimit=" + kmLimit +
                ", open=" + open +
                ", operator=" + operator +
                ", customer=" + customer +
                ", startAgency=" + startAgency +
                ", endAgency=" + endAgency +
                ", car=" + car +
                ", type=" + type +
                '}';
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (!(obj instanceof Contract)) {
            return false;
        }
		Contract other = (Contract) obj;
		if (id == 0) {
			if (other.id != 0) {
                return false;
            }
		} else if (!(this.id==other.id)) {
            return false;
        }
		return true;
	}
}
