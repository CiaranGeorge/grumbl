CREATE DATABASE  IF NOT EXISTS `grumbldb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `grumbldb`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: grumbl.cmvs5hmsurto.eu-west-1.rds.amazonaws.com    Database: grumbldb
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(250) NOT NULL,
  `timeCreated` timestamp NOT NULL,
  `timeUpdated` timestamp NOT NULL,
  `complaintId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Oh noes','2014-11-04 14:21:21','2014-11-04 14:21:21',3),(2,'dsadsadsa','2014-11-04 16:23:37','2014-11-04 16:23:37',15),(3,'asaffsafas','2014-11-04 16:32:29','2014-11-04 16:32:29',15),(4,'dsadsa','2014-11-04 16:33:42','2014-11-04 16:33:42',15),(5,'dffsfs','2014-11-04 16:34:22','2014-11-04 16:34:22',15),(6,'','2014-11-04 16:35:32','2014-11-04 16:35:32',15),(7,'HGello','2014-11-04 16:35:58','2014-11-04 16:35:58',15),(8,'dsadsa','2014-11-04 16:41:03','2014-11-04 16:41:03',15),(9,'dsadsadsada','2014-11-04 16:41:27','2014-11-04 16:41:27',15),(10,'Boom','2014-11-04 16:43:05','2014-11-04 16:43:05',15),(11,'dsadsada','2014-11-04 16:43:29','2014-11-04 16:43:29',15),(12,'Howdy','2014-11-04 16:46:16','2014-11-04 16:46:16',15),(13,'Hey there','2014-11-04 16:47:19','2014-11-04 16:47:19',15),(14,'dsadsa','2014-11-04 16:47:46','2014-11-04 16:47:46',15),(15,'','2014-11-04 16:52:25','2014-11-04 16:52:25',15),(16,'','2014-11-04 16:53:29','2014-11-04 16:53:29',15),(17,'','2014-11-04 16:53:37','2014-11-04 16:53:37',15),(18,'','2014-11-04 16:54:06','2014-11-04 16:54:06',15),(19,'21321321231','2014-11-04 16:55:14','2014-11-04 16:55:14',15),(20,'Fffff','2014-11-05 11:45:24','2014-11-05 11:45:24',3),(21,'Fffff','2014-11-05 11:45:24','2014-11-05 11:45:24',3),(22,'Fffff','2014-11-05 11:45:24','2014-11-05 11:45:24',3),(23,'Gggg','2014-11-05 11:45:47','2014-11-05 11:45:47',12),(24,'Gggg','2014-11-05 11:45:52','2014-11-05 11:45:52',12),(25,'Ggggvv','2014-11-05 11:45:55','2014-11-05 11:45:55',12),(26,'I\'m sorry we where so slow','2014-11-05 13:32:03','2014-11-05 13:32:03',17),(27,'Oh really is that right','2014-11-05 13:36:29','2014-11-05 13:36:29',17),(28,'Oh really is that right','2014-11-05 13:36:31','2014-11-05 13:36:31',17),(29,'OH really is that right','2014-11-05 13:36:42','2014-11-05 13:36:42',17),(30,'OH really is that right','2014-11-05 13:36:45','2014-11-05 13:36:45',17),(31,'ewqewewq','2014-11-07 16:54:09','2014-11-07 16:54:09',21),(32,'Ggggg','2014-11-07 17:16:56','2014-11-07 17:16:56',1),(33,'Jshsh','2014-11-08 13:37:16','2014-11-08 13:37:16',12),(34,'Hhhhhhhhfdhhgf','2014-11-10 18:31:30','2014-11-10 18:31:30',21),(35,'Hello','2014-11-11 10:50:05','2014-11-11 10:50:05',23),(36,'adsadsada','2014-11-11 10:50:52','2014-11-11 10:50:52',23),(37,'jnkasfnjkffnjkfjfffsanjkfasfsjajffajffa','2014-11-11 11:07:16','2014-11-11 11:07:16',23),(38,'dsasasasasasasasasasasasasasasasasasasasasds','2014-11-11 11:08:18','2014-11-11 11:08:18',23),(39,'thingy','2014-11-11 11:41:52','2014-11-11 11:41:52',23),(40,'bhahjsbdhjas bdhjsabhdab bhdahbdjjhsda bhjsbahdjsbahj bhsadjbhdasbjhda','2014-11-11 11:46:02','2014-11-11 11:46:02',23),(41,'dsadsadsa','2014-11-11 11:47:34','2014-11-11 11:47:34',23),(42,'safsafsa','2014-11-11 11:48:42','2014-11-11 11:48:42',23),(43,'fasfsfaasf','2014-11-11 11:52:20','2014-11-11 11:52:20',23),(44,'dsadsa','2014-11-11 11:52:27','2014-11-11 11:52:27',23),(45,'ksamdsmkldas','2014-11-11 11:54:42','2014-11-11 11:54:42',23),(46,'Hello','2014-11-11 11:56:05','2014-11-11 11:56:05',23),(47,'faffsasaf','2014-11-11 11:56:49','2014-11-11 11:56:49',23),(48,'Cycycycycu','2014-11-11 12:08:16','2014-11-11 12:08:16',23),(49,'Hgjbvgh','2014-11-11 13:56:08','2014-11-11 13:56:08',25),(50,'Ggggg','2014-11-11 17:53:39','2014-11-11 17:53:39',23),(51,'Yeoooo','2014-11-16 16:16:11','2014-11-16 16:16:11',26),(52,'Gt idiejhxur','2014-11-16 16:16:17','2014-11-16 16:16:17',26),(53,'Hfjduuwus','2014-11-16 16:17:49','2014-11-16 16:17:49',29),(54,'<script>alert(\"xss\")</script>','2014-11-16 16:23:16','2014-11-16 16:23:16',30),(55,'Hshhshshs','2014-11-22 21:01:22','2014-11-22 21:01:22',12),(56,'Gggg','2014-11-27 15:25:16','2014-11-27 15:25:16',1),(57,'Gggggg','2014-11-27 15:25:20','2014-11-27 15:25:20',1),(58,'Vbhhh','2014-11-27 15:25:27','2014-11-27 15:25:27',1),(59,'Gggv','2014-11-27 15:39:03','2014-11-27 15:39:03',21),(60,'Hggg','2014-11-28 09:34:59','2014-11-28 09:34:59',35),(61,'Bhhh','2014-11-28 09:35:02','2014-11-28 09:35:02',35),(62,'Hbhvvv','2014-11-28 23:25:16','2014-11-28 23:25:16',36),(63,'Hhhhh','2014-11-28 23:25:19','2014-11-28 23:25:19',36),(64,'Ciaran','2014-11-30 11:34:12','2014-11-30 11:34:12',1),(65,'Gdhsh','2014-12-01 14:51:58','2014-12-01 14:51:58',37),(66,'Kdndndjnd','2014-12-03 16:02:16','2014-12-03 16:02:16',39),(67,'Nxjddjdnd','2014-12-03 16:02:20','2014-12-03 16:02:20',39);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `description` varchar(400) NOT NULL,
  `timeCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `timeUpdated` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `company` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `status` varchar(10) NOT NULL,
  `ownerId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_OWNER` (`ownerId`),
  CONSTRAINT `FK_OWNER` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES (1,'omg its broken','Its all broken','2014-10-21 10:35:37','2014-10-21 10:35:37','camlin','lisburn','N',3),(2,'ffdfsd','fdsdfsdfs','2014-10-21 15:16:23','2014-10-21 15:16:23','fsdfdsfsd','dsffdsfsd','N',3),(3,'ffffffffffffffffffffffffffffff','fassaffsa','2014-10-21 15:16:42','2014-10-21 15:16:42','fsafsaas','fsasfafas','A',3),(5,'awesome','He is so gargeous','2014-10-23 13:33:52','2014-10-23 13:33:52','uni of life','lurgan','N',10),(6,'test; select * from user;','Yre','2014-10-23 13:36:46','2014-10-23 13:36:46','tet','test','N',10),(7,'test; delete from user where id=1;','Bs','2014-10-23 13:37:42','2014-10-23 13:37:42','ydh','hd','N',10),(8,'t; delete * from complaint;','Nsb','2014-10-23 13:39:11','2014-10-23 13:39:11','tsg','bd','N',10),(11,'gsgshdh','Hdhdhdh','2014-10-27 15:33:16','2014-10-27 15:33:16','hshdhdhd','select * from user','A',3),(12,'<p style=\"color:red\">hi</p>','Fff','2014-10-27 15:46:04','2014-10-27 15:46:04','fff','ggg','R',3),(15,'ggg','Hhhh','2014-11-04 13:44:57','2014-11-04 13:44:57','hhhhh','bbb','N',3),(16,'ciaram','Bbh','2014-11-04 17:01:22','2014-11-04 17:01:22','gghh','gvg','A',3),(18,'no work','Nothing to do','2014-11-05 13:29:13','2014-11-05 13:29:13','camlin','Lisburn','N',23),(20,'dsaasd','dsadsasdasdasda','2014-11-07 10:09:16','2014-11-07 10:09:16','dadas','adsdas','N',3),(21,'\'SELECT * FROM USER\'','\'SELECT * FROM USER\'','2014-11-07 10:10:53','2014-11-07 10:10:53','\'SELECT * FROM USER\'','\'SELECT * FROM USER\'','N',3),(22,'SUmmary','asdasdkdsadsamkdaskdasmkdasmkdasmklasdmdksdamkasdmkldmkmkdmdamkdlmkdmdkddmkldmklmkldmklmkldmdklmkldddmkmdkdaddmkldmdmdkasd','2014-11-11 09:55:58','2014-11-11 09:55:58','sadsads','asdas','N',3),(23,'Another Issue','Today I was in the shop, it was a lovely shop filled with people. Until a tesco employee attacked james may.','2014-11-11 09:57:08','2014-11-11 09:57:08','Tesco','Belfast','N',3),(24,'fhdhdb','Djdbfbcbfbdhhdbdbfbd','2014-11-11 12:46:29','2014-11-11 12:46:29','bxbcjfhfhfhhfbfnf','cjdjdbdjdhdkskdn','N',22),(25,'fcfcf','Gtvtctctct','2014-11-11 13:55:49','2014-11-11 13:55:49','crcrc','crctcc','N',26),(26,'dsaasd','dsadassdadsads','2014-11-11 16:06:42','2014-11-11 16:06:42','dsadasdsa','dsadsadsa','N',3),(29,'New picture','Vfggbbfdjcjjd','2014-11-16 16:17:24','2014-11-16 16:17:24','fg','vffvffg','N',3),(30,'<script>alert(\"xss\")</script>','<script>alert(\"xss\")</script>','2014-11-16 16:23:00','2014-11-16 16:23:00','<script>alert(\"xss\")</script>','<script>alert(\"xss\")</script>','N',3),(31,'ggggggggggfuvusvutfsvupcjcuxufjcjcifififjxjdudjcjc','ggggggggggfuvusvusvupcjcuxufjcjcifififjxjdudjcjcv; // ff\nSelect * from user','2014-11-16 16:25:41','2014-11-16 16:25:41','ggggggggggfuvusvusvupcj','ggggggggggfuvusvusvupcjcuxufjc','N',3),(34,'fffrrr','Cfffffffffff','2014-11-27 14:37:38','2014-11-27 14:37:38','fffff','ffffff','N',35),(35,'gggg','Gvvgggvvvvvv','2014-11-28 09:34:26','2014-11-28 09:34:26','hhhhh','ggggv','N',35),(36,'gggg','Bhvghhhhhh','2014-11-28 23:24:59','2014-11-28 23:24:59','bbbb','gggg','N',36),(37,'hshsh','Ydhdhdhhdhxhd','2014-12-01 14:51:45','2014-12-01 14:51:45','jdjdh','hdhdjd','N',35),(38,'sammys grumbl','Logged as same fb showing ciaran','2014-12-03 11:38:43','2014-12-03 11:38:43','fdfvjhbn','bguhggvh','N',36),(39,'hello','Sammys first grumbl','2014-12-03 16:01:44','2014-12-03 16:01:44','someone','somewhere','N',37);
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `emailSent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,'0:0:0:0:0:0:0:1',3,'2014-12-05 10:55:23.0'),(2,'0:0:0:0:0:0:0:1',3,'2014-12-05 11:14:30.0'),(3,'0:0:0:0:0:0:0:1',3,'2014-12-05 11:14:35.0');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `timeCreated` varchar(45) DEFAULT NULL,
  `timeUpdated` varchar(45) DEFAULT NULL,
  `roles` varchar(45) DEFAULT NULL,
  `facebookAccount` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'cgeorge','Ciaran','George','6604f0924874eb5359823a40b748a1787aa8ca23b7390f943a6d587535cd9486','test@grumbltest.com',NULL,NULL,NULL,0),(2,'mmillar','Michael','Millar','6604f0924874eb5359823a40b748a1787aa8ca23b7390f943a6d587535cd9486','test@test.com',NULL,NULL,NULL,0),(3,'ciaran','ciaran','georgeeeeeeeeeeeeeeeeeeeeeee','$2a$10$w3iwNHQAehpJhH3cjpMH2OzeKVz7B1CScMoLDElHxwhTvXP4.asnC','ciarangeorge@gmail.com','2014-10-21 10:34:48.0','2014-12-03 15:35:18.0',NULL,0),(5,'gggggg','ggggggg','hhhhhh','$2a$10$bGG0FgZs7H9DZ3SbYqJnm.9KWRrgPIv1FFRFAoWt5GRF4uLyZtxZy','ggggggg@hhhhh','2014-10-22 15:10:57.0','2014-10-22 15:10:57.0',NULL,0),(6,'lalala','ladi','dah','$2a$10$wvaUBvLndpF8pGPM7f5H5.3rb25dXtj4M7QA4PkK3wtUhPs5iFIJu','bdjdnejd@dndjrnr.com','2014-10-23 10:24:07.0','2014-10-23 10:24:07.0',NULL,0),(10,'mgmillar','Michael','millar','$2a$10$IcgmX1hH5B/JAGEZN7ixRObctKDeSOpzlz1r3ZzL9om9cagm2eOL2','gone@live.co.uk','2014-10-23 13:31:35.0','2014-10-23 13:31:35.0',NULL,0),(13,'CiaranGeorge','Ciaran','George','$2a$10$oc1yBIH8dg6KRYP1SeWN8eQNNdKWwW.MBf13tO68XIDk5MUYlL72G','old@gmail.com','2014-10-27 14:07:54.0','2014-10-27 14:07:54.0',NULL,0),(14,'CiaranG','Ciaran','Georgee','$2a$10$6IaGZ1N0DKzgE.g8RpY4v.qFGeGF3XmhwHIkLe4C3xP1sIQmnToUG','ciarangeorgwe@gmail.com','2014-10-27 14:29:06.0','2014-10-27 14:29:06.0',NULL,0),(22,'Nemiya','Sammy','George','$2a$10$YhXzjFQ2pbni3yp5TJDxk.yerrWgz4vFGc6TeoUobjbiHvribvE7S','sam@emailio.com','2014-11-05 12:12:33.0','2014-11-05 12:15:26.0',NULL,0),(23,'matt','Ciaran','George','$2a$10$Oh71O4TUnARY.cTHoCYZBebrv4Egt1Aoodiw7RaCKFpV.0WBY.Hwa','ciarangeforge@gmail.com','2014-11-05 13:27:43.0','2014-11-05 13:27:43.0',NULL,0),(26,'james','james','may','$2a$10$OUvJcBjrFUYXIMs1gWTMKep6rQMriKuhJ2m5muNdtbBd0z/NwkzVK','james@james.com','2014-11-11 13:54:58.0','2014-11-11 13:54:58.0',NULL,0),(29,'dsadsasa','Pass','Word','$2a$10$7.Vzch5iiJ8PJWONtaWqhukUSFFJ9UyGJoh5xgNR0rxyQQh5HY6hK','password@password.com','2014-11-13 09:33:17.0','2014-11-13 09:35:04.0',NULL,0),(35,'ciaran89','Ciaran','George','$2a$10$Yu7zIXyyBeBrCGQSxRst..zCS0ke62Xf0y9eb6lZfX4qOnre46cWq','ciaran89@hotmail.com','2014-11-27 14:37:01.0','2014-11-27 14:37:01.0',NULL,1),(36,'samjo388','Sammy','Selly','$2a$10$lJIFnrN353sf2K1TRmOODeH2g8NHEU42tgyHyfDVo7dfM1JRF/EB6','samjo388@hotmail.com','2014-11-28 23:22:09.0','2014-11-28 23:22:09.0',NULL,0),(37,'sammy','sammy','selly','$2a$10$FMKJMmHRBHNKXOoosMkz0OAk3EzBIpkqspLYfDO24LM/3w.lQa3S6','sammyselly@gmail.com','2014-12-03 15:58:36.0','2014-12-03 16:05:20.0',NULL,0),(38,'ddsasda','password','mcpass','$2a$10$hE.KJD2Ovm5bZCgU0Q2EvOhg5IkcuOZIP3x9v0nWafYvN0LOrIeum','pass@sadsa.com','2014-12-03 16:37:18.0','2014-12-03 16:37:18.0',NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` int(6) NOT NULL,
  `role_id` int(6) NOT NULL,
  KEY `FK_USER_ID` (`user_id`),
  KEY `FK_USER_ROLE` (`role_id`),
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,2),(2,2),(3,2),(5,2),(6,2),(10,2),(13,2),(14,2),(22,2),(23,2),(26,2),(29,2),(35,2),(36,2),(37,2),(38,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-05 11:20:04
