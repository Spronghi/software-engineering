package interation.dao;

import interation.dao.exception.LocationNotFoundException;
import interation.database.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Agency;
import business.model.Location;

class AgencyDAO extends GenericDAO<Agency> {
	public AgencyDAO() {
	}
	private Location getLocation(int id) throws LocationNotFoundException{
		GenericDAO<Location> locationDAO= new LocationDAO();
		return locationDAO.getAll().stream().filter(l -> l.getId()==id).findFirst()
				.orElseThrow(() ->new LocationNotFoundException());
	}
	private Agency setAgency(ResultSet rs) throws SQLException, LocationNotFoundException{
		Agency agency=new Agency(rs.getString(2), getLocation(rs.getInt(3)));
		agency.setNumber(rs.getInt(1));
		return agency;
	}
	@Override
	public List<Agency> getAll() {
		List<Agency> agencies = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_AGENCY);
		try {
			while(rs.next())
				agencies.add(setAgency(rs));
		} catch (SQLException | LocationNotFoundException e) {
			e.printStackTrace();
		}
		return agencies;
	}
	@Override
	public void add(Agency agency) {
		String query = Queries.INSERT_AGENCY.replaceFirst("[?]", agency.getName())
				.replaceFirst("[?]", Integer.toString(agency.getLocation().getId()));
		agency.setNumber(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void edit(Agency agency) {
		String query = Queries.UPDATE_AGENCY.replaceFirst("[?]", agency.getName())
				.replaceFirst("[?]", Integer.toString(agency.getLocation().getId()))
				.replaceFirst("[?]", Integer.toString(agency.getNumber()));			
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Agency agency) {
		String query = Queries.DELETE_AGENCY.replaceFirst("[?]", Integer.toString(agency.getNumber()))
				.replaceFirst("[?]", agency.getName());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
