package com.csumb.cst363;
/*
 * This file is currently not in use
 * We thought this project was going to require all tables we originally created
 */
public class Prescription_Drug_has_Retail_Pharmacy {
    private int drugID;
    private int retailPharmacyID;
    private double drugCost;

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public int getRetailPharmacyID() {
        return retailPharmacyID;
    }

    public void setRetailPharmacyID(int retailPharmacyID) {
        this.retailPharmacyID = retailPharmacyID;
    }

    public double getDrugCost() {
        return drugCost;
    }

    public void setDrugCost(double drugCost) {
        this.drugCost = drugCost;
    }

    @Override
    public String toString() {
        return "Prescription_Drug_has_Retail_Pharmacy{" +
                "drugID=" + drugID +
                ", retailPharmacyID=" + retailPharmacyID +
                ", drugCost=" + drugCost +
                '}';
    }
}
