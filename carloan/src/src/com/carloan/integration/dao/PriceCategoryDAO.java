package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.PriceCategory;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class PriceCategoryDAO implements DAO<PriceCategory> {
	private static final String SELECT = "SELECT `id`,`category`,`base_rate_limit`," +
            "`base_rate_unlimit`, `km_rate` FROM car_category";
	private static final String SELECT_FROM_ID = "SELECT `id`,`category`,`base_rate_limit`," +
            "`base_rate_unlimit`, `km_rate` FROM car_category WHERE id=?";
	private static final String INSERT = "INSERT INTO car_category(category,base_rate_limit,"
			+ "base_rate_unlimit, km_rate) VALUES ('?',?,?,?)";
	private static final String UPDATE = "UPDATE car_category SET category='?',base_rate_limit=?,"
			+ "base_rate_unlimit=?, km_rate=? WHERE id=?";
	private static final String DELETE = "DELETE FROM car_category WHERE id=?";
	
	private PriceCategory setPriceCategory(ResultSet rs) throws SQLException{
		PriceCategory category = new PriceCategory();
        category.setId(rs.getInt(1));
        category.set(rs.getString(2));
        category.setBaseRateLimit(rs.getDouble(3));
        category.setBaseRateUnlimit(rs.getDouble(4));
        category.setKmRate(rs.getDouble(5));
        return category;
	}
	@Override
	public PriceCategory get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setPriceCategory(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<PriceCategory> getAll() {
		List<PriceCategory> priceCategories = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
                priceCategories.add(setPriceCategory(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return priceCategories;
	}
	@Override
	public void create(PriceCategory priceCategory) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, priceCategory.get());
        query = Replacer.replaceFirst(query, priceCategory.getBaseRateLimit());
        query = Replacer.replaceFirst(query, priceCategory.getBaseRateUnlimit());
        query = Replacer.replaceFirst(query, priceCategory.getKmRate());
		ConnectorFactory.getConnection().executeUpdate(query);
	}

	@Override
	public void update(PriceCategory priceCategory) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, priceCategory.get());
        query = Replacer.replaceFirst(query, priceCategory.getBaseRateLimit());
        query = Replacer.replaceFirst(query, priceCategory.getBaseRateUnlimit());
        query = Replacer.replaceFirst(query, priceCategory.getKmRate());
        query = Replacer.replaceFirst(query, priceCategory.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(PriceCategory priceCategory) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, priceCategory.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
