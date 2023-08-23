-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 34.101.125.238    Database: ou-social-network
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(300) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `status` enum('LOCKED','ACTIVE','AUTHENTICATION_PENDING','EMAIL_VERIFICATION_PENDING','REJECT','PASSWORD_CHANGE_REQUIRED') DEFAULT 'ACTIVE',
  `role_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin456@gmail.com','$2a$10$3YohAzphxM8cU1uvEkikleeAf4xPlPZrQ0eqx5iPgc0bcUT48j/SC','2023-08-21 07:10:14',NULL,'ACTIVE',3),(2,'phongvulai96@gmail.com','$2a$10$bHmRkswjDvT333Fex6JF/eh71zLgyMzahLDfXaHHqLsT6nhS.Ikx6','2023-08-21 11:13:11','Mru7lHpLiPx4NFdFOz5T1mM2KvIjX8LNDP86ooyhePLznp4AazgTHyDNg7FnVES5','ACTIVE',1),(3,'2051012054linh@ou.edu.vn','$2a$10$lc.gJeOTk1XUm.uhxKxu9.wUCaNZBaK2dE/FgC7nVnC304.FCkP8W','2023-08-21 12:19:09','1ScrnZ5Qv0Sw66FGRBp5SmFoZNvjO7x5P0xOuXDpuPMJnjBRvLoXTXAmJRLfxPrD','ACTIVE',1),(4,'thanhhuyen1952002@gmail.com','$2a$10$ozvYVGApzQVgZbC9S9uD5.M0bU1aB5v9A79j4eiCyXR7QRifgNMfC','2023-08-21 12:20:39','IXYPWuMzJiKYS4NbeTAFAJOKjW6Lpl73MAfC2h3zhht2f1w9W151B3MNxPdq0LgJ','ACTIVE',1),(5,'phanthanhhai3317@gmail.com','$2a$10$qgiAnoD74zw4fEgOnwq/k.rqwgi8uaTSvpnf4muSNqwtS8JQ561Im','2023-08-21 13:03:09','hwExrThXHUUxXa4Ph9aQyMe6hQLD8hEa2TRL66hZ9qCelHuAIcsefz4tBvpWv3cT','ACTIVE',1),(6,'truongthanhdat2002@gmail.com','$2a$10$pf2I/6.pK8YYJ4UZ0LRuoeWXMRObf1ssGKQ5ukEvegOuHZQSTsOey','2023-08-21 13:39:28','akibHDG8P18J3unpyWI055ZELoRtCKuvy0FJD3zbBiaxrRsjB8m7xc2siL0BnkId','ACTIVE',1),(7,'tambui@gmail.com','$2a$10$psf.TkokI4frE1IEVY9gNe6G8uYCulJKNfFDr6/w6dRxavC0uyWHa','2023-08-21 13:44:15',NULL,'LOCKED',2),(8,'qingxuan131415@gmail.com','$2a$10$8VpsQEl7cr0OItO65L6hLOIRfcdN6PUJOpX6.zqZkb6mL/F7cI1PW','2023-08-21 13:57:42','5YOaiNlVGiuD5ZTFMR0sDX5s7igN6qUIex95etEFFtkKsoty7OqC2PfmMut4ob90','ACTIVE',1),(9,'AncuaHao@gmail.com','$2a$10$CDYy8bUyzSeE/IaNKedrB.vRM8KR50A1l.SAdnHnpkE34uhfIZ6Va','2023-08-21 14:06:43',NULL,'ACTIVE',2),(10,'2051052087ngan@ou.edu.vn','$2a$10$xCf4hfMR27SmxMm66Y2H1eYGkNFskBYePWR8nrxmA89dt0AHD4TkW','2023-08-22 05:09:29','CwZjEfXSpHGwRxQSectfG2F5FGFlGInAA8ikGIt0FU52i2rFZqY45e9EKjURY3ar','ACTIVE',1),(11,'vonhung5852@gmail.com','$2a$10$DOctWpvIQFLyQOqhp6ahnu4UlvBll37kS6csvZLRm2FysQs1bRioO','2023-08-22 05:25:44','DXKmBzKXxhkBZXXtlFs44oElUzQzGkvFYI4gJQYCK1gKmAODbun2LxcqHLmGvoQT','ACTIVE',1),(12,'hai2000@gmail.com','$2a$10$NFcdBlXE/USg97/tkwKfDuq6tihp398ac4Ed4d3TarWb.drkYX8p6','2023-08-22 05:34:49',NULL,'PASSWORD_CHANGE_REQUIRED',2),(13,'panwink160617@gmail.com','$2a$10$.TsS4ztJYfgDVKQts78E0.07I1Xkx66WtPKVdGoCKNsUB4Tfay4xa','2023-08-22 13:30:32','3fV75nDfqID1uC6W4wULzOViBD5yeDGeJhl6yVnxzoBMF1Ea5arNkzyxAg8D36Rh','ACTIVE',1),(14,'ousocialnetwork@gmail.com','$2a$10$89bXeEnuFKbSWgICqFBpLuft0F.qmolB0WtJcsJn62p3evAXKGiGW','2023-08-22 14:01:36','ZzsdhGxYc0d0zIhmTby5hO8TwYKze5QimaDy0xZWKITsfDki8TeXsZrT35ZHVygc','EMAIL_VERIFICATION_PENDING',1),(15,'phongvulai99@gmail.com','$2a$10$z0rXuvgyQpRjGNVDXyfrgOmOiO5J39pvNNqjKsg4qfcupmunVhNjK','2023-08-22 14:04:14','79EAKrLZ3iq1zIxEZzTqwqnbvX1woO2gQjUuDxbKI2qPvHDnZC9T0XCl2Xi83SRw','EMAIL_VERIFICATION_PENDING',1),(17,'thithanhhuyennguyen002@gmail.com','$2a$10$um/P7rGUETlaEEkDzVs78eszLNeXp32lZUS1DgmV/bF4LoCXtNwoq','2023-08-22 15:31:47','2rFXKRSI6FMBt7MEIci5kvCk1CCuANAM6f8yxfxhjYPRMM3o1ZVhOQjWK4oDYkXm','ACTIVE',1),(18,'thanhhai18052002@gmail.com','$2a$10$J8ZgTFFNk2rmtDfkjj34wOWM2BGZ3HNQjGXPcQ0DCDCmdtVSPt2YC','2023-08-22 15:51:07','LT5fWLblaeQrRPJhgwEO1NiyHbRoj8JOxBBD68WLiClqDFnZofZfaAQwUg8GoU0e','EMAIL_VERIFICATION_PENDING',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-23 11:40:42
