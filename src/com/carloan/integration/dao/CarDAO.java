package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.PriceCategory;
import com.carloan.business.model.Status;
import com.carloan.integration.database.ConnectorFactory;

class CarDAO extends DAO<Car> {
	private static final String SELECT = "SELECT * FROM car";
	private static final String SELECT_FROM_ID = "SELECT * FROM car WHERE id=?";
	private static final String INSERT = "INSERT INTO car(model,license_plate,km,category_id,agency_id,"
			+ "status_id) VALUES ('?','?',?,'?',?,?)";
	private static final String UPDATE = "UPDATE car SET model='?',license_plate='?',km=?,category_id=?,"
			+ "agency_id=?,status_id=? WHERE car.id=?";
	private static final String DELETE = "DELETE FROM car WHERE car.id=?";

	private DAO<Agency> agencyDAO;
	private DAO<PriceCategory> categoryDAO;
	private DAO<Status> statusDAO;

	public CarDAO(){
		agencyDAO = new AgencyDAO();
		categoryDAO = new PriceCategoryDAO();
		statusDAO = new CarStatusDAO();
	}
	private Car setCar(ResultSet rs) throws SQLException{
		return new Car(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),
				categoryDAO.get(rs.getInt(5)),agencyDAO.get(rs.getInt(6)),
				statusDAO.get(rs.getInt(7)));
	}
	@Override
	public Car get(int id){
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setCar(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Car> getAll() {
		List<Car> cars = new ArrayList<>();
		try {
			ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
			while(rs.next())
				cars.add(setCar(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cars;
	}
	@Override
	public void create(Car car) {
		String query = INSERT.replaceFirst("[?]", car.getModel())
				.replaceFirst("[?]", car.getLicensePlate())
				.replaceFirst("[?]", Integer.toString(car.getKm()))
				.replaceFirst("[?]", Integer.toString(car.getCategory().getId()))
				.replaceFirst("[?]", Integer.toString(car.getAgency().getNumber()))
				.replaceFirst("[?]", Integer.toString(car.getStatus().getId()));
		car.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Car car) {
		String query = UPDATE.replaceFirst("[?]", car.getModel())
				.replaceFirst("[?]", car.getLicensePlate())
				.replaceFirst("[?]", Integer.toString(car.getKm()))
				.replaceFirst("[?]", Integer.toString(car.getCategory().getId()))
				.replaceFirst("[?]", Integer.toString(car.getAgency().getNumber()))
				.replaceFirst("[?]", Integer.toString(car.getStatus().getId()))
				.replaceFirst("[?]", Integer.toString(car.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Car car) {
		String query = DELETE.replaceFirst("[?]", Integer.toString(car.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
