package com.csumb.cst363;

import java.util.Date;

public class Doctor {
    private int SSN;
    private String firstName;
    private String lastName;
    private Date startDate;
    private int yearsOfExperience;
    private int specialtyID;

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getSpecialtyID() {
        return specialtyID;
    }

    public void setSpecialtyID(int specialtyID) {
        this.specialtyID = specialtyID;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "SSN=" + SSN +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startDate=" + startDate +
                ", yearsOfExperience=" + yearsOfExperience +
                ", specialtyID=" + specialtyID +
                '}';
    }

	public String getPractice_since_year() {
		// TODO Auto-generated method stub
		return null;
	}
}
