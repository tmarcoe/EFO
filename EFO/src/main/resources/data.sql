-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 13, 2018 at 06:09 AM
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
-- Dumping data for table `chart_of_accounts`
--

INSERT IGNORE INTO `chart_of_accounts` (`account_num`, `description`, `account_balance`, `account_name`, `account_type`) VALUES
('1000', 'This is a cash account', 0, 'Cash', 'Asset'),
('1001', 'This is Accounts Receivable', 0, 'Accounts Receivable', 'Asset'),
('1002', 'This is Accounts Payable', 0, 'Accounts Payable', 'Liability'),
('1003', 'This is Inventory Expense', 0, 'Inventory Expense', 'Expense'),
('1004', 'Owner\'s Equity Account', 0, 'Equity Accounts', 'Equity'),
('1005', 'Capital Equipment Account', 0, 'Capital', 'Asset'),
('1006', 'Interest Expense', 0, 'Interest Expense', 'Expense'),
('1009', 'Income through Sales', 0, 'Sales', 'Revenue'),
('1010', 'Tax Expense', 0, 'Tax Account', 'Liability'),
('1015', 'Investment Account', 0, 'Investments', 'Asset'),
('2001', 'Labor Expense', 0, 'Labor Expense', 'Liability'),
('3000', 'Petty Cash Account', 0, 'Petty Cash', 'Asset'),
('3001', 'This account is to keep track of Petty Cash expenditures ', 0, 'Petty Cash (Office Supplies)', 'Expense'),
('3002', 'This account is to keep track of Petty Cash expenditures.', 0, 'Petty Cash (Delivery Expense)', 'Expense'),
('3003', 'This is to keep track of Petty Cash expenditures.', 0, 'Petty Cash (Postage Expense)', 'Expense'),
('3004', 'This is to keep track of Petty Cash expenditures.', 0, 'Petty Cash (General Office Expense)', 'Expense'),
('3005', 'This is to keep track of Petty Cash Expenditures.', 0, 'Petty Cash (Short and Over)', 'Expense');

--
-- Dumping data for table `common_fields`
--

INSERT IGNORE INTO `common_fields` (`user_id`, `address1`, `address2`, `city`, `country`, `postal_code`, `region`) VALUES
(1, '', '', '', 'USA', '', '');

--
-- Dumping data for table `employee`
--

INSERT IGNORE INTO `employee` (`user_id`, `cell_phone`, `company`, `division`, `dnr`, `emer_contact`, `emer_ph`, `emp_type`, `end_date`, `extension`, `home_phone`, `office_loc`, `position`, `start_date`, `supervisor`, `firstname`, `lastname`, `male_female`, `salutation`) VALUES
(1, '', '', '', b'0', '', '', 'F', '2100-12-31 00:00:00', '', '', '', '', '2018-06-09 00:00:00', '', 'New', 'Employee', 'M', 'Mr.');

--
-- Dumping data for table `emp_financial`
--

INSERT IGNORE INTO `emp_financial` (`user_id`, `account_num`, `f_tax_prcnt`, `f_un_prcnt`, `fd_exempt`, `garnishment`, `hourly_rate`, `med_prcnt`, `other`, `other_expl`, `pay_method`, `retire_prcnt`, `routing_num`, `s_tax_prcnt`, `s_un_prcnt`, `ssi_prcnt`, `st_exempt`, `tax_id`) VALUES
(1, '', 0, 0, 0, 0, 0, 0, 0, '', 'Check', 0, '', 0, 0, 0, 0, '');

--
-- Dumping data for table `petty_cash`
--

INSERT IGNORE INTO `petty_cash` (`pc_id`, `last_transaction`, `max_amount`, `min_amount`) VALUES
(1, '2018-05-30 00:00:00', 400, 50);

--
-- Dumping data for table `role`
--

INSERT IGNORE INTO `role` (`role_id`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'SUPERVISOR'),
(4, 'ACCOUNTING'),
(5, 'ROOT');

--
-- Dumping data for table `user`
--

INSERT IGNORE INTO `user` (`user_id`, `enabled`, `password`, `temp_pw`, `username`) VALUES
(1, b'1', '$2a$10$QFxwISh5JvXg8ztNMkkZ7u1VdWhEIDd/0tYa6fNRNst1KKrf0PzDC', b'0', 'New@new.com');

--
-- Dumping data for table `user_role`
--

INSERT IGNORE INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
