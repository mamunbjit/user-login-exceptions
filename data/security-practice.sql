-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 07, 2023 at 12:01 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `security-practice`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `user_id`) VALUES
(1, 'user3@example.com', '$2a$10$WpzZYkqfaXcjt4d0smLpPuIVb3v2.0Iuu0Ue6QVrSHjkenqKeA.8K', 'z2BeqNTCFt'),
(2, 'user6@example.com', '$2a$10$aO2YqRnfYLTRjVQr.iBQzu1ldusv5eAAdZSnB.coZjbIQkX/1iNGC', 'X1GOcmanJV'),
(3, 'user7@example.com', '$2a$10$eW9CCiYnIsQgJexlpACqJuYyGuyqPG6aeGp0D81v3VAocZ/UzcROG', 'GzeE186Qki'),
(4, 'user8@example.com', '$2a$10$OxvZDBm4IS2QqFjb9jGND.aiU1Gt1vIyjrW6IYWC7Z.voCDhxmd7O', '0azYpOcoc2'),
(5, 'user@example.com', '$2a$10$/chR/XYmnSocFK931g2zyuriLIhZ9lvpU0w/x4HKEp4NruSINTvjO', 's5XblHKjXe');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
