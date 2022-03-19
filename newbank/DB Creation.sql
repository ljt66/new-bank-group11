CREATE DATABASE `newbank` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `customers` (
  `CustomerID` int NOT NULL AUTO_INCREMENT,
  `CustomerName` varchar(255) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  UNIQUE KEY `CustomerID_UNIQUE` (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `accounts` (
  `AccountID` int NOT NULL AUTO_INCREMENT,
  `CustomerID` int NOT NULL,
  `AccountName` varchar(255) NOT NULL,
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`AccountID`),
  UNIQUE KEY `AccountID_UNIQUE` (`AccountID`),
  KEY `CustomerID_idx` (`CustomerID`),
  CONSTRAINT `CustomerID` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO customers(CustomerName) values('Bhagy');
INSERT INTO customers(CustomerName) values('Christina');
INSERT INTO customers(CustomerName) values('John');
INSERT INTO accounts(CustomerID, AccountName, Amount) values(1, 'Main', 1000.00);
INSERT INTO accounts(CustomerID, AccountName, Amount) values(2, 'Savings', 1500.00);
INSERT INTO accounts(CustomerID, AccountName, Amount) values(3, 'Checking', 250.00);

