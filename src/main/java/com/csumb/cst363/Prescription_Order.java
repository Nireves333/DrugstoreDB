package com.csumb.cst363;
import java.util.Date;

public class Prescription_Order {
    private int RXNumber;
    private java.sql.Date prescribeDate;
    private int quantity;
    private boolean genericOk;
    private int pharmaceuticalCompanyID;
    private int drugID;
    private int pharmacyID;
    private int Doctor_SSN;
    private int Patient_SSN;
    private Date fillDate;
    
    // added for functionality of html file requirements 
    private String doctorFirstName;
    private String doctorLastName;
    private String patientFirstName;
    private String patientLastName;
    private String drugName;
    private String pharmacyName;
    private String pharmacyAddress;

    public int getRXNumber() {
        return RXNumber;
    }

    public void setRXNumber(int RXNumber) {
        this.RXNumber = RXNumber;
    }

    public java.sql.Date getPrescribeDate() {
        return prescribeDate;
    }

    public void setPrescribeDate(java.sql.Date prescribeDate) {
        this.prescribeDate = prescribeDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isGenericOk() {
        return genericOk;
    }

    public void setGenericOk(boolean genericOk) {
        this.genericOk = genericOk;
    }

    public int getPharmaceuticalCompanyID() {
        return pharmaceuticalCompanyID;
    }

    public void setPharmaceuticalCompanyID(int pharmaceuticalCompanyID) {
        this.pharmaceuticalCompanyID = pharmaceuticalCompanyID;
    }

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public int getDoctor_SSN() {
        return Doctor_SSN;
    }

    public void setDoctor_SSN(int doctor_SSN) {
        Doctor_SSN = doctor_SSN;
    }

    public int getPatient_SSN() {
        return Patient_SSN;
    }

    public void setPatient_SSN(int patient_SSN) {
        Patient_SSN = patient_SSN;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    @Override
    public String toString() {
        return "Prescription_Order{" +
                "RXNumber=" + RXNumber +
                ", prescribeDate=" + prescribeDate +
                ", quantity=" + quantity +
                ", genericOk=" + genericOk +
                ", pharmaceuticalCompanyID=" + pharmaceuticalCompanyID +
                ", drugID=" + drugID +
                ", pharmacyID=" + pharmacyID +
                ", Doctor_SSN=" + Doctor_SSN +
                ", Patient_SSN=" + Patient_SSN +
                ", fillDate=" + fillDate +
                '}';
    }

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getPharmacyAddress() {
		return pharmacyAddress;
	}

	public void setPharmacyAddress(String pharmacyAddress) {
		this.pharmacyAddress = pharmacyAddress;
	}
}
