package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Currency;
import com.carloan.integration.database.ConnectorFactory;

public class CurrencyDAO extends DAO<Currency> {
	private static final String SELECT_ALL = "SELECT * FROM currency";
	private static final String SELECT_FROM_ID = "SELECT * FROM currency WHERE id=?";
	private static final String INSERT = "INSERT INTO currency(type,symbol) VALUES ('?','?')";
	private static final String UPDATE = "UPDATE currency SET type='?', symbol='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM currency WHERE id=?";

	private Currency setCurrency(ResultSet rs) throws SQLException{
		return new Currency(rs.getInt(1), rs.getString(2), rs.getString(3));
	}
	@Override
	public List<Currency> getAll() {
		List<Currency> currencies = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try {
			while(rs.next())
				currencies.add(setCurrency(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currencies;
	}

	@Override
	public Currency get(int id) {
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setCurrency(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void create(Currency currency) {
		String query = INSERT.replaceFirst("[?]", currency.get())
				.replaceFirst("[?]", currency.getSymbol());
		currency.setId(ConnectorFactory.getConnection().executeUpdate(query));		
	}

	@Override
	public void update(Currency currency) {
		String query = UPDATE.replaceFirst("[?]", currency.get())
				.replaceFirst("[?]", currency.getSymbol())
				.replaceFirst("[?]", Integer.toString(currency.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}

	@Override
	public void delete(Currency currency) {
		String query = DELETE.replaceFirst("[?]", Integer.toString(currency.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
