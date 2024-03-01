package com.csumb.cst363;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;




public class Prescription_report {
	
	static final String DBURL = "jdbc:mysql://localhost:3306/cst363";  // database URL
	static final String USERID = "root";
	static final String PASSWORD = "bingle145";
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Please input PharmacyID (Enter To Continue).");
		String inputPharmacy = input.nextLine();
		System.out.println("Enter Start Date as yyyy-MM-dd: ");
		String startDate = input.nextLine();
		System.out.println("Enter End Date as yyyy-MM-dd: ");
		String endDate = input.nextLine();
		
		int pharmacyID = Integer.parseInt(inputPharmacy);
		input.close();
		create_report(pharmacyID, startDate, endDate);
	}
		
		public static void create_report(int pharmacyNum, String startDate, String endDate) {
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				PreparedStatement ps;
				ResultSet rs;
			
		
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
				java.util.Date start_date;
				java.util.Date end_date;
				try {
					start_date = formatter.parse(startDate);
					end_date = formatter.parse(endDate);
				
					Date start = new Date(start_date.getTime());
					Date end = new Date(end_date.getTime());
				
					String sqlSELECT = "SELECT pd.genericname, pd.tradename, SUM(po.quantity) AS TotalFilled "
						+ "FROM prescription_order AS po "
						+ "JOIN Prescription_Drug AS pd ON po.drugid = pd.id "
						+ "WHERE pharmacyID = ? AND fillDate IS NOT NULL AND fillDate BETWEEN ? AND ? "
						+ "GROUP BY pd.id" ;
		
					
					ps = conn.prepareStatement(sqlSELECT);
					ps.setInt(1, pharmacyNum);
					ps.setDate(2, start);
					ps.setDate(3,  end);
				
					rs = ps.executeQuery();
					
					String sqlSelect2 = "SELECT name FROM retail_pharmacy WHERE ID = ?";
					PreparedStatement ps2 = conn.prepareStatement(sqlSelect2);
					ps2.setInt(1, pharmacyNum);
					ResultSet rs2 = ps2.executeQuery();
					
					if (rs2.next()) {
						System.out.println(rs2.getString("name"));
						System.out.println("Quantity of Prescription Drugs filled between "
								+ startDate + " and " + endDate + "\n");
						
						System.out.printf("%-20S   %-20S  %-20S %n", "Generic Name", "Trade Name", "Total Quantity Filled");
						
						while (rs.next()) {
							String genericname = rs.getString("genericname");
							String tradename = rs.getString("tradename");
							int total_filled = rs.getInt("TotalFilled");
							System.out.printf("%-20s   %-20s  %-20s \n", genericname, tradename, total_filled);
						}
					
					}else {
						System.out.println("Pharmacy ID " + String.valueOf(pharmacyNum) 
						+ " does not exist in database.");
					}
			
			
				} catch (ParseException e) {
					System.out.println("Error: ParseException "+ e.getMessage());
				}
			
				
			}catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
	}
}
