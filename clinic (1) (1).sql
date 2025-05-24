-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 06, 2024 at 12:37 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clinic`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `UserName` varchar(300) NOT NULL,
  `Password` varchar(300) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`UserName`, `Password`) VALUES
('admin', '12345'),
('admin', '12345');

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `appointmentID` int(11) NOT NULL,
  `appointmentDate` date NOT NULL,
  `patientName` varchar(300) NOT NULL,
  `doctorName` varchar(200) NOT NULL,
  `appointmentStatus` varchar(50) NOT NULL,
  `description` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`appointmentID`, `appointmentDate`, `patientName`, `doctorName`, `appointmentStatus`, `description`) VALUES
(2, '2024-06-15', 'Jane Smith', 'Dr. Mohamed', 'Scheduled', 'Follow-up visit'),
(3, '2024-06-05', 'mariam', 'rawan', 'booked', 'asjxjjgdja'),
(4, '2024-06-05', 'Rawan', 'walid', 'booked', 'jkjs'),
(5, '2024-06-05', 'rawan', 'jkjc', 'Procedure', 'kjjhkv'),
(6, '2024-06-05', '', '', 'Checkup', ''),
(7, '2024-06-05', 'jhbjc', 'sjkj', 'Follow Up', ''),
(8, '2024-12-20', 'farah', 'Ahmed', 'Checkup', 'ill'),
(9, '2024-08-20', 'farah', 'Ahmed', 'Procedure', 'Back pain');

-- --------------------------------------------------------

--
-- Table structure for table `assistant`
--

CREATE TABLE `assistant` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assistant`
--

INSERT INTO `assistant` (`id`, `name`, `email`, `phone`, `password`) VALUES
(1, 'Farah Tarek', 'farah@example.com', '01234567890', '12345'),
(2, 'farah', 'farah@example.com', '01234567890', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `D_ID` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `Phone` varchar(200) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`D_ID`, `Name`, `Email`, `Phone`, `password`, `specialty`) VALUES
(1, 'Dr.Zeina', 'Zeina@gmail.com', '010468528965', '12345', 'Physiotherapy'),
(3, 'rawan', 'rawan@gmail.com', '01123565590', '123456', 'hbhj'),
(4, 'rawan', 'jkjnjb', '012154', '5255', 'hjj'),
(5, 'Ahmed', 'Ahmed@gmail.com', '010598598', '1234', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `P_ID` int(11) NOT NULL,
  `Email` varchar(300) NOT NULL,
  `Name` varchar(300) NOT NULL,
  `Medicine` varchar(300) DEFAULT NULL,
  `Age` int(11) NOT NULL,
  `Gender` varchar(300) NOT NULL,
  `appointment` datetime DEFAULT NULL,
  `prescriptions` text DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`P_ID`, `Email`, `Name`, `Medicine`, `Age`, `Gender`, `appointment`, `prescriptions`, `Phone`) VALUES
(1, 'farah@gmail.com', 'Omar Hany', 'Mentos', 22, 'Male', NULL, NULL, NULL),
(5, 'mjnj', 'rawan', NULL, 10, 'Female', NULL, NULL, NULL),
(3, 'Mariam@gmail.com', 'Mariam', NULL, 20, 'Female', NULL, NULL, NULL),
(4, 'jbkjb', 'jkkjkn', NULL, 20, 'Male', NULL, NULL, NULL),
(6, 'farah@gmail.com', 'farah', 'panadole', 0, 'female', NULL, NULL, NULL),
(7, 'farah1@gmail.com', 'farahtarek', NULL, 0, 'female', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`appointmentID`);

--
-- Indexes for table `assistant`
--
ALTER TABLE `assistant`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`D_ID`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`P_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
  MODIFY `appointmentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `assistant`
--
ALTER TABLE `assistant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `D_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `P_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
