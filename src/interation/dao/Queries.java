package interation.dao;

final class Queries {
	static final String SELECT_LOCATION = "SELECT * FROM location";
	static final String SELECT_LOCATION_ID = "SELECT id FROM location WHERE city='?'&& postal_no='?' && road='?'";
	static final String INSERT_LOCATION = "INSERT INTO location(city,postal_no,road) VALUES ('?','?','?')";
	static final String UPDATE_LOCATION = "UPDATE location SET city='?',postal_no='?',road='?' "
			+ "WHERE id=?";
	static final String DELETE_LOCATION = "DELETE FROM location WHERE city='?' && postal_no='?' && road='?'";
	
	static final String SELECT_AGENCY = "SELECT * FROM agency";
	static final String SELECT_AGENCY_ID = "SELECT id FROM agency WHERE id=? && name='?'";
	static final String INSERT_AGENCY = "INSERT INTO agency(name,location_id) "
			+ "VALUES ('?' , ?)";
	static final String UPDATE_AGENCY = "UPDATE agency SET name='?',location_id=? "
			+ "WHERE agency.id=?";
	static final String DELETE_AGENCY = "DELETE FROM agency WHERE agency.id=? && agency.name='?'";
	
	static final String SELECT_CAR = "SELECT * FROM car";
	static final String SELECT_CAR_ID = "SELECT id FROM car WHERE car.license_plate='?'";
	static final String INSERT_CAR = "INSERT INTO car(model,license_plate,km,category_id,agency_id,"
			+ "status_id) VALUES ('?','?',?,'?',?,?)";
	static final String UPDATE_CAR = "UPDATE car SET model='?',license_plate='?',km=?,category_id=?,"
			+ "agency_id=?,status_id=? WHERE car.license_plate='?'";
	static final String DELETE_CAR = "DELETE FROM car WHERE license_plate= '?'";
	
	static final String SELECT_CUSTOMER = "SELECT * FROM customer";
	static final String SELECT_CUSTOMER_ID = "SELECT id FROM customer WHERE first_name='?' && last_name='?' "
			+ "&& telephone='?' && email='?'";
	static final String INSERT_CUSTOMER = "INSERT INTO customer(first_name,last_name,telephone,email)"
			+ "VALUES('?','?','?','?')";
	static final String UPDATE_CUSTOMER = "UPDATE customer SET first_name='?',last_name='?',telephone='?',"
			+ "email='?' WHERE id=?";
	static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE first_name='?' && last_name='?' && "
			+ "telephone='?' && email='?'";

	static final String SELECT_OPERATOR = "SELECT * FROM operator";
	static final String SELECT_OPERATOR_ID = "SELECT id FROM operator WHERE username='?'";
	static final String INSERT_OPERATOR = "INSERT INTO operator(first_name,last_name,email,username,password)"
			+ "VALUES('?','?','?','?','?')";
	static final String UPDATE_OPERATOR = "UPDATE operator SET first_name='?',last_name='?',email='?',"
			+ "username='?',password='?' WHERE username='?'";
	static final String DELETE_OPERATOR = "DELETE FROM operator WHERE username='?'";
	
	static final String SELECT_PAYMENT = "SELECT * FROM payment";
	static final String SELECT_PAYMENT_ID = "SELECT id FROM payment WHERE  amount=? && date='?'";
	static final String INSERT_PAYMENT = "INSERT INTO payment(amount,date,type_id,currency_id,contract_id) "
			+ "VALUES(?,'?',?,?,?)";

	static final String SELECT_CONTRACT = "SELECT * FROM contract";
	static final String SELECT_CONTRACT_FROM_ID = "SELECT * FROM contract WHERE id=?";
	static final String SELECT_CONTRACT_PAYMENTS = "SELECT id FROM payment WHERE contract_id=?";
	static final String SELECT_CONTRACT_MAX_ID = "select MAX(id) from contract";
	static final String INSERT_CONTRACT = "INSERT INTO contract(start,end,km,km_limit,open,operator_id,"
			+ "customer_id,start_agency_id,end_agency_id,car_id,type_id,deposit) VALUES "
			+ "('?','?',?,?,?,?,?,?,?,?,?,?)";
	static final String UPDATE_CONTRACT = "UPDATE contract SET start='?',end='?',km=?,km_limit=?,open=?,"
			+ "operator_id=?,customer_id=?,start_agency_id=?,end_agency_id=?,car_id=?,type_id=?,deposit=? "
			+ "WHERE contract.id=?";
	static final String DELETE_CONTRACT = "DELETE FROM contract WHERE id=?";
}
