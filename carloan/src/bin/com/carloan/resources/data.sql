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
-- Dumping data for table `agency`
--

LOCK TABLES `agency` WRITE;
/*!40000 ALTER TABLE `agency` DISABLE KEYS */;
INSERT INTO `agency` VALUES (1, 'CarLoan Lecce',1),(2,'CarLoan Firenze',2),(3,'CarLoan Milano',3),(4,'CarLoan Venezia',4),(5,'CarLoan Genova',5);
/*!40000 ALTER TABLE `agency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (1,'Audi TT','MI29ARL',1003,1,1,1),(2,'Alfa Romeo 4C','BN93SLE',9038,2,2,1),(3,'Seat Lèon','BO85FSD',2839,3,3,1),(4,'Jeep Grand Cherokee','VE49XCJ',3748,2,4,1),(5,'BMW Serie 1','RE46VCX',2039,1,5,2),(6,'Alfa Romeo Giulietta','GO28WKF',1003,3,1,1),(7,'Toyota Yaris','PR82FYH',2837,4,2,1),(8,'Nissan Micra','GO94JIJ',4039,4,3,1),(9,'Mercedes CLA','PZ74TJI',2837,2,4,2),(10,'BMW X3','UD04NLB',4958,1,5,1),(11,'Honda Civic','GE09HOK',2739,3,1,1);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Trono','Di Spade','3401983374','trono.dispade@gmail.com'),(2,'Giacomo','Malatesta','3890496578','giacomo.malatesta@gmail.com'),(5,'Mario','Zio Frank','3890298493','mario.ziofrank@gmail.com'),(7,'Giuliano','Cosenza','3289309483','giulioc@gmail.com'),(8,'Fabrizio','Cielo','38903920321','fabrizio.c@gmail.com'),(9,'Porto','Biologi','3467387485','porto.b@gmail.com'),(12,'Giulio','Lanisona','32840395534','giulio.laniso@hotmail.it');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Lecce','73100','Via Toma, 43'),(2,'Firenze','50121','Via Della Scienza, 4'),(3,'Milano','20121','Viale Loreto, 109'),(4,'Venezia','73100','Via Tizio, 392'),(5,'Genova','30010','Via Duca, 6'),(6,'asdasd','asda','asd'),(7,'asd','asds','asdas');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `operator`
--

LOCK TABLES `operator` WRITE;
/*!40000 ALTER TABLE `operator` DISABLE KEYS */;
INSERT INTO `operator` VALUES (8,'Giuliano','Giulio','giacomo.tizio@gmail.com','giacomotizio','giacomotizio',1,0),(9,'Alberto','Forensi','alberto.forensi@gmail.com','albertoforensi','albertoforensi',2,0),(3,'Fiorenzo','Ganioni','lorenzo.ganioni@gmail.com','lorenzoganioni','lorenzoganioni1',3,0),(4,'Giancarlo','Fiono','giancarlo.fiono@gmail.com','giancarlofiono','giancarlofiono2',4,0),(5,'Fiorenzo','Porto','fiorenzo.porto@gmail.com','fiorenzoporto','fiorenzoporto2',5,0);
/*!40000 ALTER TABLE `operator` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-08 12:35:20
