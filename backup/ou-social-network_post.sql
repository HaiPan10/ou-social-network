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
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_active_comment` tinyint(1) DEFAULT '1',
  `user_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Hello xin chào mọi người, mạng xã hội cựu sinh viên v2 đã có thêm newfeed, reactions, đổi mật khẩu. Enjoy testing','2023-08-21 11:17:03','2023-08-21 11:17:20',1,2),(2,'Mình nhận cài win 11, Ubuntu dạo nhé\r\nGiá 100k 1 lần uy tín đảm bảo thành công','2023-08-21 13:06:01','2023-08-21 13:06:01',1,5),(3,'Testing','2023-08-21 13:42:42','2023-08-21 13:42:42',1,6),(4,'Khởi nghiệp','2023-08-21 13:46:33','2023-08-21 13:46:33',1,2),(5,'Góc bán sách kiếm tiền cho Hải mua laptop ','2023-08-21 13:51:55','2023-08-21 13:51:55',1,2),(6,'Giấc ngủ vội của bạn Phong\r\n','2023-08-21 13:52:01','2023-08-21 13:52:01',1,5),(7,'Flex intern cực mạnh','2023-08-21 13:53:07','2023-08-21 13:53:07',1,2),(8,'Đi intern chưa chắc đã có đc cái này :)','2023-08-21 13:55:29','2023-08-21 13:55:29',1,5),(9,'Có huy chương chưa chắc có cái này','2023-08-21 14:03:36','2023-08-21 14:03:36',1,2),(10,'Chưa cho đăng video nữa ?','2023-08-21 14:04:48','2023-08-21 14:04:48',1,8),(11,'Ít ra cx có cái giấy khen.','2023-08-21 14:06:41','2023-08-21 14:06:41',1,5),(12,'Ko cho đổi mật khẩu luôn ','2023-08-21 14:09:26','2023-08-21 14:09:26',1,8),(13,'heloo','2023-08-21 14:09:38','2023-08-21 14:09:38',1,9),(14,'Mỗi lần quên mật khẩu bắt tạo acc mới ?','2023-08-21 14:10:27','2023-08-21 14:10:27',1,8),(15,'Pùn vãi ò ??','2023-08-21 14:11:30','2023-08-21 14:11:30',1,8),(16,'Sao để ảnh cái chuông mà m bình luận ko hiện thông báo zẫy ? \r\nTrang trí thoi hả ?\r\n@PhongLại ','2023-08-21 14:13:33','2023-08-21 14:13:33',1,8),(18,'Đột nhập OU','2023-08-21 14:15:33','2023-08-21 14:15:33',1,4),(19,'Ít ra ấn vô logo OU cũng phải cho t về trang newsfeed chớ :v','2023-08-21 14:18:05','2023-08-21 14:18:05',1,8),(20,'Web lậu này kỳ thị ng dùng điện thoại nha bà con ?\r\nFeedback 5 câu 3 câu nó rep bắt mở lap r ?','2023-08-21 14:20:22','2023-08-21 14:20:22',1,8);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-23 11:40:51
