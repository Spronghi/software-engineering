package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Location;
import com.carloan.integration.database.ConnectorFactory;

class AgencyDAO extends DAO<Agency> {
	private static final String SELECT_AGENCY = "SELECT * FROM agency";
	private static final String SELECT_AGENCY_FROM_ID = "SELECT * FROM agency WHERE id=?";
	private static final String INSERT_AGENCY = "INSERT INTO agency(name,location_id) VALUES ('?',?)";
	private static final String DELETE_AGENCY = "DELETE FROM agency WHERE id=?";
	private static final String UPDATE_AGENCY = "UPDATE agency SET name='?',location_id=? WHERE id=?";
	private DAO<Location> locationDAO;
	
	public AgencyDAO(){
		locationDAO = new LocationDAO();
	}
	private Agency setAgency(ResultSet rs) throws SQLException{
		Agency agency = new Agency(rs.getString(2), locationDAO.get(rs.getInt(3)));
		agency.setNumber(rs.getInt(1));
		return agency;
	}
	@Override
	public Agency get(int id){
		String query = SELECT_AGENCY_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setAgency(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Agency> getAll() {
		List<Agency> agencies = new ArrayList<>();
		
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_AGENCY);
		try {
			while(rs.next())
				agencies.add(setAgency(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return agencies;
	}
	@Override
	public void create(Agency agency) {
		String query = INSERT_AGENCY.replaceFirst("[?]", agency.getName())
				.replaceFirst("[?]", Integer.toString(agency.getLocation().getId()));
		agency.setNumber(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Agency agency) {
		String query = UPDATE_AGENCY.replaceFirst("[?]", agency.getName())
				.replaceFirst("[?]", Integer.toString(agency.getLocation().getId()))
				.replaceFirst("[?]", Integer.toString(agency.getNumber()));			
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Agency agency) {
		String query = DELETE_AGENCY.replaceFirst("[?]", Integer.toString(agency.getNumber()))
				.replaceFirst("[?]", agency.getName());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
