-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for extension_repository
DROP DATABASE IF EXISTS `extension_repository`;
CREATE DATABASE IF NOT EXISTS `extension_repository` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `extension_repository`;

-- Dumping structure for table extension_repository.authorities
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `username_authority` (`username`,`authority`),
  CONSTRAINT `FK__users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.authorities: ~0 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- Dumping structure for table extension_repository.extensions
DROP TABLE IF EXISTS `extensions`;
CREATE TABLE IF NOT EXISTS `extensions` (
  `ExtensionID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` text NOT NULL,
  `Owner` varchar(50) NOT NULL,
  `Downloads` int(11) NOT NULL DEFAULT 0,
  `Link` varchar(200) NOT NULL,
  `Issues` int(11) NOT NULL DEFAULT 0,
  `PullRequests` int(11) NOT NULL DEFAULT 0,
  `LastCommit` date NOT NULL,
  `FeaturedDate` date DEFAULT NULL,
  `PublishedDate` date DEFAULT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `ImageID` int(11) NOT NULL,
  `FileID` int(11) NOT NULL,
  PRIMARY KEY (`ExtensionID`),
  KEY `FK_extensions_users` (`Owner`),
  KEY `FK_extensions_images` (`ImageID`),
  KEY `FK_extensions_files` (`FileID`),
  CONSTRAINT `FK_extensions_files` FOREIGN KEY (`FileID`) REFERENCES `files` (`FileID`),
  CONSTRAINT `FK_extensions_images` FOREIGN KEY (`ImageID`) REFERENCES `images` (`ImageID`),
  CONSTRAINT `FK_extensions_users` FOREIGN KEY (`Owner`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extensions: ~3 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
REPLACE INTO `extensions` (`ExtensionID`, `Name`, `Description`, `Owner`, `Downloads`, `Link`, `Issues`, `PullRequests`, `LastCommit`, `FeaturedDate`, `PublishedDate`, `CreatedDate`, `ImageID`, `FileID`) VALUES
	(1, 'Python', 'Python', 'user', 0, 'link', 1, 1, '2018-08-22', '2019-08-22', '2018-08-22', '2018-08-22 15:22:43', 1, 1),
	(2, 'Extension1', 'new one', 'Bibby', 0, 'link1', 1, 0, '2018-08-22', '2018-08-22', NULL, '2018-08-22 15:26:27', 2, 2),
	(3, 'Extension 2', 'Extension2', 'Radik', 0, 'link2', 0, 0, '2018-08-22', '2018-08-22', '2018-08-22', '2018-08-22 15:28:14', 3, 3);
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping structure for table extension_repository.extension_tag
DROP TABLE IF EXISTS `extension_tag`;
CREATE TABLE IF NOT EXISTS `extension_tag` (
  `ExtensionID` int(11) DEFAULT NULL,
  `TagID` int(11) DEFAULT NULL,
  KEY `FK_extension_tag_extensions` (`ExtensionID`),
  KEY `FK_extension_tag_tags` (`TagID`),
  CONSTRAINT `FK_extension_tag_extensions` FOREIGN KEY (`ExtensionID`) REFERENCES `extensions` (`ExtensionID`),
  CONSTRAINT `FK_extension_tag_tags` FOREIGN KEY (`TagID`) REFERENCES `tags` (`TagID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extension_tag: ~1 rows (approximately)
/*!40000 ALTER TABLE `extension_tag` DISABLE KEYS */;
REPLACE INTO `extension_tag` (`ExtensionID`, `TagID`) VALUES
	(1, 2);
/*!40000 ALTER TABLE `extension_tag` ENABLE KEYS */;

-- Dumping structure for table extension_repository.files
DROP TABLE IF EXISTS `files`;
CREATE TABLE IF NOT EXISTS `files` (
  `FileID` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(50) NOT NULL,
  `DownloadUri` varchar(50) NOT NULL,
  `FileType` varchar(50) NOT NULL,
  `Size` int(11) NOT NULL,
  PRIMARY KEY (`FileID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.files: ~3 rows (approximately)
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
REPLACE INTO `files` (`FileID`, `FileName`, `DownloadUri`, `FileType`, `Size`) VALUES
	(1, 'file', 'url', 'doc', 12),
	(2, 'file1', 'url1', 'doc1', 12),
	(3, 'file2', 'url2', 'doc2', 12);
/*!40000 ALTER TABLE `files` ENABLE KEYS */;

-- Dumping structure for table extension_repository.images
DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `ImageID` int(11) NOT NULL AUTO_INCREMENT,
  `ImagePath` varchar(50) NOT NULL,
  PRIMARY KEY (`ImageID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.images: ~3 rows (approximately)
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
REPLACE INTO `images` (`ImageID`, `ImagePath`) VALUES
	(1, 'image'),
	(2, 'image2'),
	(3, 'image3');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;

-- Dumping structure for table extension_repository.tags
DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `TagID` int(11) NOT NULL AUTO_INCREMENT,
  `TagName` varchar(50) NOT NULL,
  PRIMARY KEY (`TagID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.tags: ~2 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
REPLACE INTO `tags` (`TagID`, `TagName`) VALUES
	(1, 'python'),
	(2, 'debugger');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping structure for table extension_repository.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.users: ~3 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`username`, `password`, `enabled`) VALUES
	('Bibby', '123', 1),
	('Radik', '123', 1),
	('user', '123', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
