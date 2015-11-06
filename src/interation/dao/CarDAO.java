package interation.dao;

import interation.dao.Queries;
import interation.dao.exception.AgencyNotFoundException;
import interation.database.ConnectorFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Agency;
import business.model.Car;

class CarDAO extends GenericDAO<Car> {
	List<Car> cars;
	GenericDAO<Agency> daoAgency;
	public CarDAO(){
		cars = new ArrayList<>();
		daoAgency = new AgencyDAO();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_CAR);
		init(rs);
	}
	private void init(ResultSet rs){
		try {
			while(rs.next())
				cars.add(setCar(rs));
		} catch (SQLException | AgencyNotFoundException e) {
			e.printStackTrace();
		}
	}
	private Car setCar(ResultSet rs) throws AgencyNotFoundException, SQLException{
		Car car = new Car(rs.getString(2),rs.getString(3),rs.getInt(4),
				Util.getCarCategory(rs.getInt(5)),getAgency(rs.getInt(6)),Util.getCarStatus(rs.getInt(7)));
		car.setId(rs.getInt(1));
		return car;
	}
	private Agency getAgency(int id) throws AgencyNotFoundException{
		return daoAgency.getAll().stream().filter(agency -> agency.getNumber() == id)
				.findFirst().orElseThrow(() -> new AgencyNotFoundException());
	}
	@Override
	public List<Car> getAll() {
		return cars;
	}
	@Override
	public void add(Car car) {
		if(!cars.contains(car)){
			String query = Queries.INSERT_CAR.replaceFirst("[?]", car.getModel())
					.replaceFirst("[?]", car.getLicensePlate())
					.replaceFirst("[?]", Integer.toString(car.getKm()))
					.replaceFirst("[?]", Integer.toString(Util.getCarCategoryId(car.getCategory())))
					.replaceFirst("[?]", Integer.toString(car.getAgency().getNumber()))
					.replaceFirst("[?]", Integer.toString(Util.getCarStatusId(car.getStatus())));
			ConnectorFactory.getConnection().executeUpdate(query);
			cars.add(car);
		}
	}
	@Override
	public void edit(Car car) {
		if(cars.contains(car)){
			String query = Queries.UPDATE_CAR.replaceFirst("[?]", car.getModel())
					.replaceFirst("[?]", car.getLicensePlate())
					.replaceFirst("[?]", Integer.toString(car.getKm()))
					.replaceFirst("[?]", Integer.toString(Util.getCarCategoryId(car.getCategory())))
					.replaceFirst("[?]", Integer.toString(car.getAgency().getNumber()))
					.replaceFirst("[?]", Integer.toString(Util.getCarStatusId(car.getStatus())))
					.replaceFirst("[?]", car.getLicensePlate());
			ConnectorFactory.getConnection().executeUpdate(query);
		}
	}
	@Override
	public void delete(Car car) {
		if(cars.contains(car)){
			String query = Queries.DELETE_CAR.replaceFirst("[?]", car.getLicensePlate());
			ConnectorFactory.getConnection().executeUpdate(query);
			cars.remove(car);
		}
	}
}
