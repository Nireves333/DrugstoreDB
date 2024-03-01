package com.csumb.cst363;
import java.util.Date;
/*
 * This file is currently not in use
 * We thought this project was going to require all tables we originally created
 */
public class Pharmaceutical_Company_has_Retail_Pharmacy1 {
    private int retailPharmacyID;
    private int pharmaceuticalComanyID;
    private int supervisorID;
    private Date contractStartDate;
    private Date contractEndDate;
    private String contractText;

    public int getRetailPharmacyID() {
        return retailPharmacyID;
    }

    public void setRetailPharmacyID(int retailPharmacyID) {
        this.retailPharmacyID = retailPharmacyID;
    }

    public int getPharmaceuticalComanyID() {
        return pharmaceuticalComanyID;
    }

    public void setPharmaceuticalComanyID(int pharmaceuticalComanyID) {
        this.pharmaceuticalComanyID = pharmaceuticalComanyID;
    }

    public int getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractText() {
        return contractText;
    }

    public void setContractText(String contractText) {
        this.contractText = contractText;
    }

    @Override
    public String toString() {
        return "Pharmaceutical_Company_has_Retail_Pharmacy1{" +
                "retailPharmacyID=" + retailPharmacyID +
                ", pharmaceuticalComanyID=" + pharmaceuticalComanyID +
                ", supervisorID=" + supervisorID +
                ", contractStartDate=" + contractStartDate +
                ", contractEndDate=" + contractEndDate +
                ", contractText='" + contractText + '\'' +
                '}';
    }
}
