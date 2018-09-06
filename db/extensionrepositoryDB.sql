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

-- Dumping structure for table extension_repository.extensions
DROP TABLE IF EXISTS `extensions`;
CREATE TABLE IF NOT EXISTS `extensions` (
  `ExtensionID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` text NOT NULL,
  `Owner` int(11) NOT NULL,
  `Downloads` int(11) NOT NULL DEFAULT 0,
  `Link` varchar(200) NOT NULL,
  `Issues` int(11) DEFAULT 0,
  `PullRequests` int(11) DEFAULT 0,
  `LastCommit` timestamp NULL DEFAULT NULL,
  `FeaturedDate` timestamp NULL DEFAULT NULL,
  `PublishedDate` timestamp NULL DEFAULT NULL,
  `CreatedDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `Image` varchar(500) NOT NULL,
  `File` varchar(500) NOT NULL,
  `Version` decimal(10,1) NOT NULL DEFAULT 1.0,
  `LastSuccessfulSync` timestamp NULL DEFAULT NULL,
  `LastFailedSync` timestamp NULL DEFAULT NULL,
  `FailedSyncDetails` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ExtensionID`),
  KEY `FK_extensions_users` (`Owner`),
  CONSTRAINT `FK_extensions_users` FOREIGN KEY (`Owner`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table extension_repository.extension_tag
DROP TABLE IF EXISTS `extension_tag`;
CREATE TABLE IF NOT EXISTS `extension_tag` (
  `ExtensionID` int(11) NOT NULL,
  `TagName` varchar(50) NOT NULL,
  KEY `FK_extension_tag_extensions` (`ExtensionID`),
  KEY `FK_extension_tag_tags` (`TagName`),
  CONSTRAINT `FK_extension_tag_extensions` FOREIGN KEY (`ExtensionID`) REFERENCES `extensions` (`ExtensionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_extension_tag_tags` FOREIGN KEY (`TagName`) REFERENCES `tags` (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table extension_repository.roles
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `RoleID` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(50) NOT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table extension_repository.tags
DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `TagName` varchar(50) NOT NULL,
  PRIMARY KEY (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table extension_repository.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(68) NOT NULL,
  `Enabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table extension_repository.users_roles
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles` (
  `UserID` int(11) NOT NULL,
  `RoleID` int(11) NOT NULL,
  KEY `FK_users_roles_roles` (`RoleID`),
  KEY `FK_users_roles_users` (`UserID`),
  CONSTRAINT `FK_users_roles_roles` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_users_roles_users` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
