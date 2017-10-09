-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Applikaasie
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Applikaasie
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Applikaasie` DEFAULT CHARACTER SET utf8 ;
USE `Applikaasie` ;

-- -----------------------------------------------------
-- Table `Applikaasie`.`Cheese`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`Cheese` (
  `CheeseID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `Price` DECIMAL(6,2) NULL,
  `Stock` VARCHAR(45) NULL,
  PRIMARY KEY (`CheeseID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`Client` (
  `ClientID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NULL,
  `LastName` VARCHAR(45) NULL,
  `E-mail` VARCHAR(45) NULL,
  PRIMARY KEY (`ClientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`Account` (
  `AccountID` INT NOT NULL AUTO_INCREMENT,
  `AccountName` VARCHAR(45) NULL,
  `AccountPassword` VARCHAR(45) NULL,
  `AccountStatus` INT NULL,
  PRIMARY KEY (`AccountID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`Order` (
  `OrderID` INT NOT NULL AUTO_INCREMENT,
  `OrderDate` DATETIME NULL,
  `TotalPrice` DECIMAL(6,2) NULL,
  `ProcessedDate` DATETIME NULL,
  `Client_ClientID` INT NOT NULL,
  PRIMARY KEY (`OrderID`),
  INDEX `fk_Order_Client1_idx` (`Client_ClientID` ASC),
  CONSTRAINT `fk_Order_Client1`
    FOREIGN KEY (`Client_ClientID`)
    REFERENCES `Applikaasie`.`Client` (`ClientID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`AddressType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`AddressType` (
  `AddressTypeID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(45) NULL,
  PRIMARY KEY (`AddressTypeID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`Address` (
  `AddressID` INT NOT NULL AUTO_INCREMENT,
  `StreetName` VARCHAR(45) NULL,
  `HouseNumber` INT NULL,
  `HouseNumberAddition` VARCHAR(5) NULL,
  `PostalCode` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `Client_ClientID` INT NOT NULL,
  `AddressType_AddressTypeID` INT NOT NULL,
  PRIMARY KEY (`AddressID`),
  INDEX `fk_Address_Client1_idx` (`Client_ClientID` ASC),
  INDEX `fk_Address_AddressType1_idx` (`AddressType_AddressTypeID` ASC),
  CONSTRAINT `fk_Address_Client1`
    FOREIGN KEY (`Client_ClientID`)
    REFERENCES `Applikaasie`.`Client` (`ClientID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Address_AddressType1`
    FOREIGN KEY (`AddressType_AddressTypeID`)
    REFERENCES `Applikaasie`.`AddressType` (`AddressTypeID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Applikaasie`.`OrderDetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Applikaasie`.`OrderDetail` (
  `OrderDetailID` INT NOT NULL AUTO_INCREMENT,
  `Quantity` INT NULL,
  `Cheese_CheeseID` INT NOT NULL,
  `Order_OrderID` INT NOT NULL,
  PRIMARY KEY (`OrderDetailID`),
  INDEX `fk_OrderDetail_Cheese_idx` (`Cheese_CheeseID` ASC),
  INDEX `fk_OrderDetail_Order1_idx` (`Order_OrderID` ASC),
  CONSTRAINT `fk_OrderDetail_Cheese`
    FOREIGN KEY (`Cheese_CheeseID`)
    REFERENCES `Applikaasie`.`Cheese` (`CheeseID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderDetail_Order1`
    FOREIGN KEY (`Order_OrderID`)
    REFERENCES `Applikaasie`.`Order` (`OrderID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE USER 'user' IDENTIFIED BY 'Password';

GRANT ALL ON `Applikaasie`.* TO 'user';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
