CREATE DATABASE  IF NOT EXISTS `roomsense` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;
USE `roomsense`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: roomsense
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

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
-- Table structure for table `actuation_command`
--

DROP TABLE IF EXISTS `actuation_command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actuation_command` (
  `actuation_command_id` int(11) NOT NULL,
  `actuator` enum('AC','HEATER') NOT NULL,
  `status` enum('QUEUED','COMPLETED') NOT NULL,
  `created_at` datetime NOT NULL,
  `executed_at` datetime NOT NULL,
  PRIMARY KEY (`actuation_command_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuation_command`
--

LOCK TABLES `actuation_command` WRITE;
/*!40000 ALTER TABLE `actuation_command` DISABLE KEYS */;
/*!40000 ALTER TABLE `actuation_command` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_state`
--

DROP TABLE IF EXISTS `device_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_state` (
  `device_state_id` int(11) NOT NULL AUTO_INCREMENT,
  `heater_state` tinyint(1) NOT NULL,
  `ac_state` tinyint(1) NOT NULL,
  `mode` enum('MANUAL','AUTO') DEFAULT NULL,
  PRIMARY KEY (`device_state_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_state`
--

LOCK TABLES `device_state` WRITE;
/*!40000 ALTER TABLE `device_state` DISABLE KEYS */;
INSERT INTO `device_state` VALUES (1,0,0,'AUTO');
/*!40000 ALTER TABLE `device_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_readings`
--

DROP TABLE IF EXISTS `sensor_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensor_readings` (
  `sensor_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `temperature` double NOT NULL,
  `humidity` double NOT NULL,
  `movement_detected` tinyint(1) NOT NULL,
  `light_level` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`sensor_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_readings`
--

LOCK TABLES `sensor_readings` WRITE;
/*!40000 ALTER TABLE `sensor_readings` DISABLE KEYS */;
INSERT INTO `sensor_readings` VALUES (1,21.5,45.2,0,120,'2026-04-06 08:00:00'),(2,21.7,44.8,0,130,'2026-04-06 08:05:00'),(3,22.1,43.9,1,300,'2026-04-06 08:10:00'),(4,22.8,42.5,1,500,'2026-04-06 08:15:00'),(5,23.2,41.7,0,550,'2026-04-06 08:20:00'),(6,23.5,40.9,0,600,'2026-04-06 08:25:00'),(7,24,39.5,1,650,'2026-04-06 08:30:00'),(8,24.3,38.8,1,700,'2026-04-06 08:35:00'),(9,24.7,37.9,0,720,'2026-04-06 08:40:00'),(10,25.1,37.2,0,750,'2026-04-06 08:45:00'),(11,25.5,36.5,1,800,'2026-04-06 08:50:00'),(12,25,38,1,600,'2026-04-06 09:00:00'),(13,24.6,39.2,0,400,'2026-04-06 09:10:00'),(14,24,40.5,0,200,'2026-04-06 09:20:00'),(15,23.5,42,1,150,'2026-04-06 09:30:00');
/*!40000 ALTER TABLE `sensor_readings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'roomsense'
--

--
-- Dumping routines for database 'roomsense'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-07  1:39:23
