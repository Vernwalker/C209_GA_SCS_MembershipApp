-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2024 at 10:29 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gademodb`
--

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Category` varchar(20) NOT NULL,
  `MemberUntil` date DEFAULT NULL,
  `School` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`ID`, `Name`, `Category`, `MemberUntil`, `School`) VALUES
('M1001', 'Joe Fenandez', 'Ordinary', '2023-03-13', NULL),
('M1002', 'Connie Wong', 'Ordinary', '2024-07-18', NULL),
('M1003', 'Ahmad Ibrahim', 'Lifetime', NULL, NULL),
('M1004', 'Chan Chun Hang', 'Lifetime', NULL, NULL),
('M1005', 'Rani Selvarajah', 'Student', NULL, 'Temasek Polytechnic'),
('M1006', 'Toh Mui Leng', 'Ordinary', '2022-12-04', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
