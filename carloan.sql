CREATE database carloan;
USE carloan

CREATE TABLE customer(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    telephone INTEGER(20) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    username VARCHAR(100) DEFAULT NULL,
    password VARCHAR(100) DEFAULT NULL
);

CREATE TABLE location(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    cap VARCHAR(5) NOT NULL,
    address1 VARCHAR(500) NOT NULL,
    address2 VARCHAR(500) DEFAULT NULL,
    address3 VARCHAR(500) DEFAULT NULL
);

CREATE TABLE agency (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location_id INTEGER(10) NOT NULL,
    FOREIGN KEY (location_id) REFERENCES customer(id)
    ON UPDATE CASCADE
);

CREATE TABLE car_status (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(26) DEFAULT NULL
);

CREATE TABLE car_category (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(1) NOT NULL
);

CREATE TABLE car (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(7) NOT NULL,
    last_km INTEGER(10) NOT NULL,
    car_category_id INTEGER(10) NOT NULL,
    car_status_id INTEGER(10) NOT NULL,
    FOREIGN KEY (car_category_id) REFERENCES car_category(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (car_status_id) REFERENCES car_status(id)
    ON UPDATE CASCADE
);

CREATE TABLE payment_type (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE currency(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    symbol VARCHAR(1) NOT NULL
);

CREATE TABLE payment(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount INTEGER(10) NOT NULL,
    payment_type_id INTEGER(10) NOT NULL,
    currency_id INTEGER(10) NOT NULL,
    FOREIGN KEY(payment_type_id) REFERENCES payment_type(id),
    FOREIGN KEY(currency_id) REFERENCES currency(id)
);

CREATE TABLE contract_type (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    base VARCHAR(11) NOT NULL,
    km_limit BOOLEAN NOT NULL
);

CREATE TABLE contract( 
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code INTEGER(6) NOT NULL,
    start DATE NOT NULL,
    return_limit DATE NOT NULL,
    end DATE DEFAULT NULL,
    end_km INTEGER(10) DEFAULT NULL,
    customer_id INTEGER(10) NOT NULL,
    agency_id INTEGER(10) NOT NULL,
    return_agency_id INTEGER(10) NOT NULL,
    car_id INTEGER(10) NOT NULL,
    contract_type_id INTEGER(10) NOT NULL,
    total_payment_id INTEGER(10) NOT NULL,
    deposit_id INTEGER(10) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (agency_id) REFERENCES agency(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (return_agency_id) REFERENCES agency(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (contract_type_id) REFERENCES contract_type(id),
    FOREIGN KEY (total_payment_id) REFERENCES payment(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (deposit_id) REFERENCES payment(id)
    ON UPDATE CASCADE
);

