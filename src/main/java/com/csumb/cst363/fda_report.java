package com.csumb.cst363;

import java.util.Scanner;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;


//An FDA government official is looking for the quantity of drugs that each doctor has prescribed.  
//The report shows the doctor’s name and quantity prescribed. 
//Input is drug name (may be partial name) and a start and end date range.
public class fda_report {
	
	static final String DBURL = "jdbc:mysql://localhost:3306/cst363";  // database URL
	static final String USERID = "root";
	static final String PASSWORD = "bingle145";
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		 try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
			
			PreparedStatement ps = conn.prepareStatement(
					"SELECT firstName, lastName, SUM(po.quantity) AS prescribedAmount FROM doctor "
					+ "JOIN prescription_order po ON po.Doctor_SSN = doctor.SSN "
					+ "JOIN prescription_drug pd ON pd.ID = po.drugID "
					+ "WHERE pd.genericName LIKE ? OR pd.tradeName LIKE ? "
					+ "		AND po.prescribeDate BETWEEN ? AND ? "
					+ "GROUP BY doctor.firstName, doctor.lastName; ");
			
			// User input generic drug name
			System.out.println("Enter Generic Drug Name (Hit Enter To Skip): ");
			String GenericDrug = input.nextLine();
			if(!GenericDrug.isBlank()) {
				GenericDrug = '%' + GenericDrug + '%';
			}
			ps.setString(1, GenericDrug);
			
			// User input trade drug name
			System.out.println("Enter Trade Drug Name (Hit Enter To Skip): ");
			String TradeDrug = input.nextLine();
			if(!TradeDrug.isBlank()) {
				TradeDrug = '%' + TradeDrug + '%';
			}		
			ps.setString(2, TradeDrug);
			
			//Exit code if no drug is entered
			if(GenericDrug.isBlank() && TradeDrug.isBlank()) {
				System.out.println("No Drug Entered. Exiting.");
				System.exit(0);
			}
			
			// User input date start
			System.out.println("Enter Start Date as yyyy/mm/dd: ");
			Date beginningDate = null;
			while (beginningDate == null) {
			    String userInput = input.nextLine();
			    try {
			    	beginningDate = (Date) format.parse(userInput);
			    } catch (ParseException e) {
					System.out.println("Please enter a valid start");
					System.out.println("Enter Start Date yyyy/mm/dd: ");
			    }
			}
			java.sql.Date bDate = new java.sql.Date(beginningDate.getTime());
			ps.setDate(3,bDate);
			
			// User input date end
			System.out.println("Enter End Date as yyyy/mm/dd: ");
			Date endDate = null;
			while (endDate == null) {
			    String userInput = input.nextLine();
			    try {
			    	endDate = (Date) format.parse(userInput);
			    } catch (ParseException e) {
					System.out.println("Please enter a valid End");
					System.out.println("Enter End Date yyyy/mm/dd: ");
			    }
			}
			java.sql.Date eDate = new java.sql.Date(endDate.getTime());
			ps.setDate(4,eDate);
			
			//Return results
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String firstName = rs.getString(1);
				String lastName = rs.getString(2);
				int prescribedAmount = rs.getInt(3);
				System.out.println("Name: " + firstName + " " + lastName);
				System.out.println("Amount Prescribed: " + prescribedAmount);
				System.out.println("");	
				}
			
			} catch (SQLException e) {
			System.out.println("Error: SQLException "+e.getMessage());
		} input.close();		
	}
	

}
