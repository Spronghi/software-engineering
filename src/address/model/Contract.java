package address.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contract {
	private final int id;
	private final IntegerProperty contractNo;
	private final StringProperty startDate;
	private final StringProperty returnLimitDate;
	private final StringProperty endDate;
	//private final ObjectProperty<LocalDate> startDate;
	//private final ObjectProperty<LocalDate> returnLimitDate;
	//private final ObjectProperty<LocalDate> endDate;
	private final BooleanProperty kmLimit;
	private final IntegerProperty endKm;
	private final ObjectProperty<Customer> customer;
	private final ObjectProperty<Car> car;
	private final ObjectProperty<Agency> agency;
	private final ObjectProperty<Agency> returnAgency;
	private final StringProperty contractType;
	//private final ObjectProperty<Payment> totalPayment;
	//private final ObjectProperty<Payment> deposit;
	
	public Contract(int id, int contractNo, String startDate,
			String returnLimitDate, String endDate, boolean kmLimit,
			int endKm, Customer customer, Car car, Agency agency, Agency returnAgency, 
			String contractType) {
		this.id = id;
		this.contractNo = new SimpleIntegerProperty(contractNo);
		this.startDate = new SimpleStringProperty(startDate);
		this.returnLimitDate = new SimpleStringProperty(returnLimitDate);
		this.endDate = new SimpleStringProperty(endDate);
		//this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
		//this.returnLimitDate = new SimpleObjectProperty<LocalDate>(returnLimitDate);
		//this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
		this.kmLimit = new SimpleBooleanProperty(kmLimit);
		this.endKm = new SimpleIntegerProperty(endKm);
		this.customer = new SimpleObjectProperty<Customer>(customer);
		this.car = new SimpleObjectProperty<Car>(car);
		this.agency = new SimpleObjectProperty<Agency>(agency);
		this.returnAgency = new SimpleObjectProperty<Agency>(returnAgency);
		this.contractType = new SimpleStringProperty(contractType);
	}

	public int getId() {
		return id;
	}

	public Integer getContractNo() {
		return contractNo.get();
	}

	public String getStartDate() {
		return startDate.get();
	}

	public String getReturnLimitDate() {
		return returnLimitDate.get();
	}

	public String getEndDate() {
		return endDate.get();
	}

	public boolean getKmLimit() {
		return kmLimit.get();
	}

	public int getEndKm() {
		return endKm.get();
	}
	public Customer getCustomer() {
		return customer.get();
	}
	public Car getCar() {
		return car.get();
	}

	public Agency getAgency() {
		return agency.get();
	}

	public Agency getReturnAgency() {
		return returnAgency.get();
	}

	public String getContractType() {
		return contractType.get();
	}

}