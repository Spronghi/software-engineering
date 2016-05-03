package com.anrc.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anrc.integration.database.ConnectorFactory;
import com.anrc.integration.util.Replacer;
import com.anrc.model.DetectorType;

class DetectorTypeDAO implements DAO<DetectorType> {
	private static final String SELECT = "SELECT `id`,`type`,`min_value`,`max_value` FROM detector_type";
	private static final String SELECT_FROM_ID = "SELECT `id`,`type`,`min_value`,`max_value` FROM detector_type WHERE ID=?";
	private static final String INSERT = "INSERT INTO detector_type(type,min_value,max_value) VALUES ('?',?,?)";
	private static final String DELETE = "DELETE FROM detector_type WHERE id=?";
	private static final String UPDATE = "UPDATE detector_type SET type='?',min_value=?,max_value=? WHERE id=?";
	
	private DetectorType setType(ResultSet rs) throws SQLException{
		DetectorType type = new DetectorType();
        type.setId(rs.getInt(1));
        type.setType(rs.getString(2));
        type.setMinValue(rs.getFloat(3));
        type.setMaxValue(rs.getFloat(4));
        return type;
	}
	@Override
	public List<DetectorType> getAll() {
		List<DetectorType> types = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
            	types.add(setType(rs));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return types;
	}
	@Override
	public DetectorType get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setType(rs);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
	}
	@Override
	public void create(DetectorType model) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, model.getType());
        query = Replacer.replaceFirst(query, model.getMinValue());
        query = Replacer.replaceFirst(query, model.getMaxValue());
        model.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(DetectorType model) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, model.getType());
        query = Replacer.replaceFirst(query, model.getMinValue());
        query = Replacer.replaceFirst(query, model.getMaxValue());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(DetectorType model) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, model.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
