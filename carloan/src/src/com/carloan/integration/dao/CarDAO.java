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
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class CarDAO implements DAO<Car> {
	private static final String SELECT = "SELECT `id`, `model`,`license_plate`,`km`,`category_id`,`agency_id`,"
            +"`status_id` FROM car";
	private static final String SELECT_FROM_ID = "SELECT `id`, `model`,`license_plate`,`km`,`category_id`," +
            "`agency_id`,`status_id` FROM car WHERE id=?";
	private static final String INSERT = "INSERT INTO car(model,license_plate,km,category_id,agency_id,"
			+ "status_id) VALUES ('?','?',?,'?',?,?)";
	private static final String UPDATE = "UPDATE car SET model='?',license_plate='?',km=?,category_id=?,"
			+ "agency_id=?,status_id=? WHERE car.id=?";
	private static final String DELETE = "DELETE FROM car WHERE car.id=?";

	private DAO<Agency> agencyDAO;
	private DAO<PriceCategory> categoryDAO;
	private DAO<Status> statusDAO;

    CarDAO(){
		agencyDAO = new AgencyDAO();
		categoryDAO = new PriceCategoryDAO();
		statusDAO = new CarStatusDAO();
	}
	private Car setCar(ResultSet rs) throws SQLException{
        Car car = new Car();
        car.setId(rs.getInt(1));
        car.setModel(rs.getString(2));
        car.setLicensePlate(rs.getString(3));
        car.setKm(rs.getInt(4));
        car.setCategory(categoryDAO.get(rs.getInt(5)));
        car.setAgency(agencyDAO.get(rs.getInt(6)));
        car.setStatus(statusDAO.get(rs.getInt(7)));
        return car;
	}
	@Override
	public Car get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setCar(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e, "Database Error");
        }
    return null;
	}
	@Override
	public List<Car> getAll() {
		List<Car> cars = new ArrayList<>();
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
                cars.add(setCar(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e, "Database Error");
        }
        return cars;
	}
	@Override
	public void create(Car car) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, car.getModel());
        query = Replacer.replaceFirst(query, car.getLicensePlate());
        query = Replacer.replaceFirst(query, car.getKm());
        query = Replacer.replaceFirst(query, car.getCategory().getId());
        query = Replacer.replaceFirst(query, car.getAgency().getId());
        query = Replacer.replaceFirst(query, car.getStatus().getId());

		car.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Car car) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, car.getModel());
        query = Replacer.replaceFirst(query, car.getLicensePlate());
        query = Replacer.replaceFirst(query, car.getKm());
        query = Replacer.replaceFirst(query, car.getCategory().getId());
        query = Replacer.replaceFirst(query, car.getAgency().getId());
        query = Replacer.replaceFirst(query, car.getStatus().getId());
        query = Replacer.replaceFirst(query, car.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Car car) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, car.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
