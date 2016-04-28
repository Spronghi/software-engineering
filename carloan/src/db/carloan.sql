
CREATE USER 'carloan_user'@'localhost' IDENTIFIED BY 'popo';
GRANT DELETE, INSERT, SELECT, UPDATE ON carloan.* TO `carloan_user`@`localhost`;

CREATE DATABASE `carloan`;
USE `carloan`;

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

LOCK TABLES `customer` WRITE;
INSERT INTO `customer` VALUES (1,'Trono','Di Spade','3401983374','trono.dispade@gmail.com'),(2,'Giacomo','Malatesta','3890496578','giacomo.malatesta@gmail.com'),(5,'Mario','Zio Frank','3890298493','mario.ziofrank@gmail.com'),(7,'Giuliano','Cosenza','3289309483','giulioc@gmail.com'),(8,'Fabrizio','Cielo','38903920321','fabrizio.c@gmail.com');
UNLOCK TABLES;

DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) NOT NULL,
  `postal_no` varchar(5) NOT NULL,
  `road` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

LOCK TABLES `location` WRITE;
INSERT INTO `location` VALUES (1,'Lecce','73100','Via Toma, 43'),(2,'Firenze','50121','Via Della Scienza, 4'),(6,'asdasd','asda','asd'),(7,'asd','asds','asdas');
UNLOCK TABLES;

DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `location_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `agency_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

LOCK TABLES `agency` WRITE;
INSERT INTO `agency` VALUES (1,'CarLoan Lecce',1),(2,'CarLoan Firenze',2);
UNLOCK TABLES;

DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` text NOT NULL,
  `agency_id` int(10) NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `agency_id` (`agency_id`),
  CONSTRAINT `operator_ibfk_1` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

LOCK TABLES `operator` WRITE;
INSERT INTO `operator` VALUES (1,'Simone','Colaci','sim.colaci@gmail.com','admin_firenze','1000:6a6176612e73656375726974792e53656375726552616e646f6d403764333330636333:b847fb95ae7594953a89368e87b7c654fdd88c2fbe111ea68f52269e1496c527bb7bbc037f6829a141949ef0dc14fcf407ebd6e69a7be43abcd31100b29e9519',2,1),(7,'Francesco','Pinocchio','francesco.pinocchio@gmail.com','operator_lecce','1000:6a6176612e73656375726974792e53656375726552616e646f6d4061336230306433:922a5fefa82b5d0b204055e4b1f5a3c852912904f0dedad7ea4403dd793c945e3b29ced4075060721f98d1b9e22034472aeb1c908311e7595d58a60fb55c0377',1,0),(8,'Giulio','Ponzetta','giulio.ponzetta@gmail.com','admin_lecce','1000:6a6176612e73656375726974792e53656375726552616e646f6d403539333732363830:4666764d8f4a9b9a1f01ffdf2588f0b91251f881119f875a3e4ae6fb471f4f6d1f659bf93fa9c5f3359905e531b87cea79a9feb9c56fcee0124fd6111f1f8eb9',1,1),(9,'Fiorenzo','Pandemonio','fiorenzo.pandemonio@gmail.com','operator_firenze','1000:6a6176612e73656375726974792e53656375726552616e646f6d403363623361613737:bd695fb8acff2e602325f941480f7ae75161967cf01a7527365d2208474521e1f05f125ccd83cbdcb7262604a18e4f46e50e782d262ce93f7b220842f2c24251',2,0);
UNLOCK TABLES;

DROP TABLE IF EXISTS `car_category`;
CREATE TABLE `car_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category` varchar(1) NOT NULL,
  `base_rate_limit` float(5,2) NOT NULL,
  `base_rate_unlimit` float(5,2) NOT NULL,
  `km_rate` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

LOCK TABLES `car_category` WRITE;
INSERT INTO `car_category` VALUES (1,'A',250.00,350.00,0.25),(2,'B',135.00,200.00,0.20),(3,'C',60.00,100.00,0.15),(4,'D',20.00,50.00,0.05);
UNLOCK TABLES;

DROP TABLE IF EXISTS `car_status`;
CREATE TABLE `car_status` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `status` varchar(26) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

LOCK TABLES `car_status` WRITE;
INSERT INTO `car_status` VALUES (1,'avaible'),(2,'hired'),(3,'routine maintenance'),(4,'emergency maintenance');
UNLOCK TABLES;

DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `model` varchar(200) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
  `km` int(10) NOT NULL,
  `category_id` int(10) NOT NULL,
  `agency_id` int(10) NOT NULL,
  `status_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `agency_id` (`agency_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `car_category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `car_ibfk_2` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `car_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `car_status` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

LOCK TABLES `car` WRITE;
INSERT INTO `car` VALUES (1,'Audi TT','MI29ARL',1003,1,1,1),(2,'Alfa Romeo 4C','BN93SLE',9048,2,1,1),(3,'Seat Lèon','BO85FSD',2839,3,2,1),(4,'Jeep Grand Cherokee','VE49XCJ',3748,2,1,1),(5,'BMW Serie 1','RE46VCX',2039,1,2,1),(6,'Alfa Romeo Giulietta','GO28WKF',1103,3,2,1),(7,'Toyota Yaris','PR82FYH',2837,4,2,1),(8,'Nissan Micra','GO94JIJ',4039,4,2,1),(9,'Mercedes CLA','PZ74TJI',2837,2,1,1),(10,'BMW X3','UD04NLB',4958,1,2,1),(11,'Honda Civic','GE09HOO',2739,3,2,1);
UNLOCK TABLES;

DROP TABLE IF EXISTS `contract_type`;
CREATE TABLE `contract_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `contract_type` WRITE;
INSERT INTO `contract_type` VALUES (1,'one-day pass'),(2,'one-week pass');
UNLOCK TABLES;

DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `km` int(10) NOT NULL,
  `km_limit` tinyint(1) NOT NULL,
  `open` tinyint(1) NOT NULL DEFAULT '1',
  `operator_id` int(10) NOT NULL,
  `customer_id` int(10) NOT NULL,
  `start_agency_id` int(10) NOT NULL,
  `end_agency_id` int(10) NOT NULL,
  `car_id` int(10) NOT NULL,
  `type_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `operator_id` (`operator_id`),
  KEY `customer_id` (`customer_id`),
  KEY `start_agency_id` (`start_agency_id`),
  KEY `end_agency_id` (`end_agency_id`),
  KEY `car_id` (`car_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`operator_id`) REFERENCES `operator` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_3` FOREIGN KEY (`start_agency_id`) REFERENCES `agency` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_4` FOREIGN KEY (`end_agency_id`) REFERENCES `agency` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_5` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_6` FOREIGN KEY (`type_id`) REFERENCES `contract_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

LOCK TABLES `contract` WRITE;
INSERT INTO `contract` VALUES (15,'2016-02-24','2016-02-25',100,0,0,8,5,1,2,6,1),(16,'2016-02-25','2016-02-26',10,1,0,7,2,1,1,2,1);
UNLOCK TABLES;

DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `symbol` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

LOCK TABLES `currency` WRITE;
INSERT INTO `currency` VALUES (1,'euro','€'),(2,'dollar','$'),(3,'pound','£');
UNLOCK TABLES;

DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `payment_type` WRITE;
INSERT INTO `payment_type` VALUES (1,'cash'),(2,'credit/debit card');
UNLOCK TABLES;

DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `amount` decimal(7,2) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type_id` int(10) NOT NULL,
  `currency_id` int(10) NOT NULL,
  `contract_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `currency_id` (`currency_id`),
  KEY `contract_id` (`contract_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `payment_type` (`id`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
  CONSTRAINT `payment_ibfk_3` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

LOCK TABLES `payment` WRITE;
INSERT INTO `payment` VALUES (20,33.33,'2016-02-24 19:24:07',1,1,15),(21,45.00,'2016-02-25 14:31:01',1,1,16),(22,92.00,'2016-02-25 14:34:47',1,1,16),(23,66.67,'2016-02-25 14:35:30',2,1,15);
UNLOCK TABLES;

