package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.PaymentType;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class PaymentTypeDAO implements DAO<PaymentType> {
	private static final String SELECT_ALL = "SELECT `id`,`type` FROM payment_type";
	private static final String SELECT_FROM_ID = "SELECT `id`,`type` FROM payment_type WHERE id=?";
	private static final String INSERT = "INSERT INTO payment_type(type) VALUES ('?')";
	private static final String UPDATE = "UPDATE payment_type SET type='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM payment_type WHERE id=?";
	
	private PaymentType setType(ResultSet rs) throws SQLException{
		PaymentType type = new PaymentType();
        type.setId(rs.getInt(1));
        type.set(rs.getString(2));
        return type;
	}
	@Override
	public List<PaymentType> getAll(){
		List<PaymentType> types = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                types.add(setType(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return types;
	}
	@Override
	public PaymentType get(int id){
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setType(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public void create(PaymentType type) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, type.get());
        type.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(PaymentType type) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, type.get());
        query = Replacer.replaceFirst(query, type.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(PaymentType type) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, type.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
