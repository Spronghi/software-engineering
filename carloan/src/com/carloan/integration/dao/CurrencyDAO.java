package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Currency;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class CurrencyDAO implements DAO<Currency> {
	private static final String SELECT_ALL = "SELECT `id`,`type`,`symbol` FROM currency";
	private static final String SELECT_FROM_ID = "SELECT `id`,`type`,`symbol` FROM currency WHERE id=?";
	private static final String INSERT = "INSERT INTO currency(type,symbol) VALUES ('?','?')";
	private static final String UPDATE = "UPDATE currency SET type='?', symbol='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM currency WHERE id=?";

	private Currency setCurrency(ResultSet rs) throws SQLException{
        Currency currency = new Currency();
        currency.setId(rs.getInt(1));
        currency.set(rs.getString(2));
        currency.setSymbol(rs.getString(3));
        return currency;
	}
	@Override
	public List<Currency> getAll() {
		List<Currency> currencies = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                currencies.add(setCurrency(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return currencies;
	}

	@Override
	public Currency get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setCurrency(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public void create(Currency currency) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, currency.get());
        query = Replacer.replaceFirst(query, currency.getSymbol());
		currency.setId(ConnectorFactory.getConnection().executeUpdate(query));		
	}

	@Override
	public void update(Currency currency) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, currency.get());
        query = Replacer.replaceFirst(query, currency.getSymbol());
        query = Replacer.replaceFirst(query, currency.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}

	@Override
	public void delete(Currency currency) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, currency.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
