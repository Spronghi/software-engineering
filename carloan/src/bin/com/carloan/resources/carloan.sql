-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: carloan
-- ------------------------------------------------------
-- Server version	5.6.27-0ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) NOT NULL,
  `postal_no` varchar(5) NOT NULL,
  `road` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agency`
--

DROP TABLE IF EXISTS `agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agency` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `location_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `agency_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operator`
--

DROP TABLE IF EXISTS `operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operator` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` text NOT NULL,
  `agency_id` int(10) NOT NULL,
  `admin` boolean NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `agency_id` (`agency_id`),
  CONSTRAINT `operator_ibfk_1` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car_category`
--

DROP TABLE IF EXISTS `car_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category` varchar(1) NOT NULL,
  `base_rate_limit` float(5,2) NOT NULL,
  `base_rate_unlimit` float(5,2) NOT NULL,
  `km_rate` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car_status`
--

DROP TABLE IF EXISTS `car_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_status` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `status` varchar(26) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `car_category`
--

LOCK TABLES `car_category` WRITE;
/*!40000 ALTER TABLE `car_category` DISABLE KEYS */;
INSERT INTO `car_category` VALUES (1,'A',500.00,700.00,0.50),(2,'B',270.00,400.00,0.40),(3,'C',120.00,200.00,0.30),(4,'D',40.00,100.00,0.10);
/*!40000 ALTER TABLE `car_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `car_status`
--

LOCK TABLES `car_status` WRITE;
/*!40000 ALTER TABLE `car_status` DISABLE KEYS */;
INSERT INTO `car_status` VALUES (1,'avaible'),(2,'hired'),(3,'routine maintenance'),(4,'emergency maintenance');
/*!40000 ALTER TABLE `car_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_type`
--

DROP TABLE IF EXISTS `contract_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_type`
--

LOCK TABLES `contract_type` WRITE;
/*!40000 ALTER TABLE `contract_type` DISABLE KEYS */;
INSERT INTO `contract_type` VALUES (1,'one-day pass'),(2,'one-week pass');
/*!40000 ALTER TABLE `contract_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `symbol` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'euro','€'),(2,'dollar','$'),(3,'pound','£');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `operator`
--

LOCK TABLES `operator` WRITE;
/*!40000 ALTER TABLE `operator` DISABLE KEYS */;
INSERT INTO `operator` VALUES (1,'Simone','Colaci','sim.colaci@gmail.com','admin','1000:6a6176612e73656375726974792e53656375726552616e646f6d40353531613165:b17c3be00670aa0734d08ab4ff1c2761a56f4b9dd8ddabe2b973dceb2974c99567a5f91e0757ab2d6cc0c5c8474630a682f870acdc933365030c07a0428eb2b8', 2, 1),(7,'Simone','Colaci','sim.colaci@gmail.com','operator','1000:6a6176612e73656375726974792e53656375726552616e646f6d403736363065386339:611e2480cfcdd134fd24a8e8336ad420270f99a827bf849871359861ff3eed7468485b148de61cb63c92c4d6a39831ef11fa040c729c08b37f0ab7677b35e2be',3,0);
/*!40000 ALTER TABLE `operator` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_type`
--

LOCK TABLES `payment_type` WRITE;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
INSERT INTO `payment_type` VALUES (1,'cash'),(2,'credit/debit card');
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-25 15:42:25
