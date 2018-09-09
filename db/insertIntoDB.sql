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

-- Dumping data for table extension_repository.extensions: ~6 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` (`ExtensionID`, `Name`, `Description`, `Owner`, `Downloads`, `Link`, `Issues`, `PullRequests`, `LastCommit`, `FeaturedDate`, `PublishedDate`, `CreatedDate`, `Image`, `File`, `Version`, `LastSuccessfulSync`, `LastFailedSync`, `FailedSyncDetails`) VALUES
	(69, 'Vim', 'Vim is a highly configurable text editor for efficiently creating and changing any kind of text. It is included as "vi" with most UNIX systems and with Apple OS X. \n\nVim is rock stable and is continuously being developed to become even better. Among its features are:\n- persistent, multi-level undo tree\n- extensive plugin system\n- support for hundreds of programming languages and file formats\n- powerful search and replace\n- integrates with many tools', 2, 1, 'https://github.com/VSCodeVim/Vim', 474, 30, '2018-09-09 06:13:24', NULL, '2018-09-10 01:15:57', '2018-09-10 00:32:07', 'http://localhost:8080/api/files/downloadFile/5c7b3647-499c-448d-b25f-c0f45a29f0ae.png', 'http://localhost:8080/api/files/downloadFile/6720a080-a8d1-4a3c-9c7c-ed725a790d93.vsix', 1.0, '2018-09-10 01:16:00', NULL, NULL),
	(70, 'indent-rainbow', 'This extension colorizes the indentation in front of your text alternating four different colors on each step. Some may find it helpful in writing code for Nim or Python.', 2, 28, 'https://github.com/oderwat/vscode-indent-rainbow', 1, 5, '2018-08-17 14:48:50', NULL, '2018-09-10 01:10:52', '2018-09-10 00:39:18', 'http://localhost:8080/api/files/downloadFile/9245222c-0280-4805-9dc6-08e831bebbad.png', 'http://localhost:8080/api/files/downloadFile/670ecdbb-391b-4f6a-a68a-fa1e2ac50593.vsix', 1.0, '2018-09-10 01:10:55', NULL, NULL),
	(71, 'Night Owl', 'A VS Code theme for the night owls out there. Works well in the daytime, too, but this theme is fine-tuned for those of us who like to code late into the night. Color choices have taken into consideration what is accessible to people with colorblindness and in low-light circumstances. Decisions were also based on meaningful contrast for reading comprehension and for optimal razzle dazzle. ?\n\nAbout this theme, and some of the considerations made while creating it (as well as how to create it should you want to make your own): https://css-tricks.com/creating-a-vs-code-theme/', 2, 12, 'https://github.com/sdras/night-owl-vscode-theme', 14, 30, '2018-08-04 03:05:56', '2018-09-10 01:19:24', '2018-09-10 01:15:43', '2018-09-10 00:42:53', 'http://localhost:8080/api/files/downloadFile/ca23d8d2-bb7a-4680-84f9-bb279212c701.png', 'http://localhost:8080/api/files/downloadFile/6e9f4493-9b0a-4f97-b3bd-3f128e0c4b75.vsix', 1.0, '2018-09-10 01:15:46', NULL, NULL),
	(72, 'Debugger for Chrome', 'A VS Code extension to debug your JavaScript code in the Google Chrome browser, or other targets that support the Chrome DevTools Protocol.\n\nSupported features\n\nSetting breakpoints, including in source files when source maps are enabled\nStepping, including with the buttons on the Chrome page\nThe Locals pane\nDebugging eval scripts, script tags, and scripts that are added dynamically\nWatches\nConsole\nUnsupported scenarios\n\nDebugging web workers\nAny features that aren\'t script debugging.', 11, 6, 'https://github.com/Microsoft/vscode-chrome-debug', 63, 30, '2018-09-06 19:54:03', '2018-09-10 01:15:29', '2018-09-10 01:14:56', '2018-09-10 00:46:08', 'http://localhost:8080/api/files/downloadFile/e0790feb-981e-4a0d-9c6d-4a62639279da.png', 'http://localhost:8080/api/files/downloadFile/40f56878-8c70-44d5-ba23-aca6fc404a7f.vsix', 1.0, '2018-09-10 01:14:59', NULL, NULL),
	(73, 'Python Test Explorer', 'Features\nShows a Test Explorer in the Test view in VS Code\'s sidebar with all detected tests and suites and their state\nShows a failed test\'s log when the test is selected in the explorer\nSupports multi-root workspaces', 11, 0, 'https://github.com/kondratyev-nv/vscode-python-test-adapter.git', 0, 0, NULL, NULL, '2018-09-10 01:15:20', '2018-09-10 00:55:57', 'http://localhost:8080/api/files/downloadFile/6bd64efa-71d2-48fb-8161-4151c6e3f8fe.png', 'http://localhost:8080/api/files/downloadFile/cd452fe1-b969-469b-b3b6-1f650b7e27aa.vsix', 1.0, NULL, '2018-09-10 01:15:20', 'Could not get number of issues.'),
	(74, 'clipboard with formatter', 'Optimize your copy and paste.Keep a history of your copied items and re-paste if needed. What\'s more? it can format your copied code segment.\n\nFeatures\nAs we can see on VSCode marketplace, there are some extensions on clipboard. But I haven\'t seen a clipboard with formatter features. If we try to rewrite some project, several past variables & class & function were useful. After we copied them, the details about them became mistery. That\'s why I code this.\n\nEnjoy!', 12, 1, 'https://github.com/yexiaosong/vscode-clipboard-with-formatter/', 0, 0, NULL, '2018-09-10 01:19:16', '2018-09-10 01:15:51', '2018-09-10 00:57:40', 'http://localhost:8080/api/files/downloadFile/db20637b-7635-4800-a011-a819a48e973a.png', 'http://localhost:8080/api/files/downloadFile/1546f81d-6eaa-4472-ba59-2105343d2c5a.vsix', 1.0, NULL, '2018-09-10 01:15:51', 'Could not get number of issues.'),
	(75, 'ESLint', 'VS Code ESLint extension\nIntegrates ESLint into VS Code. If you are new to ESLint check the documentation.\n\nThe extension uses the ESLint library installed in the opened workspace folder. If the folder doesn\'t provide one the extension looks for a global install version. If you haven\'t installed ESLint either locally or globally do so by running npm install eslint in the workspace folder for a local install or npm install -g eslint for a global install.\n\nOn new folders you might also need to create a .eslintrc configuration file. You can do this by either using the VS Code command Create ESLint configuration or by running the eslint command in a terminal. If you have installed ESLint globally (see above) then run eslint --init in a terminal. If you have installed ESLint locally then run .\\node_modules\\.bin\\eslint --init under Windows and ./node_modules/.bin/eslint --init under Linux and Mac.', 12, 34, 'https://github.com/Microsoft/vscode-eslint.git', 0, 0, NULL, NULL, '2018-09-10 01:15:38', '2018-09-10 00:59:10', 'http://localhost:8080/api/files/downloadFile/f3dca236-6ace-48d3-8812-528bc32123d3.png', 'http://localhost:8080/api/files/downloadFile/70a144d2-c86d-4fc3-ad85-f1eb35b6cbd0.vsix', 1.0, NULL, '2018-09-10 01:15:38', 'Could not get number of issues.'),
	(76, 'C/C++', 'This preview release of the extension adds language support for C/C++ to Visual Studio Code including:\n\nLanguage service\nCode Formatting (clang-format)\nAuto-Completion (experimental)\nSymbol Searching\nGo to Definition/Declaration\nPeek Definition/Declaration\nClass/Method Navigation\nSignature Help\nQuick Info (Hover)\nError Squiggles', 2, 0, 'https://github.com/Microsoft/vscode-cpptools', 0, 0, NULL, NULL, NULL, '2018-09-10 01:48:38', 'http://localhost:8080/api/files/downloadFile/162b2393-2cdd-4ffc-8610-f4d06fd0bef7.png', 'http://localhost:8080/api/files/downloadFile/e744be8e-7dac-4495-a619-4536cd774ce7.vsix', 1.0, NULL, NULL, NULL);
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping data for table extension_repository.extension_tag: ~25 rows (approximately)
/*!40000 ALTER TABLE `extension_tag` DISABLE KEYS */;
INSERT INTO `extension_tag` (`ExtensionID`, `TagName`) VALUES
	(69, 'text'),
	(69, 'editor'),
	(69, 'text_editor'),
	(69, 'code'),
	(69, 'vim'),
	(69, 'windows'),
	(69, 'mac'),
	(70, 'rainbow'),
	(70, 'code'),
	(70, 'colorful'),
	(71, 'theme'),
	(71, 'colorful'),
	(71, 'vscode'),
	(72, 'chrome'),
	(72, 'js'),
	(72, 'javascript'),
	(72, 'microsoft'),
	(73, 'python'),
	(73, 'code'),
	(73, 'vscode'),
	(73, 'test'),
	(74, 'clipboard'),
	(74, 'code'),
	(74, 'basic'),
	(74, 'format'),
	(75, 'vscode'),
	(75, 'lint'),
	(75, 'terminal'),
	(76, 'c'),
	(76, 'c'),
	(76, 'debugger'),
	(76, 'Microsoft');
/*!40000 ALTER TABLE `extension_tag` ENABLE KEYS */;

-- Dumping data for table extension_repository.tags: ~22 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`TagName`) VALUES
	('basic'),
	('c'),
	('chrome'),
	('clipboard'),
	('code'),
	('colorful'),
	('debugger'),
	('editor'),
	('format'),
	('javascript'),
	('js'),
	('lint'),
	('mac'),
	('microsoft'),
	('python'),
	('rainbow'),
	('terminal'),
	('test'),
	('text'),
	('text_editor'),
	('theme'),
	('vim'),
	('vscode'),
	('windows');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping data for table extension_repository.users: ~4 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`UserID`, `Username`, `Password`, `Enabled`) VALUES
	(11, 'JuliaRoberts', '$2a$10$mdL4eve4acIz0YulOGqbA.6x25DI2szl7Eyin2WP4Ra2ctqlDIM7i', 1),
	(12, 'Marco87', '$2a$10$k.9K1H6ROYh6xZNYDxN6Q.3zB1TBfQyDAYEWIwwJZ5rvoLV.96jn2', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping data for table extension_repository.users_roles: ~4 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`UserID`, `RoleID`) VALUES
	(11, 2),
	(12, 2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
