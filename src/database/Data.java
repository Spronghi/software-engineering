package database;

public class Data {
	static CarData car = new CarData();
	static AgencyData agency = new AgencyData();
	static CustomerData customer = new CustomerData();
	static ContractData contract = new ContractData();


	public static CarDataInterface car(){
		return car;
	}
	public static AgencyDataInterface agency(){
		return agency;
	}
	public static CustomerDataInterface customer(){
		return customer;
	}
	public static ContractDataInterface contract(){
		return contract;
	}
}
