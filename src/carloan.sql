CREATE database CarloanDB;
USE CarloanDB;

#create a new user username: CarloanUser, password: carloan
CREATE USER 'CarloanUser'@'localhost' IDENTIFIED BY 'carloan';
GRANT CREATE, SELECT, INSERT, DELETE ON CarloanDB.* TO CarloanUser@localhost IDENTIFIED BY 'carloan';

CREATE TABLE customer(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    telephone VARCHAR(20) DEFAULT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
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
    FOREIGN KEY (location_id) REFERENCES location(id)
    ON UPDATE CASCADE
);

CREATE TABLE car_status (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(26) NOT NULL
);

CREATE TABLE car_category (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(1) NOT NULL
);

CREATE TABLE car (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(10) NOT NULL,
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
    type VARCHAR(10) NOT NULL
);

CREATE TABLE contract( 
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    contract_no INTEGER(6) NOT NULL,
    start DATE NOT NULL,
    return_limit DATE NOT NULL,
    km_limit BOOLEAN NOT NULL,
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

#populate tables
INSERT INTO customer(name, surname, telephone, email, username, password)
    VALUES("Giacomo", "Tizio", "3802539904", "giacomo.tizio@gmail.com", "giacomotizio", "giacomotizio");
INSERT INTO customer(name, surname, telephone, email, username, password) 
    VALUES("Alberto", "Forensi", "3490872294", "alberto.forensi@gmail.com", "albertoforensi", "albertoforensi");
INSERT INTO customer(name, surname, telephone, email, username, password) 
    VALUES("Lorenzo", "Ganioni", "3478940937", "lorenzo.ganioni@gmail.com", "lorenzoganioni", "lorenzoganioni");
INSERT INTO customer(name, surname, telephone, email, username, password) 
    VALUES("Giancarlo", "Fiono", "3809587746", "giancarlo.fiono@gmail.com", "giancarlofiono", "giancarlofiono");
INSERT INTO customer(name, surname, telephone, email, username, password) 
    VALUES("Fiorenzo", "Porto", "3469387594", "fiorenzo.porto@gmail.com", "fiorenzoporto", "fiorenzoporto");

INSERT INTO location(city, cap, address1) VALUES ("Lecce", 73100, "Via Toma, 43");
INSERT INTO location(city, cap, address1, address2) VALUES ("Milano", 20121, "Viale Loreto, 109", "Via Lombardi, 79");
INSERT INTO location(city, cap, address1) VALUES ("Firenze", 50121, "Via Della Scienza, 4");
INSERT INTO location(city, cap, address1) VALUES ("Venezia", 73100, "Via Tizio, 392");
INSERT INTO location(city, cap, address1) VALUES ("Bologna", 30010, "Via Duca, 6");
INSERT INTO location(city, cap, address1) VALUES ("Milano", 20121, "Viale Marche, 80");

INSERT INTO agency(name, location_id) VALUES ("CarLoan Lecce", 1);
INSERT INTO agency(name, location_id) VALUES ("CarLoan Firenze", 3);
INSERT INTO agency(name, location_id) VALUES ("CarLoan Milano", 6);

INSERT INTO car_status(status) VALUES ("avaible");
INSERT INTO car_status(status) VALUES ("hired");
INSERT INTO car_status(status) VALUES ("routine maintenance");
INSERT INTO car_status(status) VALUES ("emergency maintenance");

INSERT INTO car_category(category) VALUES ("A");
INSERT INTO car_category(category) VALUES ("B");
INSERT INTO car_category(category) VALUES ("C");
INSERT INTO car_category(category) VALUES ("D");

INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("MI293RL", 1003, 1, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("BN930LE", 9038, 2, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("BO857493", 2839, 3, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("VE498DJ", 3748, 2, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("RE4637J", 2039, 1, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("GO283KF", 1003, 3, 1);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("PR8273H", 2837, 4, 4);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("GO940IJ", 4039, 4, 2);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("PZ748JI", 2837, 2, 2);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("UD049LB", 4958, 1, 4);
INSERT INTO car(license_plate, last_km, car_category_id, car_status_id)
    VALUES ("GE094OK", 2739, 3, 3);

INSERT INTO payment_type(type) VALUES ("cash");
INSERT INTO payment_type(type) VALUES ("credit/debit card");

INSERT INTO currency(type, symbol) VALUES ("euro", "€");
INSERT INTO currency(type, symbol) VALUES ("dollar", "$");
INSERT INTO currency(type, symbol) VALUES ("pund", "£");

INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (300, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (230, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (450, 2, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (950, 2, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (120, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (120, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (300, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (300, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (220, 1, 1);
INSERT INTO payment(amount, payment_type_id, currency_id) VALUES (320, 1, 1);

INSERT INTO contract_type(type) VALUES("one-day pass");
INSERT INTO contract_type(type) VALUES("one-week pass");

INSERT INTO contract(contract_no, start, return_limit, km_limit, end, end_km, customer_id, agency_id, return_agency_id, car_id, contract_type_id, total_payment_id, deposit_id)
    VALUES(1, "2015-04-13", "2015-04-18", true, NULL, NULL, 1, 1, 1, 8, 2, 1, 2);
INSERT INTO contract(contract_no, start, return_limit, km_limit, end, end_km, customer_id, agency_id, return_agency_id, car_id, contract_type_id, total_payment_id, deposit_id)
    VALUES(2, "2015-03-13", "2015-03-13", false, NULL, NULL, 2, 2, 2, 9, 2, 3, 4);
INSERT INTO contract(contract_no, start, return_limit, km_limit, end, end_km, customer_id, agency_id, return_agency_id, car_id, contract_type_id, total_payment_id, deposit_id)
    VALUES(3, "2015-5-23", "2015-5-26", true, 2015-5-26, 1002, 3, 3, 3, 2, 1, 5, 6);
INSERT INTO contract(contract_no, start, return_limit, km_limit, end, end_km, customer_id, agency_id, return_agency_id, car_id, contract_type_id, total_payment_id, deposit_id)
    VALUES(4, "2015-2-01", "2015-2-08", true, 2015-2-08, 2030, 4, 4, 3, 3, 1, 7, 8);
INSERT INTO contract(contract_no, start, return_limit, km_limit, end, end_km, customer_id, agency_id, return_agency_id, car_id, contract_type_id, total_payment_id, deposit_id)
    VALUES(5, "2015-3-15", "2015-3-18", true, 2015-3-18, 500, 5, 1, 1, 4, 1, 8, 9);

#creating views
CREATE VIEW agency_location_join AS SELECT agency.name, location.city, location.cap, location.address1 as "address" FROM agency LEFT JOIN location ON agency.location_id=location.id;

CREATE VIEW car_status_join AS SELECT car.license_plate, car_status.status FROM car LEFT JOIN car_status ON car.car_status_id=car_status.id;

CREATE VIEW car_category_join AS SELECT car.license_plate, car_category.category FROM car LEFT JOIN car_category ON car.car_category_id=car_category.id;

CREATE VIEW avaible_car AS SELECT car.license_plate, car_status.status FROM car INNER JOIN car_status ON car.car_status_id=car_status.id && car_status.status="avaible";

CREATE VIEW hired_car AS SELECT car.license_plate, car_status.status FROM car INNER JOIN car_status ON car.car_status_id=car_status.id && car_status.status="hired";

CREATE VIEW emergency_maintenance_car AS SELECT car.license_plate, car_status.status FROM car INNER JOIN car_status ON car.car_status_id=car_status.id && car_status.status="emergency maintenance";

CREATE VIEW routine_maintenance_car AS SELECT car.license_plate, car_status.status FROM car INNER JOIN car_status ON car.car_status_id=car_status.id && car_status.status="routine maintenance";

CREATE VIEW car_category_status_join AS SELECT car.license_plate, car.last_km, car_status.status, car_category.category FROM car LEFT JOIN car_status ON car.car_status_id=car_status.id LEFT JOIN car_category on car.car_category_id=car_category.id;

commit;
