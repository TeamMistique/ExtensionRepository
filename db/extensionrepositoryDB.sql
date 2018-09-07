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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.extensions: ~7 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
REPLACE INTO `extensions` (`ExtensionID`, `Name`, `Description`, `Owner`, `Downloads`, `Link`, `Issues`, `PullRequests`, `LastCommit`, `FeaturedDate`, `PublishedDate`, `CreatedDate`, `Image`, `File`, `Version`, `LastSuccessfulSync`, `LastFailedSync`, `FailedSyncDetails`) VALUES
	(1, 'MyExtension', 'jasdlkfjd jsdkfljladlk KLJALKAJjlkdjk fkldjkl iu4897448-528986uti ijfgj8j8439 -3ioofjg so348  4 eyesrdf', 3, 0, 'https://github.com/arocketman/Spring-oauth2-jpa-example', 1, 1, '2018-08-10 20:55:35', '2019-08-22 00:00:00', '2018-08-22 00:00:00', '2018-08-22 15:22:43', 'http://localhost:8080/api/files/downloadFile/meredith%20grey%2011x12.png', 'http://localhost:8080/api/files/downloadFile/Learning_Agile_Understanding_Scrum__XP__Lean__and_Kanban.compressed.pdf', 1.1, '2018-09-07 00:42:29', NULL, NULL),
	(4, 'PMExtension', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 3, 517, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24 13:23:00', NULL, '2018-09-07 02:16:44', '2018-08-26 01:28:01', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 1.0, '2018-09-07 02:16:46', NULL, NULL),
	(6, 'Extension without tags', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 3, 0, 'https://github.com/biaedwards/Java-Alpha-Module-1', 0, 0, '2018-04-24 13:23:00', NULL, '2018-09-06 00:00:00', '2018-08-27 10:27:14', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 'http://localhost:8080/api/files/downloadFile/10429266_778373725578078_3388048229115365384_n.jpg', 1.0, '2018-09-07 00:20:59', NULL, NULL),
	(7, 'MyExtension', 'jasdlkfjd jsdkfljladlk KLJALKAJjlkdjk fkldjkl iu4897448-528986uti ijfgj8j8439 -3ioofjg so348  4 eyesrdf', 3, 0, 'https://github.com/arocketman/Spring-oauth2-jpa-example', 1, 1, '2018-08-10 20:55:35', NULL, '2018-09-07 02:16:17', '2018-08-27 10:35:10', 'http://localhost:8080/api/files/downloadFile/meredith%20grey%2011x12.png', 'http://localhost:8080/api/files/downloadFile/Learning_Agile_Understanding_Scrum__XP__Lean__and_Kanban.compressed.pdf', 1.1, '2018-09-07 02:16:20', NULL, NULL),
	(55, 'First', '', 6, 6, 'https://github.com/callicoder/spring-boot-file-upload-download-rest-api-example', 0, 0, '2018-07-09 15:43:53', NULL, '2018-09-05 00:00:00', '2018-09-05 08:06:52', 'http://localhost:8080/api/files/downloadFile/9fb9b8a4-912e-4e69-baa7-61457c807f08.png', 'http://localhost:8080/api/files/downloadFile/cba5fe70-3417-412d-a7c2-aa2164ccd3fa.png', 0.1, '2018-09-07 00:21:04', NULL, NULL),
	(56, 'Second', '', 6, 2, 'https://github.com/felixrieseberg/windows95', 41, 4, '2018-08-27 17:46:11', NULL, NULL, '2018-09-05 08:07:37', 'http://localhost:8080/api/files/downloadFile/5968787c-74af-49fd-b245-37fea4780292.png', 'http://localhost:8080/api/files/downloadFile/64e52014-fde1-42b5-8b05-679e276721dc.png', 0.1, '2018-09-07 00:42:54', NULL, NULL),
	(57, 'Admin extension', '', 4, 0, 'https://github.com/callicoder/spring-boot-file-upload-download-rest-api-example', 0, 0, '2018-07-09 15:43:53', '2018-09-05 00:00:00', '2018-09-05 00:00:00', '2018-09-05 09:19:58', 'http://localhost:8080/api/files/downloadFile/36b1c6fc-28a2-4566-8df7-b993842cc8a9.png', 'http://localhost:8080/api/files/downloadFile/f5245993-29ba-4790-b160-13a4908c9681.png', 0.0, '2018-09-07 00:36:05', NULL, NULL);
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

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

-- Dumping data for table extension_repository.extension_tag: ~12 rows (approximately)
/*!40000 ALTER TABLE `extension_tag` DISABLE KEYS */;
REPLACE INTO `extension_tag` (`ExtensionID`, `TagName`) VALUES
	(4, 'java'),
	(4, 'chome'),
	(7, 'hello'),
	(7, 'alabala'),
	(1, 'hello'),
	(1, 'alabala'),
	(55, 'maybe'),
	(55, 'edit'),
	(57, 'tag1'),
	(57, 'tag2'),
	(57, 'tag3'),
	(56, 'tag1');
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
REPLACE INTO `roles` (`RoleID`, `Role`) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table extension_repository.tags
DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `TagName` varchar(50) NOT NULL,
  PRIMARY KEY (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.tags: ~21 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
REPLACE INTO `tags` (`TagName`) VALUES
	('alabala'),
	('chome'),
	('edit'),
	('edit,'),
	('first'),
	('first, try'),
	('hello'),
	('hmm'),
	('java'),
	('maybe'),
	('mrrrr'),
	('newTag'),
	('reg_ex'),
	('tag1'),
	('tag2'),
	('tag3'),
	('tags'),
	('this'),
	('try'),
	('try,'),
	('yesPlease');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table extension_repository.users: ~6 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`UserID`, `Username`, `Password`, `Enabled`) VALUES
	(1, 'bibby', '$2y$12$7vZJl3crY/hORasf835w6OvN0k00qjqBSNRERhF.S2Gida60Bu3TS', 1),
	(2, 'radik', '$2y$12$B3Lxp5r7ZmlwxYoPK2RvqOeqDgtdkBIMWF5SJjEvHBl9FuCzX0M4.', 1),
	(3, 'testUser', '$2y$12$5J2x6Q75Inrkj0olQ6vU4../CDBYhZvvM4POaWizT7VUOKvEREjCq', 1),
	(4, 'admin', '$2a$10$yWryOEThV7qNpG7OoijzYeCTlQkgaUv6GjD1etykOmm6p912pGI56', 1),
	(5, 'admin2', '$2a$10$OVz93fRbEdA/mcNqmgeGgekNPcHM2f7J26t2NYEXa/J6zJ3uBiSsG', 1),
	(6, 'user', '$2a$10$no8cVC/lKk1FXPBpT6Ojb./aMzbb317SkSSPDlvZzfbvtdC/On67C', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

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

-- Dumping data for table extension_repository.users_roles: ~6 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
REPLACE INTO `users_roles` (`UserID`, `RoleID`) VALUES
	(3, 2),
	(4, 1),
	(4, 2),
	(1, 2),
	(2, 1),
	(6, 2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
