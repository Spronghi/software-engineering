package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Location;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class AgencyDAO implements DAO<Agency> {
	private static final String SELECT = "SELECT `id`,`name`,`location_id` FROM agency";
	private static final String SELECT_FROM_ID = "SELECT `id`,`name`,`location_id`  FROM agency WHERE id=?";
	private static final String INSERT = "INSERT INTO agency(name,location_id) VALUES ('?',?)";
	private static final String DELETE = "DELETE FROM agency WHERE id=?";
	private static final String UPDATE = "UPDATE agency SET name='?',location_id=? WHERE id=?";

    private DAO<Location> locationDAO;
	
    AgencyDAO(){
		locationDAO = new LocationDAO();
	}
	private Agency setAgency(ResultSet rs) throws SQLException{
        Agency agency = new Agency();
        agency.setId(rs.getInt(1));
        agency.setName(rs.getString(2));
        agency.setLocation(locationDAO.get(rs.getInt(3)));
		return agency;
	}
	@Override
	public Agency get(int id){
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try{
            if (rs.next()) {
                return setAgency(rs);
            }
        } catch(SQLException e){
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<Agency> getAll(){
		List<Agency> agencies = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try{
            while(rs.next()) {
                agencies.add(setAgency(rs));
            }
        } catch(SQLException e){
            FXAlert.warning(e,"Database Error");
        }
		return agencies;
	}
	@Override
	public void create(Agency agency) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, agency.getName());
        query = Replacer.replaceFirst(query, agency.getLocation().getId());
        agency.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Agency agency) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, agency.getName());
        query = Replacer.replaceFirst(query, agency.getLocation().getId());
        query = Replacer.replaceFirst(query, agency.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Agency agency) {
        String query = DELETE;
        query = Replacer.replaceFirst(query, agency.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
