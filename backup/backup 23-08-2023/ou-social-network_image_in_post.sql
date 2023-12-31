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
-- Table structure for table `image_in_post`
--

DROP TABLE IF EXISTS `image_in_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image_in_post` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `image_url` varchar(300) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `post_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `image_in_post_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_in_post`
--

LOCK TABLES `image_in_post` WRITE;
/*!40000 ALTER TABLE `image_in_post` DISABLE KEYS */;
INSERT INTO `image_in_post` VALUES (1,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692616639/sfkh79tdl5n0peyrzalb.jpg','2023-08-21 11:17:20','2023-08-21 11:17:20',1),(2,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692623163/yrnkbknc5uefzjrkhoiv.jpg','2023-08-21 13:06:04','2023-08-21 13:06:04',2),(3,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692623163/tcmaysyutm4hikqydt9h.jpg','2023-08-21 13:06:04','2023-08-21 13:06:04',2),(4,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625363/vn3hm9ikqipqn70mhgxi.jpg','2023-08-21 13:42:44','2023-08-21 13:42:44',3),(5,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625594/bhbzswnpexrr1jxhi4vz.jpg','2023-08-21 13:46:35','2023-08-21 13:46:35',4),(6,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625594/lsiqiitvwqlj8zweqqxt.jpg','2023-08-21 13:46:35','2023-08-21 13:46:35',4),(7,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625925/ghztgnwjk2bq7yhecxgu.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(8,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625927/vmq8aerwhej0aknlwxgc.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(9,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625929/jqtultbhztzluxvspqpk.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(10,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625916/swuo3mstmkdyrnt8mwbn.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(11,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625919/edpfr8byilzcr1syi3zq.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(12,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625921/szweu4hcggqswlbieuof.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(13,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625923/c9c1vkwrtrpg6n8fosmx.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(14,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625916/jkfsq0fbgpnrurjtn2zv.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(15,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625918/enkva0xidzhsqzxd2zeg.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(16,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625920/u9nyenqk6jobbcrupspy.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(17,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625926/bbpa59ergyu2aqmbt7yz.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(18,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625928/xa6h9lr2ce1pmz4vya2p.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(19,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625922/d4ijnetgprv6axha23gx.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(20,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625924/ocwvx4xxr9cl72nmpskx.png','2023-08-21 13:52:10','2023-08-21 13:52:10',5),(21,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625926/gok7vn403vllcp3eapjg.jpg','2023-08-21 13:52:11','2023-08-21 13:52:11',6),(22,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625923/u3mggv1rfluqebe3y7z9.jpg','2023-08-21 13:52:11','2023-08-21 13:52:11',6),(23,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625929/wmvsbxcbwvibxx1f9fv1.jpg','2023-08-21 13:52:11','2023-08-21 13:52:11',6),(24,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692625988/mxmiaxvl5hdyxx0xtkqk.jpg','2023-08-21 13:53:09','2023-08-21 13:53:09',7),(25,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626130/hf5ujkjzknl6ha3wnjkp.jpg','2023-08-21 13:55:32','2023-08-21 13:55:32',8),(26,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626618/nvv2adb6irblnyyfwsoc.jpg','2023-08-21 14:03:40','2023-08-21 14:03:40',9),(27,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626689/qrlpuixqguybzpbrzlcx.jpg','2023-08-21 14:04:51','2023-08-21 14:04:51',10),(28,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626803/govunnrmtvsrnoqol37l.jpg','2023-08-21 14:06:44','2023-08-21 14:06:44',11),(29,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626967/dwesbkdgqhzq5i63onkc.jpg','2023-08-21 14:09:28','2023-08-21 14:09:28',12),(30,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692626979/uevq8azz9z5wftsnjzdk.jpg','2023-08-21 14:09:41','2023-08-21 14:09:41',13),(31,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627028/xjrzbvkqfjwtlckyvdww.jpg','2023-08-21 14:10:28','2023-08-21 14:10:28',14),(32,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627091/dopdnadapj4zvxsstahp.jpg','2023-08-21 14:11:32','2023-08-21 14:11:32',15),(33,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627213/m9x08hci250cww9s8bft.jpg','2023-08-21 14:13:34','2023-08-21 14:13:34',16),(35,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627335/z5laxce76gn5nblxnjjk.jpg','2023-08-21 14:15:36','2023-08-21 14:15:36',18),(36,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627486/ckj39exa5yu0xdx5ldhf.jpg','2023-08-21 14:18:07','2023-08-21 14:18:07',19),(37,'https://res.cloudinary.com/dxjkpbzmo/image/upload/v1692627623/ft5rxbmaba8u4uon55vr.jpg','2023-08-21 14:20:24','2023-08-21 14:20:24',20);
/*!40000 ALTER TABLE `image_in_post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-23 11:40:45
