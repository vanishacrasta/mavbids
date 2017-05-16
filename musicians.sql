-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 05, 2017 at 05:37 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `musicians`
--

-- --------------------------------------------------------

--
-- Table structure for table `musician`
--

CREATE TABLE `musician` (
  `musicianid` varchar(10) NOT NULL,
  `name` varchar(32) NOT NULL,
  `genre` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `musician`
--

INSERT INTO `musician` (`musicianid`, `name`, `genre`) VALUES
('1', 'Norah Jones', 'vocal'),
('2', 'Eminem', 'rap'),
('3', 'Lady Gaga', 'pop'),
('4', 'Shakira', 'latin'),
('5', 'Britney Spears', 'pop'),
('6', 'Chris Brown', 'classical '),
('7', 'Bruno Mars', 'funk'),
('8', 'Chris Taylor', 'Indie Pop');

-- --------------------------------------------------------

--
-- Table structure for table `performance`
--

CREATE TABLE `performance` (
  `performanceId` varchar(13) NOT NULL,
  `MonthYear` date NOT NULL,
  `musicianid` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `performance`
--

INSERT INTO `performance` (`performanceId`, `MonthYear`, `musicianid`) VALUES
('11', '2017-03-01', '1'),
('12', '2017-04-01', '2'),
('13', '2017-05-01', '3'),
('14', '2017-06-01', '4'),
('15', '2017-03-01', '5'),
('16', '2017-04-01', '6'),
('17', '2017-05-01', '7'),
('18', '2017-06-01', '8');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `musician`
--
ALTER TABLE `musician`
  ADD PRIMARY KEY (`musicianid`);

--
-- Indexes for table `performance`
--
ALTER TABLE `performance`
  ADD PRIMARY KEY (`performanceId`),
  ADD KEY `musicianid` (`musicianid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `performance`
--
ALTER TABLE `performance`
  ADD CONSTRAINT `performance_ibfk_1` FOREIGN KEY (`musicianid`) REFERENCES `musician` (`musicianid`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
