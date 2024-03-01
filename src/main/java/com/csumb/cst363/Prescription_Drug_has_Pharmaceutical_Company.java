package com.csumb.cst363;
/*
 * This file is currently not in use
 * We thought this project was going to require all tables we originally created
 */
public class Prescription_Drug_has_Pharmaceutical_Company {
    private int drugID;
    private int pharmaceuticalCompanyID;

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public int getPharmaceuticalCompanyID() {
        return pharmaceuticalCompanyID;
    }

    public void setPharmaceuticalCompanyID(int pharmaceuticalCompanyID) {
        this.pharmaceuticalCompanyID = pharmaceuticalCompanyID;
    }

    @Override
    public String toString() {
        return "Prescription_Drug_has_Pharmaceutical_Company{" +
                "drugID=" + drugID +
                ", pharmaceuticalCompanyID=" + pharmaceuticalCompanyID +
                '}';
    }
}
