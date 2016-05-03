package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Contract;
import com.carloan.business.model.Currency;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class PaymentDAO implements DAO<Payment> {
	private static final String SELECT_ALL = "SELECT `id`,`amount`,`date`,`type_id`,`currency_id`,`contract_id` FROM payment";
	private static final String SELECT_FROM_ID = "SELECT `id`,`amount`,`date`,`type_id`,`currency_id`,`contract_id` FROM payment WHERE id=?";
	private static final String INSERT = "INSERT INTO payment(amount,date,type_id,currency_id,contract_id) "
			+ "VALUES(?,'?',?,?,?)";
	private static final String UPDATE = "UPDATE payment SET amount=?,date='?',type_id=?,currency_id=?,contract_id=?"
			+ "WHERE id=?";
	private static final String DELETE = "DELETE FROM payment WHERE id=?";
	
	private DAO<Currency> currencyDAO;
	private DAO<PaymentType> paymentTypeDAO;
	private DAO<Contract> contractDAO;
	
    PaymentDAO(){
		currencyDAO = new CurrencyDAO();
		paymentTypeDAO = new PaymentTypeDAO();
		contractDAO = new ContractDAO();
	}

    private Payment setPayment(ResultSet rs) throws SQLException{
		Payment payment = new Payment();
        payment.setId(rs.getInt(1));
        payment.setAmount(rs.getDouble(2));
        payment.setDate(rs.getDate(3).toLocalDate());
        payment.setTime(LocalTime.now());
        payment.setType(paymentTypeDAO.get(rs.getInt(4)));
        payment.setCurrency(currencyDAO.get(rs.getInt(5)));
        payment.setContract(contractDAO.get(rs.getInt(6)));
        return payment;
	}
	public Payment get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setPayment(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	public List<Payment> getAll() {
		List<Payment> payments = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                payments.add(setPayment(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return payments;
	}
	public void create(Payment payment) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, payment.getAmount());
        query = Replacer.replaceFirst(query, payment.getDate(), payment.getTime(),"yyyy-MM-dd HH:mm:ss");
        query = Replacer.replaceFirst(query, payment.getType().getId());
        query = Replacer.replaceFirst(query, payment.getCurrency().getId());
        query = Replacer.replaceFirst(query, payment.getContract().getId());

        payment.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Payment payment) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, payment.getAmount());
        query = Replacer.replaceFirst(query, payment.getDate(), payment.getTime(),"yyyy-MM-dd HH:mm:ss");
        query = Replacer.replaceFirst(query, payment.getType().getId());
        query = Replacer.replaceFirst(query, payment.getCurrency().getId());
        query = Replacer.replaceFirst(query, payment.getContract().getId());
        query = Replacer.replaceFirst(query, payment.getId());
		payment.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void delete(Payment payment) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, payment.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
