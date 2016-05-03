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
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class ContractDAO implements DAO<Contract> {
	private static final String SELECT_ALL = "SELECT `id`,`start`,`end`,`km`,`km_limit`,`open`,`operator_id`," +
            "`customer_id`,`start_agency_id`,`end_agency_id`,`car_id`,`type_id` FROM contract";
	private static final String SELECT_FROM_ID = "SELECT `id`,`start`,`end`,`km`,`km_limit`,`open`,`operator_id`," +
            "`customer_id`,`start_agency_id`,`end_agency_id`,`car_id`,`type_id` FROM contract WHERE id=?";
	private static final String INSERT = "INSERT INTO contract(start,end,km,km_limit,open,operator_id,"
			+ "customer_id,start_agency_id,end_agency_id,car_id,type_id) VALUES "
			+ "('?','?',?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE contract SET start='?',end='?',km=?,km_limit=?,open=?,"
			+ "operator_id=?,customer_id=?,start_agency_id=?,end_agency_id=?,car_id=?,type_id=? "
			+ "WHERE contract.id=?";
	private static final String DELETE = "DELETE FROM contract WHERE id=?";
	private static final String DELETE_PAYMENTS_FROM_CONTRACT = "DELETE FROM payment WHERE contract_id=?";

	private DAO<Customer> customerDAO;
	private DAO<Agency> agencyDAO;
	private DAO<Car> carDAO;
	private DAO<Operator> operatorDAO;
	private DAO<ContractType> contractTypeDAO;
	
    ContractDAO(){
		operatorDAO = new OperatorDAO();
		customerDAO = new CustomerDAO();
		agencyDAO = new AgencyDAO();
		carDAO= new CarDAO();
		contractTypeDAO = new ContractTypeDAO();
	}

	private Contract setContract(ResultSet rs) throws SQLException{	
		Contract contract = new Contract();
        contract.setId(rs.getInt(1));
        contract.setStartDate(rs.getDate(2).toLocalDate());
        contract.setEndDate(rs.getDate(3).toLocalDate());
        contract.setKm(rs.getInt(4));
        contract.setKmLimit(rs.getBoolean(5));
        contract.setOpen(rs.getBoolean(6));
        contract.setOperator(operatorDAO.get(rs.getInt(7)));
        contract.setCustomer(customerDAO.get(rs.getInt(8)));
        contract.setStartAgency(agencyDAO.get(rs.getInt(9)));
        contract.setEndAgency(agencyDAO.get(rs.getInt(10)));
        contract.setCar(carDAO.get(rs.getInt(11)));
        contract.setType(contractTypeDAO.get(rs.getInt(12)));
        return contract;
	}
	@Override
	public Contract get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setContract(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<Contract> getAll() {
		List<Contract> contracts = new ArrayList<Contract>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                contracts.add(setContract(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return contracts;
	}
	@Override
	public void create(Contract contract) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, contract.getStartDate(),"yyyy-MM-dd");
        query = Replacer.replaceFirst(query, contract.getEndDate(),"yyyy-MM-dd");
        query = Replacer.replaceFirst(query, contract.getKm());
        query = Replacer.replaceFirst(query, contract.isKmLimit());
        query = Replacer.replaceFirst(query, contract.isOpen());
        query = Replacer.replaceFirst(query, contract.getOperator().getId());
        query = Replacer.replaceFirst(query, contract.getCustomer().getId());
        query = Replacer.replaceFirst(query, contract.getStartAgency().getId());
        query = Replacer.replaceFirst(query, contract.getEndAgency().getId());
        query = Replacer.replaceFirst(query, contract.getCar().getId());
        query = Replacer.replaceFirst(query, contract.getType().getId());

		contract.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Contract contract) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, contract.getStartDate(),"yyyy-MM-dd");
        query = Replacer.replaceFirst(query, contract.getEndDate(),"yyyy-MM-dd");
        query = Replacer.replaceFirst(query, contract.getKm());
        query = Replacer.replaceFirst(query, contract.isKmLimit());
        query = Replacer.replaceFirst(query, contract.isOpen());
        query = Replacer.replaceFirst(query, contract.getOperator().getId());
        query = Replacer.replaceFirst(query, contract.getCustomer().getId());
        query = Replacer.replaceFirst(query, contract.getStartAgency().getId());
        query = Replacer.replaceFirst(query, contract.getEndAgency().getId());
        query = Replacer.replaceFirst(query, contract.getCar().getId());
        query = Replacer.replaceFirst(query, contract.getType().getId());
        query = Replacer.replaceFirst(query, contract.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Contract contract) {
		deletePaymentFromContract(contract);
		String query = DELETE;
        query = Replacer.replaceFirst(query, contract.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	private void deletePaymentFromContract(Contract contract) {
		String query = DELETE_PAYMENTS_FROM_CONTRACT;
        query = Replacer.replaceFirst(query, contract.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
