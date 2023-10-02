-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db_library
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` (id, is_deleted, name) VALUES (1,_binary '\0','John Doe'),(2,_binary '\0','Jane Smith'),(3,_binary '\0','Michael Johnson'),(4,_binary '\0','Emily Brown'),(5,_binary '\0','Robert Wilson'),(6,_binary '\0','Jennifer Lee'),(7,_binary '\0','David Clark'),(8,_binary '\0','Jessica Martinez'),(9,_binary '\0','Daniel Garcia'),(10,_binary '\0','Sarah Lewis');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (id, is_deleted, name) VALUES (1,_binary '\0','Fiction'),(2,_binary '\0','Non-Fiction'),(3,_binary '\0','Mystery'),(4,_binary '\0','Thriller'),(5,_binary '\0','Romance'),(6,_binary '\0','Science Fiction'),(7,_binary '\0','Fantasy'),(8,_binary '\0','Historical Fiction'),(9,_binary '\0','Biography'),(10,_binary '\0','Self-Help'),(11,_binary '\0','Travel'),(12,_binary '\0','Cookbook'),(13,_binary '\0','Poetry'),(14,_binary '\0','Philosophy'),(15,_binary '\0','Psychology');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` (id, is_deleted, name) VALUES (1,_binary '\0','Adventure'),(2,_binary '\0','Mystery'),(3,_binary '\0','Science Fiction'),(4,_binary '\0','Fantasy'),(5,_binary '\0','Romance'),(6,_binary '\0','Horror'),(7,_binary '\0','Thriller'),(8,_binary '\0','Historical Fiction'),(9,_binary '\0','Non-Fiction'),(10,_binary '\0','Biography'),(11,_binary '\0','Self-Help'),(12,_binary '\0','Cooking'),(13,_binary '\0','Travel'),(14,_binary '\0','Philosophy'),(15,_binary '\0','Psychology');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` (id, is_deleted, name) VALUES (1,_binary '\0','Alpha Book Publisher'),(2,_binary '\0','PT.Mizan Pustaka'),(3,_binary '\0','Noura Publishing'),(4,_binary '\0','GagasMedia'),(5,_binary '\0','Bentang Pustaka'),(6,_binary '\0','UGM Press - Academic Publisher'),(7,_binary '\0','Scritto Books Publisher'),(8,_binary '\0','E-Book Publishers'),(9,_binary '\0','Green Publisher'),(10,_binary '\0','MNC Publishing (Media Nusa Creative)'),(11,_binary '\0','Mentari Books'),(12,_binary '\0','Penerbit Charissa'),(13,_binary '\0','Shira Media Group'),(14,_binary '\0','PT Gramedia Pustaka Utama'),(15,_binary '\0','Pustaka Semesta Indonesia'),(16,_binary '\0','Global Books'),(17,_binary '\0','LeutikaPrio Self Publishing'),(18,_binary '\0','News Musik - Narya Nusa Musika PT :: Publishers - Magazine'),(19,_binary '\0','Popular Books'),(20,_binary '\0','AICS (ADI International Conference Series)'),(21,_binary '\0','Penerbit Koekoesan'),(22,_binary '\0','INSISTPress'),(23,_binary '\0','CV Andi Offset'),(24,_binary '\0','PT SINAR STAR BOOKS'),(25,_binary '\0','Bandar Publishing'),(26,_binary '\0','POST Bookshop'),(27,_binary '\0','Pustaka Nasional Pte Ltd'),(28,_binary '\0','MIC Publishing Jakarta'),(29,_binary '\0','Matayearbook'),(30,_binary '\0','Ideas Publishing (CV Komunitas Ide)'),(31,_binary '\0','Pelangi Publishing Group'),(32,_binary '\0','Eureka! Bookhouse'),(33,_binary '\0','Senayan Abadi'),(34,_binary '\0','Al-Hidayah Publication'),(35,_binary '\0','Periplus Lotte Shopping Avenue'),(36,_binary '\0','Jejak Publisher'),(37,_binary '\0','Almawardi Prima. PT'),(38,_binary '\0','Indonesian Publishers Association'),(39,_binary '\0','Gerakbudaya'),(40,_binary '\0','Gramedia Book Store'),(41,_binary '\0','HM Publisher');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (id, email, is_deleted, name, password, role, username, is_actives) VALUES (1,'admin@mail.com',_binary '\0','admin','$2a$10$X6Dae31zI6IlFBUkX4fpXOx8OHsdZT0O0bcNutQnWJ5rv6MKIwGe6','ADMIN','admin',_binary ''),(2,'user@mail.com',_binary '\0','user','$2a$10$gN/N7LjM3A3qtqbIAtFh/.BqgoYjLW3sTzTLIZPaqmINDwlUFSdHq','USER','user',_binary '');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-22 13:05:22
