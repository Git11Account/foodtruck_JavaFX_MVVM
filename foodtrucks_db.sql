-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 24 nov. 2025 à 04:59
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `foodtrucks_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `disponibilite`
--

DROP TABLE IF EXISTS `disponibilite`;
CREATE TABLE IF NOT EXISTS `disponibilite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `foodtruck_id` int DEFAULT NULL,
  `position_id` int DEFAULT NULL,
  `date_debut` datetime DEFAULT NULL,
  `date_fin` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodtruck_id` (`foodtruck_id`),
  KEY `position_id` (`position_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `foodtruck`
--

DROP TABLE IF EXISTS `foodtruck`;
CREATE TABLE IF NOT EXISTS `foodtruck` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int DEFAULT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `type_cuisine` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `is_validated` tinyint(1) DEFAULT '0',
  `description` text,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `status` enum('PENDING','APPROVED','REFUSED') DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `owner_id` (`owner_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `foodtrucks`
--

DROP TABLE IF EXISTS `foodtrucks`;
CREATE TABLE IF NOT EXISTS `foodtrucks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int DEFAULT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `description` text,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `status` enum('PENDING','APPROVED','REFUSED') DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `owner_id` (`owner_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `menus`
--

DROP TABLE IF EXISTS `menus`;
CREATE TABLE IF NOT EXISTS `menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `foodtruck_id` int DEFAULT NULL,
  `item` varchar(100) DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodtruck_id` (`foodtruck_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `owner_requests`
--

DROP TABLE IF EXISTS `owner_requests`;
CREATE TABLE IF NOT EXISTS `owner_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `status` varchar(20) DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `owner_requests`
--

INSERT INTO `owner_requests` (`id`, `user_id`, `nom`, `description`, `status`, `created_at`, `latitude`, `longitude`) VALUES
(1, 1, 'foodtruck', 'foodtruck1', 'approved', '2025-11-23 15:34:17', NULL, NULL),
(2, 1, 'f1', 'f1', 'approved', '2025-11-23 17:59:46', 48.86990906900769, 2.3418045043945317),
(3, 1, 'f2', 'f2', 'approved', '2025-11-23 18:04:25', 48.87363508768185, 2.346439361572266);

-- --------------------------------------------------------

--
-- Structure de la table `positions`
--

DROP TABLE IF EXISTS `positions`;
CREATE TABLE IF NOT EXISTS `positions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `requests`
--

DROP TABLE IF EXISTS `requests`;
CREATE TABLE IF NOT EXISTS `requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `nom` varchar(100) NOT NULL,
  `description` text,
  `statut` enum('PENDING','APPROVED','REFUSED') DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('USER','OWNER_PENDING','OWNER','ADMIN') DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'azerty', '$2a$10$MwAJdLo24.K2LKiHtKcCYucr8Flm204wdq2PQaY/i1Pr2q2SmXh4y', 'USER'),
(2, 'admin', '$2a$10$2Dk6fqZoiV3lbCyPDFiqeOL.3XcpJywlUW7YmHwfmmrCtcW1qcXRi', 'ADMIN');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
