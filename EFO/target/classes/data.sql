-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: efo
-- ------------------------------------------------------
-- Server version	5.7.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `chart_of_accounts`
--

LOCK TABLES `chart_of_accounts` WRITE;
/*!40000 ALTER TABLE `chart_of_accounts` DISABLE KEYS */;
INSERT INTO `chart_of_accounts` (`account_num`, `account_balance`, `account_name`, `account_type`, `description`) VALUES ('1000',0,'Cash','Asset','This is a cash account'),('1001',0,'Accounts Receivable','Asset','This is Accounts Receivable'),('1002',0,'Accounts Payable','Liability','This is Accounts Payable'),('1003',0,'Inventory','Asset','This is Inventory Account'),('1004',0,'Equity Accounts','Equity','Owner\'s Equity Account'),('1005',0,'Capital','Asset','Capital Equipment Account'),('1006',0,'Interest Expense','Expense','Interest Expense'),('1007',0,'Bad Debt','Liability','Bad Debt'),('1008',0,'Common Stock','Liability','Common Stock Account'),('1009',0,'Sales','Revenue','Income through Sales'),('1010',0,'Tax Account','Liability','Tax Expense'),('1011',0,'Loan Liability','Liability','Loan Liability'),('1012',0,'Overhead Expense','Expense','This account is for monthly overhead expense'),('1013',0,'Customer Deposits','Liability','This account is for any down payments'),('1015',0,'Investments','Asset','Investment Account'),('2001',0,'Labor Expense','Liability','Labor Expense'),('3000',400,'Petty Cash','Asset','Petty Cash Account'),('3001',0,'Petty Cash (Office Supplies)','Expense','This account is to keep track of Petty Cash expenditures '),('3002',0,'Petty Cash (Delivery Expense)','Expense','This account is to keep track of Petty Cash expenditures.'),('3003',0,'Petty Cash (Postage Expense)','Expense','This is to keep track of Petty Cash expenditures.'),('3004',0,'Petty Cash (General Office Expense)','Expense','This is to keep track of Petty Cash expenditures.'),('3005',0,'Petty Cash (Short and Over)','Expense','This is to keep track of Petty Cash Expenditures.'),('4000',0,'Budget Appropriations','Asset','This is the accumulative account for all budget allocations. ');
/*!40000 ALTER TABLE `chart_of_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `common_fields`
--

LOCK TABLES `common_fields` WRITE;
/*!40000 ALTER TABLE `common_fields` DISABLE KEYS */;
INSERT INTO `common_fields` (`user_id`, `address1`, `address2`, `city`, `country`, `postal_code`, `region`) VALUES (1,'','','','USA','','');
/*!40000 ALTER TABLE `common_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `emp_financial`
--

LOCK TABLES `emp_financial` WRITE;
/*!40000 ALTER TABLE `emp_financial` DISABLE KEYS */;
INSERT INTO `emp_financial` (`user_id`, `account_num`, `city`, `city_trans`, `ein`, `exemptions`, `fed`, `fed_trans`, `fed_unemployment`, `fica`, `garnishment`, `medical`, `pay_method`, `pay_rate`, `retirement`, `routing_num`, `ss_tax`, `ssn`, `st_trans`, `st_unemployment`, `state`, `status`, `union_dues`) VALUES (1,'',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'C',NULL,NULL,'',NULL,'',NULL,NULL,NULL,'S',NULL);
/*!40000 ALTER TABLE `emp_financial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`user_id`, `cell_phone`, `company`, `division`, `dnr`, `emer_contact`, `emer_ph`, `emp_type`, `end_date`, `extension`, `firstname`, `home_phone`, `lastname`, `male_female`, `office_loc`, `position`, `salutation`, `start_date`, `supervisor`) VALUES (1,'','','','\0','','','F','2100-12-31 00:00:00','','New','','Employee','M','','','Mr.','2018-06-09 00:00:00','');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
INSERT INTO `profiles` (`name`, `active`, `created`, `depreciation`, `descr`, `exclude`, `script`, `show_credit_terms`, `type`, `variables`) VALUES ('Add Equity',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','equity/addequity.trans',_binary '\0','I','events,dao,Events;equity,dao,Equity'),('Capital Expense (cash)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','capital_assets/capitalexpense-cash.trans',_binary '\0','CE','expenses,dao,Expenses;events,dao,Events'),('Capital Expense (credit)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','capital_assets/capitalexpense-credit.trans',_binary '','CE','expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments;events,dao,Events'),('Loan Payment',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','accounts_payable/payloan.trans',_binary '\0','P','newPayment,dao,LoanPayments;newEvent,dao,Events'),('New Budget',_binary '','2019-08-09 00:00:00',_binary '\0',NULL,_binary '\0','budget/newbudget.trans',_binary '\0','CE','events,dao,Events'),('New Loan',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','accounts_payable/newloan.trans',_binary '','L','loans,dao,Loans;loanTerms,dao,LoanTerms;loanPayments,dao,LoanPayments;events,dao,Events'),('New Overhead (One Time)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','overhead_expense/newotoverhead.trans',_binary '\0','O','events,dao,Events;expenses,dao,Expenses'),('New Overhead (Scheduled)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','overhead_expense/newoverhead.trans',_binary '\0','O','events,dao,Events;newEvent,dao,Events;expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments'),('Order Retail (Cash)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','order_inventory/orderretail-cash.trans',_binary '\0','RE','expenses,dao,Expenses;events,dao,Events'),('Order Retail (Credit)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','order_inventory/orderretail-credit.trans',_binary '','RE','expenses,dao,Expenses;expenseTerms,dao,ExpenseTerms;expensePayments,dao,ExpensePayments;events,dao,Events'),('Pay Credit Note',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','accounts_payable/paycreditnote.trans',_binary '\0','P','newPayment,dao,ExpensePayments;newEvent,dao,Events'),('Pay Overhead',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','overhead_expense/payoverhead.trans',_binary '\0','P','newEvent,dao,Events;newPayment,dao,ExpensePayments'),('Receive Payment',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','accounts_receiveable/receivepayment.trans',_binary '\0','P','newPayment,dao,RevenuePayments;newEvent,dao,Events'),('Retail Sales (Cash)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','retail_sales/retailsales-cash.trans',_binary '\0','RR','taxRate,decimal,%tax%;revenues,dao,Revenues;events,dao,Events'),('Retail Sales (Credit)',_binary '','2019-04-29 00:00:00',_binary '\0',NULL,_binary '\0','retail_sales/retailsales-credit.trans',_binary '','RR','taxRate,decimal,%tax%;revenues,dao,Revenues;revenueTerms,dao,RevenueTerms;revenuePayments,dao,RevenuePayments;events,dao,Events');
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role`) VALUES (1,'USER'),(2,'PERSONNEL'),(3,'BASIC'),(4,'ACCOUNTING'),(5,'EVENTS'),(6,'REPORTS'),(7,'ADMIN'),(8, 'BUDGET');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `enabled`, `password`, `temp_pw`, `username`) VALUES (1,'','$2a$10$QFxwISh5JvXg8ztNMkkZ7u1VdWhEIDd/0tYa6fNRNst1KKrf0PzDC','\0','New@new.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-25 15:16:15
