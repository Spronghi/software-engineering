package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.ContractType;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Operator;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.DateFormatter;

class ContractDAO extends DAO<Contract> {
	private static final String SELECT_ALL = "SELECT * FROM contract";
	private static final String SELECT_FROM_ID = "SELECT * FROM contract WHERE id=?";
	private static final String INSERT = "INSERT INTO contract(start,end,km,km_limit,open,operator_id,"
			+ "customer_id,start_agency_id,end_agency_id,car_id,type_id) VALUES "
			+ "('?','?',?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE contract SET start='?',end='?',km=?,km_limit=?,open=?,"
			+ "operator_id=?,customer_id=?,start_agency_id=?,end_agency_id=?,car_id=?,type_id=? "
			+ "WHERE contract.id=?";
	private static final String DELETE = "DELETE FROM contract WHERE id=?";
	private static final String DELETE_CONTRACT_PAYMENTS = "DELETE FROM payment WHERE contract_id=?";

	private DAO<Customer> customerDAO;
	private DAO<Agency> agencyDAO;
	private DAO<Car> carDAO;
	private DAO<Operator> operatorDAO;
	private DAO<ContractType> contractTypeDAO;
	
	public ContractDAO(){
		operatorDAO = new OperatorDAO();
		customerDAO = new CustomerDAO();
		agencyDAO = new AgencyDAO();
		carDAO= new CarDAO();
		contractTypeDAO = new ContractTypeDAO();
	}
	private Contract setContract(ResultSet rs) throws SQLException{	
		Contract contract= new Contract(rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),
				rs.getInt(4),rs.getBoolean(5),rs.getBoolean(6),operatorDAO.get(rs.getInt(7)),
				customerDAO.get(rs.getInt(8)),agencyDAO.get(rs.getInt(9)),
				agencyDAO.get(rs.getInt(10)),carDAO.get(11),contractTypeDAO.get(rs.getInt(12)));
		contract.setContractNo(rs.getInt(1));
		return contract;
	}
	@Override
	public Contract get(int id) {
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setContract(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	@Override
	public List<Contract> getAll() {
		List<Contract> contracts = new ArrayList<Contract>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try {
			while(rs.next())
				contracts.add(setContract(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contracts;
	}
	@Override
	public void create(Contract contract) {
		String query = INSERT
				.replaceFirst("[?]",DateFormatter.toString("yyyy-MM-dd",contract.getStartDate()))
				.replaceFirst("[?]",DateFormatter.toString("yyyy-MM-dd",contract.getEndDate()))
				.replaceFirst("[?]",Integer.toString(contract.getKm()))
				.replaceFirst("[?]",Boolean.toString(contract.isKmLimit()))
				.replaceFirst("[?]",Boolean.toString(contract.isOpen()))
				.replaceFirst("[?]",Integer.toString(contract.getOperator().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getCustomer().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getStartAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getEndAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getCar().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getType().getId()));
		contract.setContractNo(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Contract contract) {
		String query = UPDATE
				.replaceFirst("[?]",DateFormatter.toString("yyyy-MM-dd",contract.getStartDate()))
				.replaceFirst("[?]",DateFormatter.toString("yyyy-MM-dd",contract.getEndDate()))
				.replaceFirst("[?]",Integer.toString(contract.getKm()))
				.replaceFirst("[?]",Boolean.toString(contract.isKmLimit()))
				.replaceFirst("[?]",Boolean.toString(contract.isOpen()))
				.replaceFirst("[?]",Integer.toString(contract.getOperator().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getCustomer().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getStartAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getEndAgency().getNumber()))
				.replaceFirst("[?]",Integer.toString(contract.getCar().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getType().getId()))
				.replaceFirst("[?]",Integer.toString(contract.getContractNo()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Contract contract) {
		deleteFromContract(contract);
		String query = DELETE.replaceFirst("[?]", Integer.toString(contract.getContractNo()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	private void deleteFromContract(Contract contract) {
		String query = DELETE_CONTRACT_PAYMENTS.replaceFirst("[?]", contract.getId().toString());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
