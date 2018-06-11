-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2018 at 10:51 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `billing_history`
--

CREATE TABLE `billing_history` (
  `id` int(11) NOT NULL,
  `amount_due` double NOT NULL,
  `customer` varchar(255) DEFAULT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `invoice_num` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `chart_of_accounts`
--

CREATE TABLE `chart_of_accounts` (
  `account_num` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `account_balance` double NOT NULL,
  `account_name` varchar(255) NOT NULL,
  `account_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `chart_of_accounts`
--

INSERT INTO `chart_of_accounts` (`account_num`, `description`, `account_balance`, `account_name`, `account_type`) VALUES
('1000', 'This is a cash account', 0, 'Cash', 'Asset'),
('1001', 'This is Accounts Receivable', 0, 'Accounts Receivable', 'Asset'),
('1002', 'This is Accounts Payable', 0, 'Accounts Payable', 'Liability'),
('1003', 'This is Inventory Expense', 0, 'Inventory Expense', 'Expense'),
('1004', 'Owner\'s Equity Account', 0, 'Equity Accounts', 'Equity'),
('1005', 'Capital Equipment Account', 0, 'Capital', 'Asset'),
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

-- --------------------------------------------------------

--
-- Table structure for table `common_fields`
--

CREATE TABLE `common_fields` (
  `user_id` int(11) NOT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `common_fields`
--

INSERT INTO `common_fields` (`user_id`, `address1`, `address2`, `city`, `country`, `postal_code`, `region`) VALUES
(31, '', '', '', 'USA', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `user_id` int(11) NOT NULL,
  `since` datetime DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `male_female` varchar(255) DEFAULT NULL,
  `salutation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `user_id` int(11) NOT NULL,
  `cell_phone` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `division` varchar(255) DEFAULT NULL,
  `dnr` bit(1) NOT NULL,
  `emer_contact` varchar(255) DEFAULT NULL,
  `emer_ph` varchar(255) DEFAULT NULL,
  `emp_type` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `home_phone` varchar(255) DEFAULT NULL,
  `office_loc` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `supervisor` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `male_female` varchar(255) DEFAULT NULL,
  `salutation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`user_id`, `cell_phone`, `company`, `division`, `dnr`, `emer_contact`, `emer_ph`, `emp_type`, `end_date`, `extension`, `home_phone`, `office_loc`, `position`, `start_date`, `supervisor`, `firstname`, `lastname`, `male_female`, `salutation`) VALUES
(31, '', '', '', b'0', '', '', 'F', '2100-12-31 00:00:00', '', '', '', '', '2018-06-09 00:00:00', '', 'New', 'Employee', 'M', 'Mr.');

-- --------------------------------------------------------

--
-- Table structure for table `emp_financial`
--

CREATE TABLE `emp_financial` (
  `user_id` int(11) NOT NULL,
  `account_num` varchar(255) DEFAULT NULL,
  `f_tax_prcnt` double NOT NULL,
  `f_un_prcnt` double NOT NULL,
  `fd_exempt` int(11) NOT NULL,
  `garnishment` double NOT NULL,
  `hourly_rate` double NOT NULL,
  `med_prcnt` double NOT NULL,
  `other` double NOT NULL,
  `other_expl` varchar(255) DEFAULT NULL,
  `pay_method` varchar(255) DEFAULT NULL,
  `retire_prcnt` double NOT NULL,
  `routing_num` varchar(255) DEFAULT NULL,
  `s_tax_prcnt` double NOT NULL,
  `s_un_prcnt` double NOT NULL,
  `ssi_prcnt` double NOT NULL,
  `st_exempt` int(11) NOT NULL,
  `tax_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emp_financial`
--

INSERT INTO `emp_financial` (`user_id`, `account_num`, `f_tax_prcnt`, `f_un_prcnt`, `fd_exempt`, `garnishment`, `hourly_rate`, `med_prcnt`, `other`, `other_expl`, `pay_method`, `retire_prcnt`, `routing_num`, `s_tax_prcnt`, `s_un_prcnt`, `ssi_prcnt`, `st_exempt`, `tax_id`) VALUES
(31, '', 0, 0, 0, 0, 0, 0, 0, '', 'Check', 0, '', 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `fetal_scripts`
--

CREATE TABLE `fetal_scripts` (
  `id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `general_ledger`
--

CREATE TABLE `general_ledger` (
  `entry_num` bigint(20) NOT NULL,
  `account_num` varchar(255) DEFAULT NULL,
  `credit_amt` double NOT NULL,
  `debit_amt` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `entry_date` datetime DEFAULT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `ordered` datetime DEFAULT NULL,
  `processed` datetime DEFAULT NULL,
  `shipped` datetime DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `sold` datetime DEFAULT NULL,
  `sold_for` double NOT NULL,
  `wholesale` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payables`
--

CREATE TABLE `payables` (
  `invoice_num` varchar(255) NOT NULL,
  `date_begin` datetime DEFAULT NULL,
  `date_due` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `supplier` varchar(255) DEFAULT NULL,
  `total_balance` double NOT NULL,
  `total_due` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payments_paid`
--

CREATE TABLE `payments_paid` (
  `id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `date_due` datetime DEFAULT NULL,
  `invoice_num` varchar(255) DEFAULT NULL,
  `payment` double NOT NULL,
  `payment_date` datetime DEFAULT NULL,
  `payment_due` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payments_received`
--

CREATE TABLE `payments_received` (
  `id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `date_due` datetime DEFAULT NULL,
  `invoice_num` varchar(255) DEFAULT NULL,
  `payment` double NOT NULL,
  `payment_date` datetime DEFAULT NULL,
  `payment_due` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `petty_cash`
--

CREATE TABLE `petty_cash` (
  `pc_id` int(11) NOT NULL,
  `last_transaction` datetime DEFAULT NULL,
  `max_amount` double NOT NULL,
  `min_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `petty_cash`
--

INSERT INTO `petty_cash` (`pc_id`, `last_transaction`, `max_amount`, `min_amount`) VALUES
(1, '2018-05-30 00:00:00', 400, 50);

-- --------------------------------------------------------

--
-- Table structure for table `petty_cash_voucher`
--

CREATE TABLE `petty_cash_voucher` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `from_account` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `time_stamp` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `sku` varchar(255) NOT NULL,
  `amount_in_stock` double NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `discontinue` bit(1) NOT NULL,
  `min_amount` double NOT NULL,
  `on_sale` bit(1) NOT NULL,
  `price` double NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `subcategory` varchar(255) DEFAULT NULL,
  `upc` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `receivables`
--

CREATE TABLE `receivables` (
  `invoice_num` varchar(255) NOT NULL,
  `customer` varchar(255) DEFAULT NULL,
  `date_due` datetime DEFAULT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `total_balance` double NOT NULL,
  `total_due` double NOT NULL,
  `total_payments` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'SUPERVISOR'),
(4, 'ACCOUNTING'),
(5, 'ROOT');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `temp_pw` bit(1) DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `enabled`, `password`, `temp_pw`, `username`) VALUES
(31, b'1', '$2a$10$QFxwISh5JvXg8ztNMkkZ7u1VdWhEIDd/0tYa6fNRNst1KKrf0PzDC', b'0', 'New@new.com');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(31, 1),
(31, 2),
(31, 3),
(31, 4),
(31, 5);

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `user_id` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `salutation` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing_history`
--
ALTER TABLE `billing_history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chart_of_accounts`
--
ALTER TABLE `chart_of_accounts`
  ADD PRIMARY KEY (`account_num`);

--
-- Indexes for table `common_fields`
--
ALTER TABLE `common_fields`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `emp_financial`
--
ALTER TABLE `emp_financial`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `fetal_scripts`
--
ALTER TABLE `fetal_scripts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `general_ledger`
--
ALTER TABLE `general_ledger`
  ADD PRIMARY KEY (`entry_num`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpfondogjs5srkwxjnsu146mit` (`sku`);

--
-- Indexes for table `payables`
--
ALTER TABLE `payables`
  ADD PRIMARY KEY (`invoice_num`);

--
-- Indexes for table `payments_paid`
--
ALTER TABLE `payments_paid`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqh11yf2jil971gb8br273ybo` (`invoice_num`);

--
-- Indexes for table `payments_received`
--
ALTER TABLE `payments_received`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqr0b8ehwsx725qmyp0d6d999q` (`invoice_num`);

--
-- Indexes for table `petty_cash`
--
ALTER TABLE `petty_cash`
  ADD PRIMARY KEY (`pc_id`);

--
-- Indexes for table `petty_cash_voucher`
--
ALTER TABLE `petty_cash_voucher`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`sku`);

--
-- Indexes for table `receivables`
--
ALTER TABLE `receivables`
  ADD PRIMARY KEY (`invoice_num`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email_user_uc` (`username`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billing_history`
--
ALTER TABLE `billing_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `fetal_scripts`
--
ALTER TABLE `fetal_scripts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `general_ledger`
--
ALTER TABLE `general_ledger`
  MODIFY `entry_num` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `payments_paid`
--
ALTER TABLE `payments_paid`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `payments_received`
--
ALTER TABLE `payments_received`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `petty_cash`
--
ALTER TABLE `petty_cash`
  MODIFY `pc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `petty_cash_voucher`
--
ALTER TABLE `petty_cash_voucher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `FKpfondogjs5srkwxjnsu146mit` FOREIGN KEY (`sku`) REFERENCES `product` (`sku`);

--
-- Constraints for table `payments_paid`
--
ALTER TABLE `payments_paid`
  ADD CONSTRAINT `FKqh11yf2jil971gb8br273ybo` FOREIGN KEY (`invoice_num`) REFERENCES `payables` (`invoice_num`);

--
-- Constraints for table `payments_received`
--
ALTER TABLE `payments_received`
  ADD CONSTRAINT `FKqr0b8ehwsx725qmyp0d6d999q` FOREIGN KEY (`invoice_num`) REFERENCES `receivables` (`invoice_num`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
