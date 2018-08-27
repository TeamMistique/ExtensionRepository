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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extensions: ~5 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` (`ExtensionID`, `Name`, `Description`, `Owner`, `Downloads`, `Link`, `Issues`, `PullRequests`, `LastCommit`, `FeaturedDate`, `PublishedDate`, `CreatedDate`, `Image`, `File`) VALUES
	(1, 'Python', 'Python', 1, 0, 'link', 1, 1, '2018-08-22', '2019-08-22', '2018-08-22', '2018-08-22 15:22:43', 'Image1', 'File1'),
	(2, 'Extension1', 'new one', 2, 0, 'link1', 1, 0, '2018-08-22', '2018-08-22', NULL, '2018-08-22 15:26:27', 'Image2', 'File2'),
	(3, 'Extension 2', 'Extension2', 2, 0, 'link2', 0, 0, '2018-08-22', '2018-08-22', '2018-08-22', '2018-08-22 15:28:14', 'Image3', 'File3'),
	(4, 'PMExtension', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 3, 0, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24', NULL, NULL, '2018-08-26 01:28:01', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/The%20Archetype%20DIet.pdf'),
	(5, 'RBextension', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 3, 0, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24', NULL, NULL, '2018-08-26 14:01:44', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/The%20Archetype%20DIet.pdf'),
	(6, 'Extension without tags', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 3, 0, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24', NULL, NULL, '2018-08-27 10:27:14', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/The%20Archetype%20DIet.pdf'),
	(7, 'Extension with tags - EDITED', 'Lorem ipsum dolor sit amet', 3, 0, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24', NULL, NULL, '2018-08-27 10:35:10', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/The%20Archetype%20DIet.pdf');
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping structure for table extension_repository.extension_tag
DROP TABLE IF EXISTS `extension_tag`;
CREATE TABLE IF NOT EXISTS `extension_tag` (
  `ExtensionID` int(11) NOT NULL,
  `TagName` varchar(50) NOT NULL,
  KEY `FK_extension_tag_extensions` (`ExtensionID`),
  KEY `FK_extension_tag_tags` (`TagName`),
  CONSTRAINT `FK_extension_tag_extensions` FOREIGN KEY (`ExtensionID`) REFERENCES `extensions` (`ExtensionID`),
  CONSTRAINT `FK_extension_tag_tags` FOREIGN KEY (`TagName`) REFERENCES `tags` (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extension_tag: ~2 rows (approximately)
/*!40000 ALTER TABLE `extension_tag` DISABLE KEYS */;
INSERT INTO `extension_tag` (`ExtensionID`, `TagName`) VALUES
	(4, 'java'),
	(2, 'chome'),
	(4, 'chome'),
	(7, 'tag1'),
	(7, 'newTag');
/*!40000 ALTER TABLE `extension_tag` ENABLE KEYS */;

-- Dumping structure for table extension_repository.roles
DROP TABLE IF EXISTS `roles`;
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
DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `TagName` varchar(50) NOT NULL,
  PRIMARY KEY (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.tags: ~2 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`TagName`) VALUES
	('chome'),
	('java'),
	('newTag'),
	('tag1'),
	('tag2');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping structure for table extension_repository.users
DROP TABLE IF EXISTS `users`;
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
DROP TABLE IF EXISTS `users_roles`;
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
