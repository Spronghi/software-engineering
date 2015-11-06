package interation.dao;

import interation.database.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Location;

class LocationDAO extends GenericDAO<Location> {
	public LocationDAO(){
	}
	private Location setLocation(ResultSet rs) throws SQLException{
		Location location = new Location(rs.getString(2), rs.getString(3), rs.getString(4));
		location.setId(rs.getInt(1));
		return location;
	}
	@Override
	public List<Location> getAll() {
		List<Location> locations = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_LOCATION);
		try {
			while(rs.next())
				locations.add(setLocation(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}
	@Override
	public void add(Location location) {
		String query = Queries.INSERT_LOCATION.replaceFirst("[?]", location.getCity())
				.replaceFirst("[?]", location.getPostalNo())
				.replaceFirst("[?]", location.getRoad());
		location.setId(ConnectorFactory.getConnection().executeUpdate(query));	
	}
	@Override
	public void edit(Location location) {
		String query = Queries.UPDATE_LOCATION.replaceFirst("[?]", location.getCity())
				.replaceFirst("[?]", location.getPostalNo())
				.replaceFirst("[?]", location.getRoad())
				.replaceFirst("[?]", Integer.toString(location.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Location location) {
		String query = Queries.DELETE_LOCATION.replaceFirst("[?]", location.getCity())
				.replaceFirst("[?]", location.getPostalNo())
				.replaceFirst("[?]", location.getRoad());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
