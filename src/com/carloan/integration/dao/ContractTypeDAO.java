package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.ContractType;
import com.carloan.integration.database.ConnectorFactory;

public class ContractTypeDAO extends DAO<ContractType> {
	private static final String SELECT_ALL = "SELECT * FROM contract_type";
	private static final String SELECT_FROM_ID = "SELECT * FROM contract_type WHERE id=?";
	private static final String INSERT = "INSERT INTO contract_type(type) VALUES ('?')";
	private static final String UPDATE = "UPDATE contract_type SET type='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM contract_type WHERE id=?";
	
	private ContractType setType(ResultSet rs) throws SQLException{
		return new ContractType(rs.getInt(1),rs.getString(2));
	}
	@Override
	public List<ContractType> getAll() {
		List<ContractType> types = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try {
			while(rs.next())
				types.add(setType(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return types;
	}
	@Override
	public ContractType get(int id) {
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setType(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void create(ContractType type) {
		String query = INSERT.replaceFirst("[?]", type.get());
		type.setId(ConnectorFactory.getConnection().executeUpdate(query));		
	}
	@Override
	public void update(ContractType type) {
		String query = UPDATE.replaceFirst("[?]", type.get())
				.replaceFirst("[?]", Integer.toString(type.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(ContractType type) {
		String query = DELETE.replaceFirst("[?]", Integer.toString(type.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
