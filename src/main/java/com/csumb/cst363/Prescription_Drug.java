package com.csumb.cst363;
public class Prescription_Drug {
    private int ID;
    private String genericName;
    private String tradeName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    @Override
    public String toString() {
        return "Prescription_Drug{" +
                "ID=" + ID +
                ", genericName='" + genericName + '\'' +
                ", tradeName='" + tradeName + '\'' +
                '}';
    }
}
