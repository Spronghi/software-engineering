package com.carloan.integration.database;

class Constants {
	static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS carloan";
	static final String CREATE_OPERATOR = "CREATE TABLE IF NOT EXISTS carloan.operator( "
			+ "id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,first_name VARCHAR(50) NOT NULL,"
			+ "last_name VARCHAR(50) NOT NULL,email VARCHAR(100) NOT NULL,username VARCHAR(200) NOT NULL,"
			+ "password VARCHAR(200) NOT NULL)";
	static final String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS carloan.customer(id INTEGER(10) NOT NULL "
			+ "AUTO_INCREMENT PRIMARY KEY,first_name VARCHAR(50) NOT NULL,last_name VARCHAR(50) NOT NULL,"
			+ "telephone VARCHAR(20) DEFAULT NULL,email VARCHAR(100) NOT NULL)";
	static final String CREATE_LOCATION = "CREATE TABLE IF NOT EXISTS carloan.location(id INTEGER(10) NOT "
			+ "NULL AUTO_INCREMENT PRIMARY KEY,city VARCHAR(100) NOT NULL,postal_no VARCHAR(5) NOT NULL,"
			+ "road VARCHAR(500) NOT NULL)";
	static final String CREATE_AGENCY = "CREATE TABLE IF NOT EXISTS carloan.agency (id INTEGER(10) NOT NULL "
			+ "AUTO_INCREMENT PRIMARY KEY,name VARCHAR(50) NOT NULL, location_id INTEGER(10) NOT NULL,"
			+ "FOREIGN KEY (location_id) REFERENCES location(id)ON UPDATE CASCADE)";
	static final String CREATE_CAR_STATUS = "CREATE TABLE IF NOT EXISTS carloan.car_status (id INTEGER(10)"
			+ "NOT NULL AUTO_INCREMENT PRIMARY KEY,status VARCHAR(26) NOT NULL)";
	static final String CREATE_CAR_CATEGORY="CREATE TABLE IF NOT EXISTS carloan.car_category (id INTEGER(10)"
			+ " NOT NULL AUTO_INCREMENT PRIMARY KEY,category VARCHAR(1) NOT NULL,base_rate_limit "
			+ "FLOAT(5,2) NOT NULL,base_rate_unlimit FLOAT(5,2) NOT NULL,km_rate FLOAT(5,2) NOT NULL)";
	static final String CREATE_CAR="CREATE TABLE IF NOT EXISTS "
			+ "carloan.car (id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,model VARCHAR(200) NOT NULL,license_plate "
			+ "VARCHAR(10) NOT NULL,km INTEGER(10) NOT NULL,category_id INTEGER(10) NOT NULL,agency_id INTEGER(10) "
			+ "NOT NULL,status_id INTEGER(10) NOT NULL,FOREIGN KEY (category_id) REFERENCES car_category(id)ON "
			+ "UPDATE CASCADE,FOREIGN KEY (agency_id) REFERENCES agency(id)ON UPDATE CASCADE,FOREIGN KEY (status_id) "
			+ "REFERENCES car_status(id)ON UPDATE CASCADE)";
	static final String CREATE_CONTRACT_TYPE="CREATE TABLE IF NOT EXISTS carloan.contract_type (id INTEGER(10) NOT NULL "
			+ "AUTO_INCREMENT PRIMARY KEY,type VARCHAR(15) NOT NULL)";
	static final String CREATE_PAYMENT_TYPE = "CREATE TABLE IF NOT EXISTS carloan.payment_type(id INTEGER(10) NOT NULL "
			+ "AUTO_INCREMENT PRIMARY KEY,type VARCHAR(50) NOT NULL)";
	static final String CREATE_CURRENCY = "CREATE TABLE  IF NOT EXISTS carloan.currency(id INTEGER(10) NOT "
			+ "NULL AUTO_INCREMENT PRIMARY KEY,type VARCHAR(50) NOT NULL,symbol char NOT NULL)";
	static final String CREATE_CONTRACT = "CREATE TABLE IF NOT EXISTS contract( id INTEGER(10) NOT NULL "
			+ "AUTO_INCREMENT PRIMARY KEY,start DATE NOT NULL,end DATE NOT NULL,km INTEGER(10) NOT NULL,km_limit "
			+ "BOOLEAN NOT NULL,open BOOLEAN DEFAULT TRUE NOT NULL,operator_id INTEGER(10) NOT NULL,customer_id "
			+ "INTEGER(10) NOT NULL,start_agency_id INTEGER(10) NOT NULL,end_agency_id INTEGER(10) NOT NULL,"
			+ " car_id INTEGER(10) NOT NULL, type_id INTEGER(10) NOT NULL,deposit DECIMAL(7,2) NOT NULL,"
			+ " FOREIGN KEY (operator_id) REFERENCES operator(id) ON UPDATE CASCADE,FOREIGN KEY (customer_id) "
			+ "REFERENCES customer(id)ON UPDATE CASCADE,FOREIGN KEY (start_agency_id) REFERENCES agency(id)"
			+ "ON UPDATE CASCADE,FOREIGN KEY (end_agency_id) REFERENCES agency(id)ON UPDATE CASCADE,"
			+ "FOREIGN KEY (car_id) REFERENCES car(id)ON UPDATE CASCADE,FOREIGN KEY (type_id) REFERENCES contract_type(id))";
	static final String CREATE_PAYMENT= "CREATE TABLE IF NOT EXISTS carloan.payment( id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
			+ "amount DECIMAL(7,2) NOT NULL,date TIMESTAMP NOT NULL,type_id INTEGER(10) NOT NULL,currency_id INTEGER(10) NOT NULL,"
			+ "contract_id INTEGER(10) NOT NULL,FOREIGN KEY (type_id) REFERENCES payment_type(id),FOREIGN KEY (currency_id) "
			+ "REFERENCES currency(id),FOREIGN KEY (contract_id) REFERENCES contract(id))";
}
