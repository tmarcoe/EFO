-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2018 at 12:49 PM
-- Server version: 5.7.17-log
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `efo`
--

--
-- Dumping data for table `user`
--

INSERT IGNORE INTO `user` (`user_id`, `enabled`, `password`, `temp_pw`, `username`) VALUES
(1, b'0', '$2a$10$QFxwISh5JvXg8ztNMkkZ7u1VdWhEIDd/0tYa6fNRNst1KKrf0PzDC', b'0', 'New@new.com'),
(3, b'0', '$2a$10$cSu8xPutXlpFC2ESmxgc9OMeO.sJJwK8dqu4wTfTzgGPa/pLfhIPm', b'1', 'tala@coffeeforpeace.com'),
(4, b'0', '$2a$10$HAw6nvBE9u24NYBviuj8WOqSk1L47jXLjmtKOgjDY1aN5JXo8Ubo2', b'1', 'jsmith@sr.com'),
(5, b'0', '$2a$10$5jPqDzu4itRZrzdiP5QkVubpBsU4.5rrxcWLmhMZ0T6ix3lKnXcPm', b'1', 'tregis@nccc.com'),
(6, b'0', '$2a$10$Mx97WUqKFptcv1MQYhTNuObFzY.c9l2MQEDlzvvTcMV6eHJFgrbym', b'1', 'yupangco.rick@ayalaland.com.ph'),
(7, b'0', '$2a$10$BNPPWAjmq0pXd7KK/Hnjoe.ox8GuTNdedgs/rMFc5.nXLDdHrVYSm', b'1', 'sally@fields.com'),
(8, b'1', '$2a$10$XKo6xneAE4CoZYzoZFtbg.GG0Z9BmzX3I.fZoutMfae6N54eEUuvy', b'0', 'tmtmarcoe80@gmail.com'),
(9, b'0', '$2a$10$Vfo2IKv2G5m1OR9T1cRdr.39RjDtMyHPtPC3pfSw4AZNkJyki4Xzm', b'1', 'julie@roob.com'),
(10, b'0', '$2a$10$BA4O3ge0yIcCtUwYe9Uo6erjdnGMXrA.XIK2rFBlW6ZywC.NXR9q.', b'1', 'michael@carr.com'),
(11, b'0', '$2a$10$AF8Qn7Af95I5pp.fkToFi.YtjwZctMO6/aMmUVP6WWWiZUtnqF/n2', b'1', 'vjmom@earthlink.com'),
(12, b'0', '$2a$10$nVGftiGmofpezXFEYIktSOwpCi.idzTW1UfU733DOwxB9of66pwSq', b'1', 'mvincent@kinkos.com'),
(13, b'0', '$2a$10$2OHk1g5nRcHd8M6NZsks2.5kXU/1kbocvxEjKq4KYF3ZmwQCf7NbO', b'1', 'jtilden@officedepot.com'),
(14, b'0', '$2a$10$pOqGkwOihLFgIEUTJxqcWOg3XOyRn5IFToX.o.49kXIt3jdKdMKd6', b'1', 'bbertelsen@progress.com');

INSERT IGNORE INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(1, 2),
(8, 2),
(1, 3),
(8, 3),
(1, 4),
(8, 4),
(1, 5),
(8, 5);

INSERT IGNORE INTO `role` (`role_id`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'SUPERVISOR'),
(4, 'ACCOUNTING'),
(5, 'ROOT');

INSERT IGNORE INTO `product` (`sku`, `category`, `consignment`, `discontinue`, `keywords`, `on_sale`, `price`, `product_name`, `subcategory`, `unit`, `upc`) VALUES
('047213818367', 'Clothing', b'0', b'0', '', b'0', 150, 'White Gown Set', 'Baby Clothes', 'Each', '047213818367'),
('047213842690', 'Clothing', b'0', b'0', '', b'0', 100, 'Training Pants', 'Baby Clothes', 'Each', '047213842690'),
('047213958117', 'Clothing', b'0', b'0', '', b'0', 150, 'Bodysuit', 'Baby Clothes', 'Each', '047213958117'),
('3700591213007', '', b'0', b'0', '', b'0', 4000, 'Atelier Cologne Cedrat Envirant Cologne Absolue', '', 'Each', '3700591213007'),
('3700591216008', '', b'0', b'0', '', b'0', 2000, 'Atelier Cologne Pomelo Paradis Cologne Absolue', '', 'Each', '3700591216008'),
('3700591228001', '', b'0', b'0', '', b'0', 1000, 'Atelier Cologne Mimosa Indigo Cologne Absolue', '', 'Each', '3700591228001'),
('4360436115201', '', b'0', b'0', '', b'0', 6000, 'Patio Furniture Outdoor Lawn Set', '', 'Each', '4360436115201'),
('4360857273412', '', b'0', b'0', '', b'0', 7000, 'Patio Furniture Outdoor Lawn Chairs', '', 'Each', '4360857273412'),
('5060317313090', '', b'0', b'0', '', b'0', 250, 'Precision Kitchenware - Ultra Sharp Pizza Cutter ', '', 'Each', '5060317313090'),
('659061945623', '', b'0', b'0', '', b'0', 200, 'Relationship Mug \\ Mugs With Quotes by Vitazi Kitchenware', '', 'Each', '659061945623'),
('680474422364', '', b'0', b'0', '', b'0', 500, 'Eiffel Tower Sterling Silver Charm', '', 'Each', '680474422364'),
('680474422708', '', b'0', b'0', '', b'0', 500, 'Sterling Silver Dog Bone Charm Pendant', '', 'Each', '680474422708'),
('680474422876', '', b'0', b'0', '', b'0', 500, 'Statue Of Liberty Charm', '', 'Each', '680474422876'),
('684758033246', '', b'0', b'0', '', b'0', 500, 'Open Your Heart Sterling Silver Charm', '', 'Each', '684758033246'),
('7109767711013', '', b'0', b'0', '', b'0', 2000, 'Aluminum Patio Furniture Antique Bronze Bistro Set', '', 'Each', '7109767711013'),
('753807320047', '', b'0', b'0', '', b'0', 500, 'My Princess Clear Ring', '', 'Each', '753807320047'),
('753807369152', '', b'0', b'0', '', b'0', 500, 'Disney Mickey & Minnie Infinity', '', 'Each', '753807369152'),
('753807369244', '', b'0', b'0', '', b'0', 500, 'Pave Lights Charm Red', '', 'Each', '753807369244'),
('754097170671', 'Perfume', b'0', b'0', '', b'0', 500, 'Eau De Parfum Spray', 'Spray', 'Each', '754097170671'),
('754097173078', 'Perfume', b'0', b'0', '', b'0', 2500, 'Miniatures 5 PC Set ', 'Women\'s Perfume', 'Each', '754097173078'),
('754097177410', 'Perfume', b'0', b'0', '', b'0', 1000, 'Gucci Rush Women\'s Perfume', 'Women\'s Perfume', 'Each', '754097177410'),
('761656326882', '', b'0', b'0', '', b'0', 2000, 'Patio Furniture-Patio Umbrella-Premium® Patio Furniture', '', 'Each', '761656326882'),
('762111766113', 'Beverage', b'0', b'0', '', b'0', 200, 'Italian Roast Coffee', 'Coffee', 'Each', '762111766113'),
('762111903334', 'Beverage', b'0', b'0', '', b'0', 200, 'French Roast', 'Coffee', 'Each', '762111903334'),
('762111903358', 'Beverage', b'0', b'0', '', b'0', 200, 'Breakfast Blend Coffee', 'Coffee', 'Each', '762111903358'),
('895063002876', '', b'0', b'0', '', b'0', 500, 'Amore Kitchenware 12" Pan or Wok Glass Lid', '', 'Each', '895063002876');

INSERT IGNORE INTO `inventory` (`sku`, `amt_committed`, `amt_in_stock`, `amt_ordered`, `min_amount`) VALUES
('047213818367', 1, 9, 0, 10),
('047213842690', 0, 10, 0, 10),
('047213958117', 1, 9, 0, 10),
('3700591213007', 0, 0, 0, 10),
('3700591216008', 0, 0, 0, 10),
('3700591228001', 0, 0, 0, 10),
('4360436115201', 0, 0, 0, 10),
('4360857273412', 0, 0, 0, 10),
('5060317313090', 0, 0, 0, 10),
('659061945623', 0, 10, 0, 10),
('680474422364', 1, 9, 0, 10),
('680474422708', 0, 10, 0, 10),
('680474422876', 1, 9, 0, 10),
('684758033246', 0, 0, 0, 10),
('7109767711013', 0, 0, 0, 10),
('753807320047', 0, 0, 0, 10),
('753807369152', 0, 0, 0, 10),
('753807369244', 0, 0, 0, 10),
('754097170671', 0, 0, 0, 10),
('754097173078', 0, 0, 0, 10),
('754097177410', 0, 0, 0, 10),
('761656326882', 0, 0, 0, 10),
('762111766113', 0, 10, 0, 10),
('762111903334', 0, 10, 0, 10),
('762111903358', 1, 9, 0, 10),
('895063002876', 0, 0, 0, 10);

INSERT IGNORE INTO `employee` (`user_id`, `cell_phone`, `company`, `division`, `dnr`, `emer_contact`, `emer_ph`, `emp_type`, `end_date`, `extension`, `firstname`, `home_phone`, `lastname`, `male_female`, `office_loc`, `position`, `salutation`, `start_date`, `supervisor`) VALUES
(1, '', '', '', b'0', '', '', 'F', '2100-12-31 00:00:00', '', 'New', '', 'Employee', 'M', '', '', 'Mr.', '2018-06-09 00:00:00', ''),
(8, '', 'EFO', '', b'0', '', '', 'F', '2100-12-31 00:00:00', '', 'Timothy', '', 'Marcoe', 'M', '', 'Programmer', 'Mr.', '2018-06-28 00:00:00', '');

INSERT IGNORE INTO `emp_financial` (`user_id`, `account_num`, `f_tax_prcnt`, `f_un_prcnt`, `fd_exempt`, `garnishment`, `hourly_rate`, `med_prcnt`, `other`, `other_expl`, `pay_method`, `retire_prcnt`, `routing_num`, `s_tax_prcnt`, `s_un_prcnt`, `ssi_prcnt`, `st_exempt`, `tax_id`) VALUES
(1, '', 0, 0, 0, 0, 0, 0, 0, '', 'Check', 0, '', 0, 0, 0, 0, ''),
(8, '', 0, 0, 1, 0, 0, 0, 0, '', 'Check', 0, '', 0, 0, 0, 1, '2222222');

INSERT IGNORE INTO `customer` (`user_id`, `firstname`, `lastname`, `male_female`, `salutation`, `since`) VALUES
(9, 'Julie', 'Roob', 'F', 'Mrs.', '2018-06-29 00:00:00'),
(10, 'Michael', 'Carr', 'M', 'Mr.', '2018-06-29 00:00:00'),
(11, 'Virginia', 'Marcoe', 'F', 'Mrs.', '2018-06-29 00:00:00');

INSERT IGNORE INTO `common_fields` (`user_id`, `address1`, `address2`, `city`, `country`, `postal_code`, `region`) VALUES
(1, '', '', '', 'USA', '', ''),
(3, '721 Loreto Street, Juna Subdivision ', 'Matina, Talomo', 'Davao City', 'PHL', '8000 ', 'Davao del Sur'),
(4, '32nd St., 5th Ave. Bonifacio ', '', 'Global City', 'PHL', '8000', 'Davao Del Sur'),
(5, 'MacArthur Highway', 'Corner Maa, Davao City ', 'Davo City', 'PHL', '8000', 'Davao Del Sur'),
(6, 'J.P. Laurel Avenue', '', 'Davao City', 'PHL', '8000', 'Davao Del Sur'),
(7, '3435 Elm St.', '', 'Cosgrove', 'USAsa', '11255', 'MD'),
(8, 'Block 1, Lot 20, Portville', 'Brgy. Sasa', 'Davao City', 'PHL', '8000', 'Davao Del Sur'),
(9, '56892 Roscoe St.', '', 'Blooming', 'USA', '55422', 'MN'),
(10, '25252 First St.', '', 'Los Angeles', 'USA', '90052', 'CA'),
(11, '11351 Herbert St.', '', 'Los Angeles', 'USA', '90066', 'CA'),
(12, '7900 Legacy Drive', '', 'Plano', 'USA', '75024', 'TX'),
(13, '6600 North Military Trail', '', 'Boca Raton', 'USA', '33496', 'FL'),
(14, 'Quimpo Blvd, Corner Eco West Drive', 'Brgy. Ecoland', 'Davao City', 'PHL', '8000', 'Davao Del Sorte');


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
