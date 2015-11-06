import business.model.Agency;
import business.model.Car;
import business.model.Contract;
import business.model.Customer;
import business.model.Location;
import business.model.Operator;
import business.service.GenericService;
import business.service.ServiceFactory;


public class Main {
	public static void main(String[] args) {
		GenericService<Location> locationService = ServiceFactory.getInstance("LocationService");
		GenericService<Agency> agencyService = ServiceFactory.getInstance("AgencyService");
		GenericService<Car> carService = ServiceFactory.getInstance("CarService");
		GenericService<Operator> operatorService = ServiceFactory.getInstance("OperatorService");
		GenericService<Customer> customerService = ServiceFactory.getInstance("CustomerService");
		GenericService<Contract> contractService = ServiceFactory.getInstance("ContractService");
		
		locationService.getAll().stream().forEach(m -> System.out.println(m.toString()));
		agencyService.getAll().stream().forEach(m -> System.out.println(m.toString()));
		carService.getAll().stream().forEach(m -> System.out.println(m.toString()));
		operatorService.getAll().stream().forEach(m -> System.out.println(m.toString()));
		customerService.getAll().stream().forEach(m -> System.out.println(m.toString()));
		contractService.getAll().stream().forEach(m -> System.out.println(m.toString()));
	}
}
