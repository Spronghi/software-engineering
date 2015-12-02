package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Operator;
import com.carloan.integration.database.ConnectorFactory;

class OperatorDAO extends DAO<Operator> {
	private static final String SELECT_ALL = "SELECT * FROM operator";
	private static final String SELECT_FROM_ID = "SELECT * FROM operator WHERE id=?";
	private static final String INSERT = "INSERT INTO operator(first_name,last_name,email,username,password)"
			+ "VALUES('?','?','?','?','?')";
	private static final String UPDATE = "UPDATE operator SET first_name='?',last_name='?',email='?',"
			+ "username='?',password='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM operator WHERE username='?'";
	
	private Operator setOperator(ResultSet rs) throws SQLException{
		Operator operator = new Operator(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
		operator.setId(rs.getInt(1));
		return operator;
	}
	@Override
	public Operator get(int id) {
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setOperator(rs);
			//rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Operator> getAll() {
		List<Operator> operators = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try { 
			while(rs.next())
				operators.add(setOperator(rs));
			//rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return operators;
	}
	@Override
	public void create(Operator operator) {
		String query = INSERT.replaceFirst("[?]", operator.getFirstName())
				.replaceFirst("[?]", operator.getLastName())
				.replaceFirst("[?]", operator.getEmail())
				.replaceFirst("[?]", operator.getUsername())
				.replaceFirst("[?]", operator.getPassword());
		operator.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Operator operator) {
		String query = UPDATE.replaceFirst("[?]", operator.getFirstName())
				.replaceFirst("[?]", operator.getLastName())
				.replaceFirst("[?]", operator.getEmail())
				.replaceFirst("[?]", operator.getUsername())
				.replaceFirst("[?]", operator.getPassword())
				.replaceFirst("[?]", Integer.toString(operator.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Operator operator) {
		String query = DELETE.replaceFirst("[?]", operator.getUsername());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
