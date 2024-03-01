-- MySQL Script generated by MySQL Workbench
-- Mon Jan 30 13:34:07 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET GLOBAL FOREIGN_KEY_CHECKS=0;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cst363
-- -----------------------------------------------------
select * from doctor;
select * from prescription_drug;
select * from prescription_order;
-- -----------------------------------------------------
-- Schema cst363
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cst363` DEFAULT CHARACTER SET utf8 ;
USE `cst363` ;

-- -----------------------------------------------------
-- Table `Specialty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Specialty` (
  `ID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doctor` (
  `SSN` INT NOT NULL,
  `firstName` VARCHAR(30) NOT NULL,
  `middleName` VARCHAR(30) NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `startDate` DATETIME NOT NULL,
  `yearsOfExperience` INT NULL,
  `specialtyID` INT NOT NULL,
  PRIMARY KEY (`SSN`, `specialtyID`),
  UNIQUE INDEX `SSN_UNIQUE` (`SSN` ASC) VISIBLE,
  INDEX `fk_Doctor_Specialty1_idx` (`specialtyID` ASC) VISIBLE,
  CONSTRAINT `fk_Doctor_Specialty1`
    FOREIGN KEY (`specialtyID`)
    REFERENCES `Specialty` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Patient` (
  `SSN` INT NOT NULL,
  `firstName` VARCHAR(30) NOT NULL,
  `middleName` VARCHAR(30) NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `dateOfBirth` DATETIME NOT NULL,
  `age` INT NULL,
  `address` VARCHAR(256) NOT NULL,
  `primaryPhysician` VARCHAR(45) NULL,
  `doctorSSN` INT NOT NULL,
  PRIMARY KEY (`SSN`, `doctorSSN`),
  INDEX `fk_Patient_Doctor1_idx` (`doctorSSN` ASC) VISIBLE,
  UNIQUE INDEX `SSN_UNIQUE` (`SSN` ASC) VISIBLE,
  CONSTRAINT `fk_Patient_Doctor1`
    FOREIGN KEY (`doctorSSN`)
    REFERENCES `Doctor` (`SSN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Pharmaceutical_Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Pharmaceutical_Company` (
  `ID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Retail_Pharmacy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Retail_Pharmacy` (
  `ID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phoneNumber` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `pharmacyID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescription_Drug`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescription_Drug` (
  `ID` INT NOT NULL,
  `genericName` VARCHAR(45) NOT NULL,
  `tradeName` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `tradeName_UNIQUE` (`tradeName` ASC) VISIBLE,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doctor_has_Prescription_Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doctor_has_Prescription_Order` (
  `doctorSSN` INT NOT NULL,
  `RXNumber` INT NOT NULL,
  PRIMARY KEY (`doctorSSN`, `RXNumber`),
  INDEX `fk_Doctor_has_Prescription_Order_Doctor1_idx` (`doctorSSN` ASC) VISIBLE,
  CONSTRAINT `fk_Doctor_has_Prescription_Order_Doctor1`
    FOREIGN KEY (`doctorSSN`)
    REFERENCES `Doctor` (`SSN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Patient_has_Prescription_Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Patient_has_Prescription_Order` (
  `patientSSN` INT NOT NULL,
  `RXNumber` INT NOT NULL,
  PRIMARY KEY (`patientSSN`, `RXNumber`),
  INDEX `fk_Patient_has_Prescription_Order_Patient1_idx` (`patientSSN` ASC) VISIBLE,
  CONSTRAINT `fk_Patient_has_Prescription_Order_Patient1`
    FOREIGN KEY (`patientSSN`)
    REFERENCES `Patient` (`SSN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescription_Order_has_Prescription_Drug`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescription_Order_has_Prescription_Drug` (
  `RXNumber` INT NOT NULL,
  `ID` INT NOT NULL,
  PRIMARY KEY (`RXNumber`, `ID`),
  INDEX `fk_Prescription_Order_has_Prescription_Drug_Prescription_Dr_idx` (`ID` ASC) VISIBLE,
  CONSTRAINT `fk_Prescription_Order_has_Prescription_Drug_Prescription_Drug1`
    FOREIGN KEY (`ID`)
    REFERENCES `Prescription_Drug` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Supervisor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Supervisor` (
  `ID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Pharmaceutical_Company_has_Retail_Pharmacy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Pharmaceutical_Company_has_Retail_Pharmacy` (
  `retailPharmacyID` INT NOT NULL,
  `pharmaceuticalCompanyID` INT NOT NULL,
  `supervisorID` INT NOT NULL,
  `contractStartDate` DATETIME NOT NULL,
  `contractEndDate` DATETIME NOT NULL,
  `contractText` LONGTEXT NULL,
  PRIMARY KEY (`retailPharmacyID`, `pharmaceuticalCompanyID`, `supervisorID`),
  INDEX `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Retail_Pharm_idx` (`retailPharmacyID` ASC) VISIBLE,
  INDEX `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Supervisor1_idx` (`supervisorID` ASC) VISIBLE,
  INDEX `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Pharmaceutic_idx` (`pharmaceuticalCompanyID` ASC) VISIBLE,
  CONSTRAINT `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Retail_Pharmacy1`
    FOREIGN KEY (`retailPharmacyID`)
    REFERENCES `Retail_Pharmacy` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Supervisor1`
    FOREIGN KEY (`supervisorID`)
    REFERENCES `Supervisor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pharmaceutical_Company_has_Retail_Pharmacy1_Pharmaceutical1`
    FOREIGN KEY (`pharmaceuticalCompanyID`)
    REFERENCES `Pharmaceutical_Company` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescription_Drug_has_Pharmaceutical_Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescription_Drug_has_Pharmaceutical_Company` (
  `drugID` INT NOT NULL,
  `pharmaceuticalCompanyID` INT NOT NULL,
  PRIMARY KEY (`drugID`, `pharmaceuticalCompanyID`),
  INDEX `fk_Prescription_Drug_has_Pharmaceutical_Company_Pharmaceuti_idx` (`pharmaceuticalCompanyID` ASC) VISIBLE,
  INDEX `fk_Prescription_Drug_has_Pharmaceutical_Company_Prescriptio_idx` (`drugID` ASC) VISIBLE,
  CONSTRAINT `fk_Prescription_Drug_has_Pharmaceutical_Company_Prescription_1`
    FOREIGN KEY (`drugID`)
    REFERENCES `Prescription_Drug` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Drug_has_Pharmaceutical_Company_Pharmaceutica1`
    FOREIGN KEY (`pharmaceuticalCompanyID`)
    REFERENCES `Pharmaceutical_Company` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescription_Drug_has_Retail_Pharmacy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescription_Drug_has_Retail_Pharmacy` (
  `drugID` INT NOT NULL,
  `retailPharmacyID` INT NOT NULL,
  `drugCost` DECIMAL(7,2) NOT NULL,
  PRIMARY KEY (`drugID`, `retailPharmacyID`),
  INDEX `fk_Prescription_Drug_has_Retail_Pharmacy_Retail_Pharmacy1_idx` (`retailPharmacyID` ASC) VISIBLE,
  INDEX `fk_Prescription_Drug_has_Retail_Pharmacy_Prescription_Drug1_idx` (`drugID` ASC) VISIBLE,
  CONSTRAINT `fk_Prescription_Drug_has_Retail_Pharmacy_Prescription_Drug1`
    FOREIGN KEY (`drugID`)
    REFERENCES `Prescription_Drug` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Drug_has_Retail_Pharmacy_Retail_Pharmacy1`
    FOREIGN KEY (`retailPharmacyID`)
    REFERENCES `Retail_Pharmacy` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prescription_Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prescription_Order` (
  `RXNumber` INT NOT NULL,
  `prescribeDate` DATETIME NOT NULL,
  `quantity` INT NOT NULL,
  `Doctor_SSN` INT NOT NULL,
  `Patient_SSN` INT NOT NULL,
  `pharmacyID` INT NOT NULL,
  `genericOK` TINYINT(1) NOT NULL,
  `pharmaceuticalCompanyID` INT NOT NULL,
  `drugID` INT NOT NULL,
  `fillDate` DATETIME NULL,
  PRIMARY KEY (`RXNumber`, `Doctor_SSN`, `Patient_SSN`, `pharmacyID`, `drugID`),
  INDEX `fk_Prescription_Order_Doctor1_idx` (`Doctor_SSN` ASC) VISIBLE,
  INDEX `fk_Prescription_Order_Patient1_idx` (`Patient_SSN` ASC) VISIBLE,
  INDEX `fk_Prescription_Order_Retail_Pharmacy1_idx` (`pharmacyID` ASC) VISIBLE,
  INDEX `fk_Prescription_Order_Prescription_Drug1_idx` (`drugID` ASC) INVISIBLE,
  INDEX `fk_Prescription_Order_Pharmaceutical_Company1_idx` (`pharmaceuticalCompanyID` ASC) VISIBLE,
  CONSTRAINT `fk_Prescription_Order_Doctor1`
    FOREIGN KEY (`Doctor_SSN`)
    REFERENCES `Doctor` (`SSN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Order_Patient1`
    FOREIGN KEY (`Patient_SSN`)
    REFERENCES `Patient` (`SSN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Order_Retail_Pharmacy1`
    FOREIGN KEY (`pharmacyID`)
    REFERENCES `Retail_Pharmacy` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Order_Prescription_Drug1`
    FOREIGN KEY (`drugID`)
    REFERENCES `Prescription_Drug` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescription_Order_Pharmaceutical_Company1`
    FOREIGN KEY (`pharmaceuticalCompanyID`)
    REFERENCES `Pharmaceutical_Company` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;