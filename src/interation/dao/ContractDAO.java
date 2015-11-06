package interation.dao;

import interation.dao.exception.AgencyNotFoundException;
import interation.dao.exception.CarNotFoundException;
import interation.dao.exception.CustomerNotFoundException;
import interation.dao.exception.OperatorNotFoundException;
import interation.dao.exception.PaymentNotFoundException;
import interation.database.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import business.model.Agency;
import business.model.Car;
import business.model.Contract;
import business.model.Customer;
import business.model.Operator;
import business.model.Payment;

class ContractDAO extends GenericDAO<Contract> {
	private GenericDAO<Operator> operatorDAO;
	private GenericDAO<Customer> customerDAO;
	private GenericDAO<Agency> agencyDAO;
	private GenericDAO<Car> carDAO;
	private PaymentDAO paymentDAO;
	private class PaymentDAO {
		private Payment setPayment(ResultSet rs) throws SQLException{
			Payment payment= new Payment(rs.getDouble(2),rs.getDate(3).toLocalDate(),
					rs.getTime(3).toLocalTime(),Util.getPaymentType(rs.getInt(4)),Util.getCurrency(rs.getInt(5)),
					Util.getCurrencySymbol(rs.getInt(6)));
			payment.setId(rs.getInt(1));
			return payment;
		}
		private int add(Payment payment, int contractNo) {
			String query = Queries.INSERT_PAYMENT.replaceFirst("[?]", Double.toString(payment.getAmount()))
					.replaceFirst("[?]", Util.getTimestampFormatString(payment.getDate(), payment.getTime()))
					.replaceFirst("[?]", Integer.toString(Util.getPaymentTypeId(payment.getType())))
					.replaceFirst("[?]", Integer.toString(Util.getCurrencyId(payment.getCurrency())))
					.replaceFirst("[?]", Integer.toString(contractNo));
			payment.setId(ConnectorFactory.getConnection().executeUpdate(query));
			return payment.getId();
		}
		private List<Payment> getAll() {
			List<Payment> payments = new ArrayList<Payment>();
			ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_PAYMENT);
			try {
				while(rs.next())
					payments.add(setPayment(rs));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return payments;
		}
	}
	public ContractDAO(){
		operatorDAO = DAOFactory.getInstance("OperatorDAO");
		customerDAO = DAOFactory.getInstance("CustomerDAO");
		agencyDAO = DAOFactory.getInstance("AgencyDAO");
		carDAO= DAOFactory.getInstance("CarDAO");
		paymentDAO = new PaymentDAO();
	}
	/*
	 * 	public Contract(LocalDate startDate, LocalDate endDate,
				int km, boolean kmLimit, boolean open, Operator operator,
				Customer customer, Agency startAgency, Agency endAgency, Car car,
				String type, List<Payment> payments, double amount) 
	 */
	private Contract setContract(ResultSet rs) throws SQLException, OperatorNotFoundException, 
					CustomerNotFoundException, AgencyNotFoundException, CarNotFoundException,
					PaymentNotFoundException{
		int contractId = rs.getInt(1);
		Contract contract= new Contract(rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),
				rs.getInt(4),rs.getBoolean(5),rs.getBoolean(6),getOperator(rs.getInt(7)),
				getCustomer(rs.getInt(8)),
				getAgency(rs.getInt(9)),getAgency(rs.getInt(10)),getCar(rs.getInt(11)),
				rs.getString(12),getPayments(contractId),rs.getDouble(13));
		contract.setContractNo(contractId);
		return contract;
	}
	private Operator getOperator(int id) throws OperatorNotFoundException{
		return operatorDAO.getAll().stream()
				.filter(operator -> operator.getId() == id)
				.findFirst().orElseThrow(() -> new OperatorNotFoundException());
	}
	private Customer getCustomer(int id) throws CustomerNotFoundException{
		return customerDAO.getAll().stream()
				.filter(customer -> customer.getId() == id)
						.findFirst().orElseThrow(() -> new CustomerNotFoundException());
	}
	private Agency getAgency(int agencyNo) throws AgencyNotFoundException{
		return agencyDAO.getAll().stream().filter(agency -> agency.getNumber() == agencyNo)
				.findFirst().orElseThrow(() -> new AgencyNotFoundException());
	}
	private Car getCar(int id) throws CarNotFoundException{
		return carDAO.getAll().stream().filter(car -> car.getId() == id)
				.findFirst().orElseThrow(() -> new CarNotFoundException());
	}
	private List<Payment> getPayments(int contractId) throws SQLException, PaymentNotFoundException{
		String query = Queries.SELECT_CONTRACT_PAYMENTS.replaceFirst("[?]",Integer.toString(contractId));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		List<Payment> payments = new ArrayList<>();
		while(rs.next()){
			int paymentId = rs.getInt(1);
			Optional<Payment> foundPayment = paymentDAO.getAll().stream()
					.filter(payment -> payment.getId() == paymentId).findFirst();
			payments.add(foundPayment.orElseThrow(() -> new PaymentNotFoundException()));
		}
		return payments;
	}
	private void updatePayments(Contract oldContract, Contract newContract){
		if(oldContract.getPayments().size() < newContract.getPayments().size()){
			List<Payment> differentPayments = newContract.getPayments().stream()
					.filter(p -> !oldContract.getPayments().contains(p)).collect(Collectors.toList());
			for(Payment p : differentPayments){
				p.setId(paymentDAO.add(p, oldContract.getContractNo()));
			}
		}
	}
	private Contract getOldContract(int contractNo){
		String query = Queries.SELECT_CONTRACT_FROM_ID.replaceFirst("[?]", Integer.toString(contractNo));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		Contract oldContract=null;
		try {
			if(rs.next())
				oldContract = setContract(rs);
		} catch (SQLException | OperatorNotFoundException
				| CustomerNotFoundException | AgencyNotFoundException
				| CarNotFoundException | PaymentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oldContract;
	}
	@Override
	public List<Contract> getAll() {
		List<Contract> contracts = new ArrayList<Contract>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_CONTRACT);
		try {
			while(rs.next())
				contracts.add(setContract(rs));
		} catch (SQLException | OperatorNotFoundException | 
				CustomerNotFoundException | AgencyNotFoundException | 
				CarNotFoundException | PaymentNotFoundException e) {
			e.printStackTrace();
		}
		return contracts;
	}
	@Override
	public void add(Contract contract) {
		String query = Queries.INSERT_CONTRACT
				.replaceFirst("[?]",Util.getTimestampFormatString(contract.getStartDate()))
				.replaceFirst("[?]",Util.getTimestampFormatString(contract.getEndDate()))
				.replaceFirst("[?]",Integer.toString(contract.getKm()))
				.replaceFirst("[?]",Boolean.toString(contract.isKmLimit()))
				.replaceFirst("[?]",Boolean.toString(contract.isOpen()))
				.replaceFirst("[?]",Integer.toString(contract.getOperator().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getCustomer().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getStartAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getEndAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getCar().getId()))
				.replaceFirst("[?]",Integer.toString(Util.getContractTypeId(contract.getType())))
				.replaceFirst("[?]",Double.toString(contract.getAmount()));
		contract.setContractNo(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void edit(Contract contract) {
		String query = Queries.UPDATE_CONTRACT
				.replaceFirst("[?]",Util.getTimestampFormatString(contract.getStartDate()))
				.replaceFirst("[?]",Util.getTimestampFormatString(contract.getEndDate()))
				.replaceFirst("[?]",Integer.toString(contract.getKm()))
				.replaceFirst("[?]",Boolean.toString(contract.isKmLimit()))
				.replaceFirst("[?]",Boolean.toString(contract.isOpen()))
				.replaceFirst("[?]",Integer.toString(contract.getOperator().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getCustomer().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getStartAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getEndAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getCar().getId()))
				.replaceFirst("[?]",Integer.toString(Util.getContractTypeId(contract.getType())))
				.replaceFirst("[?]",Double.toString(contract.getAmount()))
				.replaceFirst("[?]",Integer.toString(contract.getContractNo()));
		updatePayments(getOldContract(contract.getContractNo()),contract);
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Contract contract) {
		String query = Queries.DELETE_CONTRACT
				.replaceFirst("[?]", Integer.toString(contract.getContractNo()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
