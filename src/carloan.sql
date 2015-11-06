CREATE database IF NOT EXISTS carloan;
USE carloan;

CREATE USER 'CarloanUser'@'localhost' IDENTIFIED BY 'popo';
GRANT CREATE, SELECT, INSERT, DELETE, UPDATE ON carloan.* TO CarloanUser@localhost IDENTIFIED BY 'popo';

CREATE TABLE IF NOT EXISTS operator(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL
);
CREATE TABLE IF NOT EXISTS customer(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    telephone VARCHAR(20) DEFAULT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS location(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    postal_no VARCHAR(5) NOT NULL,
    road VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS agency (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location_id INTEGER(10) NOT NULL,
    FOREIGN KEY (location_id) REFERENCES location(id)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS car_status (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(26) NOT NULL
);

CREATE TABLE IF NOT EXISTS car_category (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(1) NOT NULL,
    base_rate_limit FLOAT(5,2) NOT NULL,
    base_rate_unlimit FLOAT(5,2) NOT NULL,
    km_rate FLOAT(5,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS car (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(200) NOT NULL,
    license_plate VARCHAR(10) NOT NULL,
    km INTEGER(10) NOT NULL,
    category_id INTEGER(10) NOT NULL,
    agency_id INTEGER(10) NOT NULL,
    status_id INTEGER(10) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES car_category(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (agency_id) REFERENCES agency(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (status_id) REFERENCES car_status(id)
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS contract_type (
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(15) NOT NULL
);

CREATe TABLE IF NOT EXISTS payment_type(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS currency(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    symbol char NOT NULL
);

CREATE TABLE IF NOT EXISTS contract( 
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start DATE NOT NULL,
    end DATE NOT NULL,
    km INTEGER(10) NOT NULL,
    km_limit BOOLEAN NOT NULL,
    open BOOLEAN DEFAULT TRUE NOT NULL,
    operator_id INTEGER(10) NOT NULL,
    customer_id INTEGER(10) NOT NULL,
    start_agency_id INTEGER(10) NOT NULL,
    end_agency_id INTEGER(10) NOT NULL,
    car_id INTEGER(10) NOT NULL,
    type_id INTEGER(10) NOT NULL,
    deposit DECIMAL(7,2) NOT NULL,
    FOREIGN KEY (operator_id) REFERENCES operator(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (start_agency_id) REFERENCES agency(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (end_agency_id) REFERENCES agency(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(id)
    ON UPDATE CASCADE,
    FOREIGN KEY (type_id) REFERENCES contract_type(id)
);
CREATE TABLE IF NOT EXISTS payment(
    id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(7,2) NOT NULL,
    date TIMESTAMP NOT NULL,
    type_id INTEGER(10) NOT NULL,
    currency_id INTEGER(10) NOT NULL,
    contract_id INTEGER(10) NOT NULL,
    FOREIGN KEY (type_id) REFERENCES payment_type(id),
    FOREIGN KEY (currency_id) REFERENCES currency(id),
    FOREIGN KEY (contract_id) REFERENCES contract(id)
);
INSERT INTO operator(first_name, last_name, email, username, password)
    VALUES("Giacomo", "Tizio", "giacomo.tizio@gmail.com", "giacomotizio", "giacomotizio");
INSERT INTO operator(first_name, last_name, email, username, password) 
    VALUES("Alberto", "Forensi", "alberto.forensi@gmail.com", "albertoforensi", "albertoforensi");
INSERT INTO operator(first_name, last_name, email, username, password) 
    VALUES("Lorenzo", "Ganioni", "lorenzo.ganioni@gmail.com", "lorenzoganioni", "lorenzoganioni");
INSERT INTO operator(first_name, last_name, email, username, password) 
    VALUES("Giancarlo", "Fiono", "giancarlo.fiono@gmail.com", "giancarlofiono", "giancarlofiono");
INSERT INTO operator(first_name, last_name, email, username, password) 
    VALUES("Fiorenzo", "Porto", "fiorenzo.porto@gmail.com", "fiorenzoporto", "fiorenzoporto");

INSERT INTO customer(first_name, last_name, telephone, email) 
    VALUES("Trono", "Di Spade", "3401983374", "trono.dispade@gmail.com");
INSERT INTO customer(first_name, last_name, telephone, email) 
    VALUES("Giacomo", "Malatesta", "3890496578", "giacomo.malatesta@gmail.com");
INSERT INTO customer(first_name, last_name, telephone, email) 
    VALUES("Silvio", "Quintino", "3809874235", "silvio.quintino@gmail.com");
INSERT INTO customer(first_name, last_name, telephone, email) 
    VALUES("Sergio", "Gatto", "3289387485", "sergio.gatto@gmail.com");
INSERT INTO customer(first_name, last_name, telephone, email) 
    VALUES("Mario", "Zio Frank", "3890298493", "mario.ziofrank@gmail.com");

INSERT INTO location(city, postal_no, road) VALUES ("Lecce", 73100, "Via Toma, 43");
INSERT INTO location(city, postal_no, road) VALUES ("Firenze", 50121, "Via Della Scienza, 4");
INSERT INTO location(city, postal_no, road) VALUES ("Milano", 20121, "Viale Loreto, 109");
INSERT INTO location(city, postal_no, road) VALUES ("Venezia", 73100, "Via Tizio, 392");
INSERT INTO location(city, postal_no, road) VALUES ("Genova", 30010, "Via Duca, 6");

INSERT INTO agency(name, location_id) VALUES ("CarLoan Lecce", 1);
INSERT INTO agency( name, location_id) VALUES ("CarLoan Firenze", 2);
INSERT INTO agency( name, location_id) VALUES ("CarLoan Milano", 3);
INSERT INTO agency( name, location_id) VALUES ("CarLoan Venezia", 4);
INSERT INTO agency( name, location_id) VALUES ("CarLoan Genova", 5);

INSERT INTO car_status(status) VALUES ("avaible");
INSERT INTO car_status(status) VALUES ("hired");
INSERT INTO car_status(status) VALUES ("routine maintenance");
INSERT INTO car_status(status) VALUES ("emergency maintenance");

INSERT INTO car_category(category, base_rate_limit, base_rate_unlimit, km_rate) 
    VALUES ("A", 500, 700, 0.50);
INSERT INTO car_category(category, base_rate_limit, base_rate_unlimit, km_rate) 
    VALUES ("B", 270, 400, 0.40);
INSERT INTO car_category(category, base_rate_limit, base_rate_unlimit, km_rate) 
    VALUES ("C", 120, 200, 0.30);
INSERT INTO car_category(category, base_rate_limit, base_rate_unlimit, km_rate) 
    VALUES ("D", 40, 100, 0.10);

INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Audi TT", "MI293RL", 1003, 1, 1, 1);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Alfa Romeo 4C", "BN930LE", 9038, 2, 2, 2);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Seat Lèon", "BO857493", 2839, 3, 3, 1);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Jeep Grand Cherokee", "VE498DJ", 3748, 2, 4, 2);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("BMW Serie 1","RE4637J", 2039, 1, 5, 1);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Alfa Romeo Giulietta", "GO283KF", 1003, 3, 1, 1);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Toyota Yaris", "PR8273H", 2837, 4, 2, 4);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Nissan Micra", "GO940IJ", 4039, 4, 3, 2);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Mercedes CLA", "PZ748JI", 2837, 2, 4, 2);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("BMW X3", "UD049LB", 4958, 1, 5, 4);
INSERT INTO car(model, license_plate, km, category_id, agency_id, status_id)
    VALUES ("Honda Civic", "GE094OK", 2739, 3, 1, 3);

INSERT INTO payment_type(type) VALUES ("cash");
INSERT INTO payment_type(type) VALUES ("credit/debit card");

INSERT INTO currency(type, symbol) VALUES ("euro", "€");
INSERT INTO currency(type, symbol) VALUES ("dollar", "$");
INSERT INTO currency(type, symbol) VALUES ("pound", "£");

INSERT INTO contract_type(type) VALUES("one-day pass");
INSERT INTO contract_type(type) VALUES("one-week pass");

INSERT INTO contract(start,end,km,km_limit,open,operator_id,customer_id,start_agency_id,end_agency_id,car_id,type_id,deposit) VALUES ("2015-04-02","2015-04-10",400,false,false,1,1,1,1,1,1,2621.00);

INSERT INTO payment(amount,date, type_id, currency_id, contract_id) VALUES (2500.50,'2015-04-02 12:32:10',1,1,1);
INSERT INTO payment(amount,date, type_id, currency_id, contract_id) VALUES (120.50,'2015-04-10 10:21:10',1,1,1);

