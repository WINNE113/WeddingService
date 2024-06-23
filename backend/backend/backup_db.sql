-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: wedding_db
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
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'khách Hàng','ROLE_CUSTOMER'),(2,'Nhà Cung Cấp','ROLE_SUPPLIER'),(3,'Quản Lý','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_album_entity_image_url_list`
--

LOCK TABLES `service_album_entity_image_url_list` WRITE;
/*!40000 ALTER TABLE `service_album_entity_image_url_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_album_entity_image_url_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_albums`
--

LOCK TABLES `service_albums` WRITE;
/*!40000 ALTER TABLE `service_albums` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_promotions`
--

LOCK TABLES `service_promotions` WRITE;
/*!40000 ALTER TABLE `service_promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_types`
--

LOCK TABLES `service_types` WRITE;
/*!40000 ALTER TABLE `service_types` DISABLE KEYS */;
INSERT INTO `service_types` VALUES (1,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/nha-hang-tiec-cuoi.svg','Nhà hàng tiệc cưới'),(2,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/nhan-cuoi.svg','Nhẫn cưới'),(3,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/qua-cuoi.svg','Quà cưới'),(4,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/chup-anh-cuoi.svg','Chụp ảnh cưới'),(5,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/trang-diem-co-dau.svg','Trang điểm cô dâu'),(6,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/nghi-thuc-le-cuoi.svg','Nghi thức lễ cưới'),(7,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/trang-phuc-cuoi.svg','Trang phục cưới'),(8,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/hoa-cuoi.svg','Hoa cưới'),(9,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/thiep-cuoi.svg','Thiệp cưới'),(10,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/xe-cuoi.svg','Xe cưới'),(11,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/wedding-planner.svg','Wedding planner'),(12,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/trang-mat.svg','Trăng mật'),(13,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/noi-that-cuoi.svg','Nội thất cưới'),(14,NULL,'https://www.marry.vn/design/themes/marry/media/images/icons/dich-vu-khac.svg','Dịch vụ khác');
/*!40000 ALTER TABLE `service_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',1,NULL,NULL,1,NULL,'Quận Tân Bình - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/244/grand-huong3.jpg',NULL,NULL,'0332101032',NULL,'Trung Tâm Hội Nghị - Tiệc Cưới Grand Palace',NULL,'APPROVED'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',2,NULL,NULL,1,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/90/hinh_DHP_-_408x600px-min.jpg',NULL,NULL,'0332101032',NULL,'Trung Tâm Hội Nghị Yến Tiệc Đại Hỷ Palace',NULL,'REVIEW'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',3,NULL,NULL,1,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/2/Untitled_design.jpg',NULL,NULL,'0332101032',NULL,'Hotel Grand Saigon',NULL,'APPROVED'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',4,NULL,NULL,1,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/2/Untitled_design.jpg',NULL,NULL,'0332101032',NULL,'Hotel Grand Saigon',NULL,'APPROVED'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',5,NULL,NULL,1,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/2/Untitled_design.jpg',NULL,NULL,'0332101032',NULL,'Hotel Grand Saigon',NULL,'APPROVED'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',6,NULL,NULL,2,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/2/Untitled_design.jpg',NULL,NULL,'0332101032',NULL,'Hotel Grand Saigon 2',NULL,'APPROVED'),(_binary '\0',300000.00,'08ce75edefd446159f4f590cb78977ca','2023-11-15 21:40:36.597000',7,NULL,NULL,2,NULL,'Quận 6 - Thành phố Hồ Chí Minh','https://www.marry.vn/images/thumbnails/204/300/detailed/2/Untitled_design.jpg',NULL,NULL,'0332101032',NULL,'Hotel Grand Saigon 2',NULL,'REVIEW');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (_binary '',_binary '',1,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTg5MDc5NzQsImV4cCI6MTcxODkxNTE3NH0.CvgSmffCavwu1JlvaJ1LKKGTa6xFYbe21EuZDIpbfis','BEARER'),(_binary '',_binary '',2,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTg5ODM3NDksImV4cCI6MTcxODk5MDk0OX0.RP8gvPWiK9eISwrlUnD0rEfTig00zA5wBlp_9Eiu5sc','BEARER'),(_binary '',_binary '',3,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkwMDIyNjcsImV4cCI6MTcxOTAwOTQ2N30.Te-RdhQBhJNFlqWeVqrxtS3A96TawPEOcmiml2ayTDk','BEARER'),(_binary '',_binary '',4,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkwMDQyNTksImV4cCI6MTcxOTAxMTQ1OX0.FDDZb7C81JPEDkmPEn2CdGSgv2IO1-v2AyUv4sd6gjY','BEARER'),(_binary '',_binary '',5,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkwMjc4MTYsImV4cCI6MTcxOTAzNTAxNn0.Zhnm5rZSYWNBX5TKhvistQVjT1EKMah9q-0tDbrA-vg','BEARER'),(_binary '',_binary '',6,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkwMjg5ODQsImV4cCI6MTcxOTAzNjE4NH0.KJ_itRfaGMRIJZvd0ekKpfiqSgx-amB2tczPbn0OERU','BEARER'),(_binary '',_binary '',7,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkwMjkxMDgsImV4cCI6MTcxOTAzNjMwOH0.vt6ZTX-P035zf6R3zJ60yTszyIythPRH7JbZZ5aJ8t4','BEARER'),(_binary '',_binary '',8,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkxMzAwMTksImV4cCI6MTcxOTEzNzIxOX0.T46NxjCahKVNHj8HWfJLI1MpjG0bj81naLn4ZFo1bUY','BEARER'),(_binary '',_binary '',9,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkxMzEwMzksImV4cCI6MTcxOTEzODIzOX0.xl8wqLl2MCT0_CQe5ua59_xUPPkbzzRqQpLDlJByAmY','BEARER'),(_binary '',_binary '',10,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkxMzI0NzAsImV4cCI6MTcxOTEzOTY3MH0.i6l8UqEcvIE4HqtqWvy-pe32yhn7fwDZGraXJHDEaAQ','BEARER'),(_binary '',_binary '',11,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkxMzQyOTYsImV4cCI6MTcxOTE0MTQ5Nn0.tzi1XNUHIeJt1fQRPE3q4vzNpYYNhRu8ge3oVEon1Bc','BEARER'),(_binary '\0',_binary '\0',12,'08ce75edefd446159f4f590cb78977ca','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrODQzMzIxMDEwMzIiLCJpYXQiOjE3MTkxMzU2MzIsImV4cCI6MTcxOTE0MjgzMn0.dbjPqMLOZrDzqgdrqJMemmGf3QndG-Eu0_V69Hb-d44','BEARER');
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'08ce75edefd446159f4f590cb78977ca');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0.00,_binary '',_binary '\0',_binary '\0',_binary '\0','2024-06-21 01:11:53.493000',NULL,NULL,'08ce75edefd446159f4f590cb78977ca',NULL,'','huuthang@gmail.com',NULL,'$2a$10$DDHTUAEVgd36Sq3KN/1dCu6aRvtAF6l83DuCgotTMTC5XkMJ7N2p6','+84332101032','Yaga','http://res.cloudinary.com/dqj1yqlkb/image/upload/v1719135514/860f82e3-2f13-4930-9f32-5cea76db8d48.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `wishlist_items`
--

LOCK TABLES `wishlist_items` WRITE;
/*!40000 ALTER TABLE `wishlist_items` DISABLE KEYS */;
INSERT INTO `wishlist_items` VALUES ('2024-06-22 11:18:09.007000',1,6,16),('2024-06-22 11:18:09.841000',3,6,17),('2024-06-22 11:18:10.869000',4,6,18);
/*!40000 ALTER TABLE `wishlist_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `wishlists`
--

LOCK TABLES `wishlists` WRITE;
/*!40000 ALTER TABLE `wishlists` DISABLE KEYS */;
INSERT INTO `wishlists` VALUES (6,'08ce75edefd446159f4f590cb78977ca','service');
/*!40000 ALTER TABLE `wishlists` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-23 16:44:13
