package com.csumb.cst363;

import java.sql.Date;

/*
 * This class is used to transfer data to/from patient templates
 *  for registering new doctor and updating doctor profile.
 */
public class Patient {

    private int SSN;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int age;
    private String address;
    private String street;
	private String city;
	private String state;
	private String zipcode;
	
	
	private int primaryID;       
	private String primaryName;  
	private String specialty;    
	private String years;   
    private String primaryPhysician;
    private int doctorSSN;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrimaryPhysician() {
        return primaryPhysician;
    }

    public void setPrimaryPhysician(String primaryPhysician) {
        this.primaryPhysician = primaryPhysician;
    }

    public int getDoctorSSN() {
        return doctorSSN;
    }

    public void setDoctorSSN(int doctorSSN) {
        this.doctorSSN = doctorSSN;
    }

    @Override
    public String toString() {
        return "patient{" +
                "SSN=" + SSN +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", primaryPhysician='" + primaryPhysician + '\'' +
                ", doctorSSN=" + doctorSSN +
                '}';
    }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getPrimaryID() {
		return primaryID;
	}

	public void setPrimaryID(int primaryID) {
		this.primaryID = primaryID;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

}
