CREATE DATABASE `anrc`;
USE `anrc`;

CREATE USER 'anrc_user'@'localhost' IDENTIFIED BY 'popo';
GRANT DELETE, INSERT, SELECT, UPDATE ON anrc.* TO `anrc_user`@`localhost`;

DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) NOT NULL,
  `postal_no` varchar(5) NOT NULL,
  `road` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);

LOCK TABLES `location` WRITE;
INSERT INTO `location` VALUES (1,'Lecce','73100','Via Toma, 43');
UNLOCK TABLES;

DROP TABLE IF EXISTS `place`;
CREATE TABLE `place` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `location_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `place_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON UPDATE CASCADE
);

LOCK TABLES `place` WRITE;
INSERT INTO `place` VALUES (2,'ANRC Lecce',1);
UNLOCK TABLES;

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `place_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `place_id` (`place_id`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`place_id`) REFERENCES `place` (`id`) ON UPDATE CASCADE
);

LOCK TABLES `room` WRITE;
INSERT INTO `room` VALUES (3,'r1',2), (4,'r2',2), (5,'r3',2), (6,'r4',2);
UNLOCK TABLES;

DROP TABLE IF EXISTS `detector_type`;
CREATE TABLE `detector_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `min_value` float(5) NOT NULL,
  `max_value` float(5) NOT NULL,
  PRIMARY KEY (`id`)
);

LOCK TABLES `detector_type` WRITE;
INSERT INTO `detector_type` VALUES (7,'rad',100,600),(8,'temp',0,1000);
UNLOCK TABLES;

DROP TABLE IF EXISTS `detector`;
CREATE TABLE `detector` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type_id` int(10) NOT NULL,  
  `room_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `detector_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `detector_type` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `detector_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON UPDATE CASCADE
);

LOCK TABLES `detector` WRITE;
INSERT INTO `detector` VALUES (9,'d1',7,3),(10,'d2',7,4),(11,'d3',8,5),(12,'d4',8,6);
UNLOCK TABLES;

DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `value` int(5) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `survey_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON UPDATE CASCADE
);

