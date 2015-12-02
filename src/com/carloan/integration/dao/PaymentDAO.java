package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Contract;
import com.carloan.business.model.Currency;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.DateFormatter;

class PaymentDAO extends DAO<Payment>{
	private static final String SELECT_ALL = "SELECT * FROM payment";
	private static final String SELECT_FROM_ID = "SELECT * FROM payment WHERE id=?";
	private static final String INSERT = "INSERT INTO payment(amount,date,type_id,currency_id,contract_id) "
			+ "VALUES(?,'?',?,?,?)";
	private static final String UPDATE = "UPDATE payment SET amount=?,date='?',type_id=?,currency_id=?,contract_id=?"
			+ "WHERE id=?";
	private static final String DELETE = "DELETE FROM payment WHERE id=?";
	
	private DAO<Currency> currencyDAO;
	private DAO<PaymentType> paymentTypeDAO;
	private DAO<Contract> contractDAO;
	
	public PaymentDAO(){
		currencyDAO = new CurrencyDAO();
		paymentTypeDAO = new PaymentTypeDAO();
		contractDAO = new ContractDAO();
	}
	private Payment setPayment(ResultSet rs) throws SQLException{
		Payment payment= new Payment(rs.getDouble(2),rs.getDate(3).toLocalDate(),rs.getTime(3).toLocalTime(),
				paymentTypeDAO.get(rs.getInt(4)),currencyDAO.get(rs.getInt(5)), contractDAO.get(rs.getInt(6)));
		payment.setId(rs.getInt(1));
		return payment;
	}
	public Payment get(int id){
		String query = SELECT_FROM_ID.replaceFirst("[?]",Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setPayment(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Payment> getAll() {
		List<Payment> payments = new ArrayList<Payment>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try {
			while(rs.next())
				payments.add(setPayment(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}
	public void create(Payment payment) {
		String query = INSERT.replaceFirst("[?]", Double.toString(payment.getAmount()))
				.replaceFirst("[?]", DateFormatter.toString("yyyy-MM-dd HH:mm:ss", payment.getDate(), payment.getTime()))
				.replaceFirst("[?]", Integer.toString(payment.getType().getId()))
				.replaceFirst("[?]", Integer.toString(payment.getCurrency().getId()))
				.replaceFirst("[?]", Integer.toString(payment.getContract().getId()));
		payment.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Payment payment) {
		String query = UPDATE.replaceFirst("[?]", Double.toString(payment.getAmount()))
				.replaceFirst("[?]", DateFormatter.toString("", payment.getDate(), payment.getTime()))
				.replaceFirst("[?]", Integer.toString(payment.getType().getId()))
				.replaceFirst("[?]", Integer.toString(payment.getCurrency().getId()))
				.replaceFirst("[?]", Integer.toString(payment.getContract().getId()))
				.replaceFirst("[?]", Integer.toString(payment.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Payment payment) {
		String query = DELETE.replaceFirst("[?]", Integer.toString(payment.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
