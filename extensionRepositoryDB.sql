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
CREATE DATABASE IF NOT EXISTS `extension_repository` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `extension_repository`;

-- Dumping structure for table extension_repository.extensions
CREATE TABLE IF NOT EXISTS `extensions` (
  `ExtensionID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` text NOT NULL,
  `Owner` int(11) NOT NULL,
  `Downloads` int(11) NOT NULL DEFAULT 0,
  `Link` varchar(200) NOT NULL,
  `Issues` int(11) NOT NULL DEFAULT 0,
  `PullRequests` int(11) NOT NULL DEFAULT 0,
  `LastCommit` date NOT NULL,
  `FeaturedDate` date DEFAULT NULL,
  `PublishedDate` date DEFAULT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `Image` varchar(500) NOT NULL,
  `File` varchar(500) NOT NULL,
  PRIMARY KEY (`ExtensionID`),
  KEY `FK_extensions_users` (`Owner`),
  CONSTRAINT `FK_extensions_users` FOREIGN KEY (`Owner`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extensions: ~3 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` (`ExtensionID`, `Name`, `Description`, `Owner`, `Downloads`, `Link`, `Issues`, `PullRequests`, `LastCommit`, `FeaturedDate`, `PublishedDate`, `CreatedDate`, `Image`, `File`) VALUES
	(1, 'Python', 'Python', 1, 0, 'link', 1, 1, '2018-08-22', '2019-08-22', '2018-08-22', '2018-08-22 15:22:43', 'Image1', 'File1'),
	(2, 'Extension1', 'new one', 2, 0, 'link1', 1, 0, '2018-08-22', '2018-08-22', NULL, '2018-08-22 15:26:27', 'Image2', 'File2'),
	(3, 'Extension 2', 'Extension2', 2, 0, 'link2', 0, 0, '2018-08-22', '2018-08-22', '2018-08-22', '2018-08-22 15:28:14', 'Image3', 'File3');
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping structure for table extension_repository.extension_tag
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
INSERT INTO `extension_tag` (`ExtensionID`, `TagID`) VALUES
	(1, 2);
/*!40000 ALTER TABLE `extension_tag` ENABLE KEYS */;

-- Dumping structure for table extension_repository.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `RoleID` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(50) NOT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`RoleID`, `Role`) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table extension_repository.tags
CREATE TABLE IF NOT EXISTS `tags` (
  `TagID` int(11) NOT NULL AUTO_INCREMENT,
  `TagName` varchar(50) NOT NULL,
  PRIMARY KEY (`TagID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.tags: ~2 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`TagID`, `TagName`) VALUES
	(1, 'python'),
	(2, 'debugger');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping structure for table extension_repository.users
CREATE TABLE IF NOT EXISTS `users` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(68) NOT NULL,
  `Enabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.users: ~5 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`UserID`, `Username`, `Password`, `Enabled`) VALUES
	(1, 'bibby', '$2y$12$7vZJl3crY/hORasf835w6OvN0k00qjqBSNRERhF.S2Gida60Bu3TS', 1),
	(2, 'radik', '$2y$12$B3Lxp5r7ZmlwxYoPK2RvqOeqDgtdkBIMWF5SJjEvHBl9FuCzX0M4.', 1),
	(3, 'testUser', '$2a$10$l654LlLsUsdRI0VFCR5ZMOiAaKmeMvFuQRxGx/RMF7bnLQZDp3K9O', 1),
	(4, 'admin', '$2a$10$yWryOEThV7qNpG7OoijzYeCTlQkgaUv6GjD1etykOmm6p912pGI56', 1),
	(5, 'admin2', '$2a$10$OVz93fRbEdA/mcNqmgeGgekNPcHM2f7J26t2NYEXa/J6zJ3uBiSsG', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table extension_repository.users_roles
CREATE TABLE IF NOT EXISTS `users_roles` (
  `UserID` int(11) NOT NULL,
  `RoleID` int(11) NOT NULL,
  KEY `FK_users_roles_users` (`UserID`),
  KEY `FK_users_roles_roles` (`RoleID`),
  CONSTRAINT `FK_users_roles_roles` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`),
  CONSTRAINT `FK_users_roles_users` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.users_roles: ~5 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`UserID`, `RoleID`) VALUES
	(3, 2),
	(4, 1),
	(4, 2),
	(1, 2),
	(2, 1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
