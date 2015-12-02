package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.PriceCategory;
import com.carloan.integration.database.ConnectorFactory;

class PriceCategoryDAO extends DAO<PriceCategory> {
	private static final String SELECT_CAR_CATEGORY = "SELECT * FROM car_category";
	private static final String SELECT_CAR_CATEGORY_FROM_ID = "SELECT * FROM car_category WHERE id=?";
	private static final String INSERT_CAR_CATEGORY = "INSERT INTO car_category(category,base_rate_limit,"
			+ "base_rate_unlimit, km_rate) VALUES ('?',?,?,?)";
	private static final String UPDATE_CAR_CATEGORY = "UPDATE car_category SET category='?',base_rate_limit=?,"
			+ "base_rate_unlimit=?, km_rate=? WHERE id=?";
	private static final String DELETE_CAR_CATEGORY = "DELETE FROM car_category WHERE id=?";
	
	private PriceCategory setPriceCategory(ResultSet rs) throws SQLException{
		PriceCategory priceCategory = new PriceCategory(rs.getString(2).charAt(0),
				rs.getDouble(3),rs.getDouble(4),rs.getDouble(5));
		priceCategory.setId(rs.getInt(1));
		return priceCategory;
	}
	@Override
	public PriceCategory get(int id) {
		String query = SELECT_CAR_CATEGORY_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setPriceCategory(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<PriceCategory> getAll() {
		List<PriceCategory> priceCategories = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_CAR_CATEGORY);
		try {
			while(rs.next())
				priceCategories.add(setPriceCategory(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return priceCategories;
	}
	@Override
	public void create(PriceCategory priceCategory) {
		String query = INSERT_CAR_CATEGORY.replaceFirst("[?]", String.valueOf(priceCategory.getCategory()))
				.replaceFirst("[?]", Double.toString(priceCategory.getBaseRateLimit()))
				.replaceFirst("[?]", Double.toString(priceCategory.getBaseRateUnlimit()))
				.replaceFirst("[?]", Double.toString(priceCategory.getKmRate()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}

	@Override
	public void update(PriceCategory priceCategory) {
		String query = UPDATE_CAR_CATEGORY.replaceFirst("[?]", String.valueOf(priceCategory.getCategory()))
				.replaceFirst("[?]", Double.toString(priceCategory.getBaseRateLimit()))
				.replaceFirst("[?]", Double.toString(priceCategory.getBaseRateUnlimit()))
				.replaceFirst("[?]", Double.toString(priceCategory.getKmRate()))
				.replaceFirst("[?]", Integer.toString(priceCategory.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(PriceCategory priceCategory) {
		String query = DELETE_CAR_CATEGORY.replaceFirst("[?]", Integer.toString(priceCategory.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);		
	}
}
