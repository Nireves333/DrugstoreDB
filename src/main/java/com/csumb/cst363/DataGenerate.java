package com.csumb.cst363;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DataGenerate {
	static final String DBURL = "jdbc:mysql://localhost:3306/cst363";  // database URL
	static final String USERID = "root";
	static final String PASSWORD = "bingle145";
	
	static final String[] specialties= {"Internal Medicine", "Family Medicine", "Pediatrics", "Orthpedics", "Dermatology", 
			"Cardiology", "Gynecology", "Gastroenterology", "Psychiatry", "Oncology"};
	static final int[] specialtyIDs= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	static ArrayList<String> usedSSN = new ArrayList<String>();
	static ArrayList<String> usedPhoneNumber = new ArrayList<String>();
	static ArrayList<String> usedRXNumber = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		
		//load all tables
		Specialty_main();
		Doctor_main();
		Patient_main();
		Prescription_Drug_main();
		Retail_Pharmacy_main();
		Pharmaceutical_Company_main();
		Prescription_Order_main();
		Supervisor_main();
	}

	
		public static void Supervisor_main() {
				try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
				
				// delete all specialty rows 
				ps = conn.prepareStatement("delete from supervisor");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// insert specialty data into table.  
				String sqlINSERT = "insert into supervisor(ID, name)"
						+ " values( ?, ?)";
				String[] keycols = {"ID"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 10 rows with data
				for (int k=0; k<=9; k++) {
					ps.setInt   (1, k + 1);
					String firstName = getName("first");
					String lastName = getName("last");
					ps.setString(2, firstName + " " + lastName);
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
				}
		
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
		}
	
		public static void Specialty_main() {
			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
				
				// delete all specialty rows 
				ps = conn.prepareStatement("delete from specialty");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// insert specialty data into table.  
				
				String sqlINSERT = "insert into specialty(ID, name)"
						+ " values( ?, ?)";
				String[] keycols = {"ID"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 10 rows with data
				for (int k=0; k<=9; k++) {
					ps.setInt   (1, k + 1);
					ps.setString(2, specialties[k] );
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
				}
		
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
		
		public static void Doctor_main() {

			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
				LocalDate ldt = LocalDate.now();
			
				// delete all doctor rows 
				ps = conn.prepareStatement("delete from doctor");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// generate doctor data and insert into table.  We want to generated column "id" value to be returned 
				// as a generated key
				
				String sqlINSERT = "insert into doctor(lastName, firstName, specialtyID, startDate, "
						+ "yearsOfExperience, SSN)"
						+ " values( ?, ?, ?, ?, ?, ?)";
				String[] keycols = {"id"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 10 rows with data
				for (int k=0; k<=9; k++) {
					Date currentYear = Date.valueOf(ldt);
					Date startDate = getDate();
					
					int yearsOfExperience = currentYear.getYear() - startDate.getYear();
				    
					String firstName = getName("first");
					String lastName = getName("last");
					
					ps.setString(1, lastName);
					ps.setString(2, "Dr. " + firstName);
					ps.setInt(3, specialtyIDs[k]);
					ps.setDate(4, startDate);
					ps.setInt(5,  yearsOfExperience);
					ps.setString(6, getSSN());
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
					
				}
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
	
		public static void Patient_main() {

			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				PreparedStatement ps2;
				ResultSet rs;
				int row_count;
				LocalDate ldt = LocalDate.now();
			
				// delete all patient rows 
				ps = conn.prepareStatement("delete from patient");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// generate patient data and insert into table. 
				
				String sqlINSERT = "insert into patient(SSN, firstName, lastName, dateOfBirth, age, address,"
						+ " doctorSSN, primaryPhysician)"
						+ " values( ?, ?, ?, ?, ?, ?, ?, ?)";
				String[] keycols = {"RXNumber"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 100 rows with data
						for (int k=0; k<=100; k++) {
							Date currentYear = Date.valueOf(ldt);
							Date dateOfBirth = getDate();
							
							int age = currentYear.getYear() - dateOfBirth.getYear();
							int doctorSSN = 0;
						    
							String firstName = getName("first");
							String lastName = getName("last");
							String primaryPhysician = "None";
							
							ps2 = conn.prepareStatement("select SSN, firstName from doctor ORDER BY RAND() LIMIT 1");
							rs = ps2.executeQuery();
							
							while(rs.next()) {
								doctorSSN = rs.getInt("SSN");
								primaryPhysician = rs.getString("firstName");
							}
							
							ps.setString(1, getSSN());
							ps.setString(2, firstName);
							ps.setString(3, lastName);
							ps.setDate(4, dateOfBirth);
							ps.setInt(5,  age);
							ps.setString(6, getAddress());
							ps.setInt(7,  doctorSSN);
							ps.setString(8, primaryPhysician);
							row_count = ps.executeUpdate();
							System.out.println("row inserted "+row_count);
							
							}
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
	
		public static void Prescription_Drug_main() {

			// connect to mysql server
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
				
			
				// delete all Prescription_Drug rows 
				ps = conn.prepareStatement("delete from Prescription_Drug");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// insert Prescription_Drug data into table.
				
				String sqlINSERT = "INSERT INTO Prescription_Drug (ID, tradeName, genericName) "
						+ "VALUES "
						+ "(1,'Tylenol with Codeine','acetaminophen and codeine'),"
						+ "(2,'Proair Proventil','albuterol aerosol'),"
						+ "(3,'Accuneb','albuterol HFA'),"
						+ "(4,'Fosamax','alendronate'),"
						+ "(5,'Zyloprim','allopurinol'),"
						+ "(6,'Xanax','alprazolam'),"
						+ "(7,'Elavil','amitriptyline'),"
						+ "(8,'Augmentin','amoxicillin and clavulanate K+'),"
						+ "(9,'Amoxil','amoxicillin'),"
						+ "(10,'Adderall XR','amphetamine and dextroamphetamine XR'),"
						+ "(11,'Tenormin','atenolol'),"
						+ "(12,'Lipitor','atorvastatin'),"
						+ "(13,'Zithromax','azithromycin'),"
						+ "(14,'Lotrel','benazepril and amlodipine'),"
						+ "(15,'Soma','carisoprodol'),"
						+ "(16,'Coreg','carvedilol'),"
						+ "(17,'Omnicef','cefdinir'),"
						+ "(18,'Celebrex','celecoxib'),"
						+ "(19,'Keflex','cephalexin'),"
						+ "(20,'Cipro','ciprofloxacin'),"
						+ "(21,'Celexa','citalopram'),"
						+ "(22,'Klonopin','clonazepam'),"
						+ "(23,'Catapres','clonidine HCl'),"
						+ "(24,'Plavix','clopidogrel'),"
						+ "(25,'Premarin','conjugated estrogens'),"
						+ "(26,'Flexeril','cyclobenzaprine'),"
						+ "(27,'Valium','diazepam'),"
						+ "(28,'Voltaren','diclofenac sodium'),"
						+ "(29,'Yaz','drospirenone and ethinyl estradiol'),"
						+ "(30,'Cymbalta','Duloxetine'),"
						+ "(31,'Vibramycin','doxycycline hyclate'),"
						+ "(32,'Vasotec','enalapril'),"
						+ "(33,'Lexapro','escitalopram'),"
						+ "(34,'Nexium','esomeprazole'),"
						+ "(35,'Zetia','ezetimibe'),"
						+ "(36,'Tricor','fenofibrate'),"
						+ "(37,'Allegra','fexofenadine'),"
						+ "(38,'Diflucan','fluconozole'),"
						+ "(39,'Prozac','fluoxetine HCl'),"
						+ "(40,'Advair','fluticasone and salmeterol inhaler'),"
						+ "(41,'Flonase','fluticasone nasal spray'),"
						+ "(42,'Folic Acid','folic acid'),"
						+ "(43,'Lasix','furosemide'),"
						+ "(44,'Neurontin','gabapentin'),"
						+ "(45,'Amaryl','glimepiride'),"
						+ "(46,'Diabeta','glyburide'),"
						+ "(47,'Glucotrol','glipizide'),"
						+ "(48,'Microzide','hydrochlorothiazide'),"
						+ "(49,'Lortab','hydrocodone and acetaminophen'),"
						+ "(50,'Motrin','ibuprophen'),"
						+ "(51,'Lantus','insulin glargine'),"
						+ "(52,'Imdur','isosorbide mononitrate'),"
						+ "(53,'Prevacid','lansoprazole'),"
						+ "(54,'Levaquin','levofloxacin'),"
						+ "(55,'Levoxl','levothyroxine sodium'),"
						+ "(56,'Zestoretic','lisinopril and hydrochlorothiazide'),"
						+ "(57,'Prinivil','lisinopril'),"
						+ "(58,'Ativan','lorazepam'),"
						+ "(59,'Cozaar','losartan'),"
						+ "(60,'Mevacor','lovastatin'),"
						+ "(61,'Mobic','meloxicam'),"
						+ "(62,'Glucophage','metformin HCl'),"
						+ "(63,'Medrol','methylprednisone'),"
						+ "(64,'Toprol','metoprolol succinate XL'),"
						+ "(65,'Lopressor','metoprolol tartrate'),"
						+ "(66,'Nasonex','mometasone'),"
						+ "(67,'Singulair','montelukast'),"
						+ "(68,'Naprosyn','naproxen'),"
						+ "(69,'Prilosec','omeprazole'),"
						+ "(70,'Percocet','oxycodone and acetaminophen'),"
						+ "(71,'Protonix','pantoprazole'),"
						+ "(72,'Paxil','paroxetine'),"
						+ "(73,'Actos','pioglitazone'),"
						+ "(74,'Klor-Con','potassium Chloride'),"
						+ "(75,'Pravachol','pravastatin'),"
						+ "(76,'Deltasone','prednisone'),"
						+ "(77,'Lyrica','pregabalin'),"
						+ "(78,'Phenergan','promethazine'),"
						+ "(79,'Seroquel','quetiapine'),"
						+ "(80,'Zantac','ranitidine'),"
						+ "(81,'Crestor','rosuvastatin'),"
						+ "(82,'Zoloft','sertraline HCl'),"
						+ "(83,'Viagra','sildenafil HCl'),"
						+ "(84,'Vytorin','simvastatin and ezetimibe'),"
						+ "(85,'Zocor','simvastatin'),"
						+ "(86,'Aldactone','spironolactone'),"
						+ "(87,'Bactrim DS','sulfamethoxazole and trimethoprim DS'),"
						+ "(88,'Flomax','tamsulosin'),"
						+ "(89,'Restoril','temezepam'),"
						+ "(90,'Topamax','topiramate'),"
						+ "(91,'Ultram','tramadol'),"
						+ "(92,'Aristocort','triamcinolone Ace topical'),"
						+ "(93,'Desyrel','trazodone HCl'),"
						+ "(94,'Dyazide','triamterene and hydrochlorothiazide'),"
						+ "(95,'Valtrex','valaciclovir'),"
						+ "(96,'Diovan','valsartan'),"
						+ "(97,'Effexor XR','venlafaxine XR'),"
						+ "(98,'Calan SR','verapamil SR'),"
						+ "(99,'Ambien','zolpidem');";
				String[] keycols = {"ID"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				ps.executeUpdate();
				System.out.println("Drug information inserted");
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
	
		public static void Retail_Pharmacy_main() {

			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
			
				// delete all Retail_Pharmacy rows 
				ps = conn.prepareStatement("delete from Retail_Pharmacy");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// generate Retail_Pharmacy data and insert into table. 
				
				String sqlINSERT = "insert into Retail_Pharmacy(ID, name, address, phoneNumber)"
						+ " values( ?, ?, ?, ?)";
				String[] keycols = {"ID"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 10 rows with data
				for (int k=0; k<=14; k++) {
					
					int pharmacyID = k+1;
					String pharmacyName = (getName("first") + "'s Pharmacy");
					String address = getAddress();
					String phoneNumber = getPhoneNumber();
					
					ps.setInt(1, pharmacyID);
					ps.setString(2, pharmacyName);
					ps.setString(3, address);
					ps.setString(4, phoneNumber);
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
					
				}
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
	
		public static void Pharmaceutical_Company_main() {

			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				int row_count;
			
				// delete all Pharmaceutical_Company rows 
				ps = conn.prepareStatement("delete from Pharmaceutical_Company");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// generate Pharmaceutical_Company data and insert into table. 
				
				String sqlINSERT = "insert into Pharmaceutical_Company(ID, name, phoneNumber)"
						+ " values( ?, ?, ?)";
				String[] keycols = {"ID"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 10 rows with data
				for (int k=0; k<=10; k++) {
					
					int pharmacyID = k+1;
					String pharmacyName = (getName("last") + " Pharmaceuticals, Inc.");
					String phoneNumber = getPhoneNumber();
					
					ps.setInt(1, pharmacyID);
					ps.setString(2, pharmacyName);
					ps.setString(3, phoneNumber);
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
					
				}
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
		
		public static void Prescription_Order_main() {
			// connect to mysql server
			
			try (Connection conn = DriverManager.getConnection(DBURL, USERID, PASSWORD);) {
				
				PreparedStatement ps;
				PreparedStatement ps2;
				
				ResultSet rs;
				int row_count;
				
			
				// delete all Prescription_Order rows 
				ps = conn.prepareStatement("delete from Prescription_Order");
				row_count = ps.executeUpdate();
				System.out.println("rows deleted "+row_count);
				
				// generate Prescription_Order data and insert into table. 
				
				String sqlINSERT = "insert into Prescription_Order(RXNumber, Doctor_SSN, Patient_SSN, pharmacyID,"
						+ "drugID, pharmaceuticalCompanyID, prescribeDate, genericOK, quantity, fillDate)"
						+ " values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				String[] keycols = {"id"};
				ps = conn.prepareStatement(sqlINSERT, keycols);
				
				// insert 50 rows with data
				for (int k=0; k<=50; k++) {
				
					Random rnd = new Random();
					Date fillDate = getDate();
					Date prescribeDate = getDate();
					int RXNumber = getRXNumber();
					int Doctor_SSN = 0;
					int Patient_SSN = 0;
					int retailPharmacyID = 0;
					int drugID = 0;
					int pharmaceuticalCompanyID = 0;
					int quantity = rnd.nextInt(150);
					boolean genericOK = false;
					if ( k % 2 == 0 ) {
						genericOK = true;
					}
					// make sure fill date comes after prescribe date
					while (prescribeDate.compareTo(fillDate) > 0) {
						fillDate = getDate();
					}
					
					ps2 = conn.prepareStatement("select SSN from doctor ORDER BY RAND() LIMIT 1");
					rs = ps2.executeQuery();
					
					while(rs.next()) {
						Doctor_SSN = rs.getInt("SSN");
					}
					
					ps2 = conn.prepareStatement("select SSN from patient ORDER BY RAND() LIMIT 1");
					rs = ps2.executeQuery();
					
					while(rs.next()) {
						Patient_SSN = rs.getInt("SSN");
					}
					
					ps2 = conn.prepareStatement("select ID from retail_pharmacy ORDER BY RAND() LIMIT 1");
					rs = ps2.executeQuery();
					
					while(rs.next()) {
						retailPharmacyID = rs.getInt("ID");
					}
					
					ps2 = conn.prepareStatement("select ID from pharmaceutical_company ORDER BY RAND() LIMIT 1");
					rs = ps2.executeQuery();
					
					while(rs.next()) {
						pharmaceuticalCompanyID = rs.getInt("ID");
					}
					
					ps2 = conn.prepareStatement("select ID from prescription_drug ORDER BY RAND() LIMIT 1");
					rs = ps2.executeQuery();
					
					while(rs.next()) {
						drugID = rs.getInt("ID");
					}
					
					ps.setInt(1, RXNumber);
					ps.setInt(2, Doctor_SSN);
					ps.setInt(3, Patient_SSN);
					ps.setInt(4, retailPharmacyID);
					ps.setInt(5, drugID);
					ps.setInt(6, pharmaceuticalCompanyID);
					ps.setDate(7, prescribeDate);
					ps.setBoolean(8, genericOK);
					ps.setInt(9, quantity);
					ps.setDate(10, fillDate);
					row_count = ps.executeUpdate();
					System.out.println("row inserted "+row_count);
					
				}
		
							
			} catch (SQLException e) {
				System.out.println("Error: SQLException "+e.getMessage());
			}
			
		}
	
		
		// Helper functions below (Previously contained on separate file)
		
		
		public static int getRXNumber() {
			
			Random gen = new Random();
			int newRXNumber = gen.nextInt(999999999);
			
			String temp = Integer.toString(newRXNumber);
			while (usedRXNumber.contains(temp)) {
				gen = new Random();
				newRXNumber = gen.nextInt(999999999);
				temp = Integer.toString(newRXNumber);
			}
			
			usedRXNumber.add(temp);
			return newRXNumber;
		}
			
		public static String getSSN() {
			
			Random gen = new Random();
			String newSSN = Integer.toString(123450000+gen.nextInt(10000));
			
			
			while (usedSSN.contains(newSSN)) {
				gen = new Random();
				newSSN = Integer.toString(123450000+gen.nextInt(10000));
			}
			
			usedSSN.add(newSSN);
			return newSSN;
		}

		public static String getPhoneNumber() {
			
			Random rand = new Random();

		    int num1, num2, num3;

		    num1 = rand.nextInt (900) + 100;
		    num2 = rand.nextInt (643) + 100;
		    num3 = rand.nextInt (9000) + 1000;
		    String newPhoneNumber = (num1 + "-" + num2 + "-" + num3);
		    
		    while(usedPhoneNumber.contains(newPhoneNumber)) {
		    	num1 = rand.nextInt (900) + 100;
		        num2 = rand.nextInt (643) + 100;
		        num3 = rand.nextInt (9000) + 1000;
		    	newPhoneNumber = (num1 + "-" + num2 + "-" + num3);
		    }
		    usedPhoneNumber.add(newPhoneNumber);
		    
		    return newPhoneNumber;
		}

		public static String getName(String nameType) {
				
				String[] firstNames= {"Michael", "Christopher", "Jessica", "Matthew", "Ashley", "Jennifer", "Joshua", "Amanda", 
						"Daniel", "David", "James", "Robert", "John", "Joseph", "Andrew", "Ryan", "Brandon", "Jason", "Justin", 
						"Sarah", "William", "Jonathan", "Stephanie", "Brian", "Nicole", "Nicholas", "Anthony", "Heather", "Eric", 
						"Elizabeth", "Adam", "Megan", "Melissa", "Kevin", "Steven", "Thomas", "Timothy", "Christina", "Kyle", 
						"Rachel", "Laura", "Lauren", "Amber", "Brittany", "Danielle", "Richard", "Kimberly", "Jeffrey", "Amy", 
						"Crystal", "Michelle", "Tiffany", "Jeremy", "Benjamin", "Mark", "Emily", "Aaron", "Charles", "Rebecca", 
						"Jacob", "Stephen", "Patrick", "Sean", "Erin", "Zachary", "Jamie", "Kelly", "Samantha", "Nathan", "Sara", 
						"Dustin", "Paul", "Angela", "Tyler", "Scott", "Katherine", "Andrea", "Gregory", "Erica", "Mary", "Travis", 
						"Lisa", "Kenneth", "Bryan", "Lindsey", "Kristen", "Jose", "Alexander", "Jesse", "Katie", "Lindsay", 
						"Shannon", "Vanessa", "Courtney", "Christine", "Alicia", "Cody", "Allison", "Bradley", "Samuel", "Shawn", 
						"April", "Derek", "Kathryn", "Kristin", "Chad", "Jenna", "Tara", "Maria", "Krystal", "Jared", "Anna", 
						"Edward", "Julie", "Peter", "Holly", "Marcus", "Kristina", "Natalie", "Jordan", "Victoria", "Jacqueline", 
						"Corey", "Keith", "Monica", "Juan", "Donald", "Cassandra", "Meghan", "Joel", "Shane", "Phillip", "Patricia", 
						"Brett", "Ronald", "Catherine", "George", "Antonio", "Cynthia", "Stacy", "Kathleen", "Raymond", "Carlos", 
						"Brandi", "Douglas", "Nathaniel", "Ian", "Craig", "Brandy", "Alex", "Valerie", "Veronica", "Cory", "Whitney", 
						"Gary", "Derrick", "Philip", "Luis", "Diana", "Chelsea", "Leslie", "Caitlin", "Leah", "Natasha", "Erika", 
						"Casey", "Latoya", "Erik", "Dana", "Victor", "Brent", "Dominique", "Frank", "Brittney", "Evan", "Gabriel"};
				
				
				String[] lastNames= {"Graves", "Scarborough", "Sutton", "Sinclair", "Bowman", "Olsen", "Love", "McLean", 
						"Christian", "Lamb", "James", "Chandler", "Stout", "Cowan", "Golden", "Bowling", 
						"Beasley", "Clapp", "Abrams", "Tilley", "Morse", "Boykin", "Sumner", "Cassidy", 
						"Heath", "Blanchard", "McAllister", "McKenzie", "Byrne", "Schroeder", "Gross", "Perkins", 
						"Robertson", "Palmer", "Brady", "Rowe", "Zhang", "Hodge", "Li", "Justice", 
						"Glass", "Willis", "Hester", "Floyd", "Fischer", "Norman", "Chan", "Hunt", 
						"Byrd", "Lane", "Kaplan", "Heller", "Jennings", "Hanna", "Locklear", "Holloway", 
						"Glover", "O'Donnell", "Goldman", "McKenna", "Starr", "Stone", "McClure", "Watson", 
						"Abbott", "Singer", "Farrell", "Atkins", "Sykes", "Reid", "Finch", "Hobbs", 
						"Adkins", "Kinney", "Whitaker", "Alexander", "Conner", "Waters", "Becker", "Rollins", 
						"Black", "Fox", "Hatcher", "Wu", "Lloyd", "Joyce", "Welch", "Matthews", 
						"Chappell", "MacDonald", "Kane", "Butler", "Pickett", "Kennedy", "Thornton", "McNeill", 
						"Weinstein", "Moss", "Carlton", "Schultz", "Nichols", "Harvey", "Stevenson", "Houston", 
						"Dunn", "West", "Barr", "Snyder", "Cain", "Boswell", "Pittman", "Weiner", 
						"Petersen", "Davis", "Coleman", "Terrell", "Burch", "Parrott", "Henry", "Gray", 
						"Chang", "Siegel", "Garrett", "Neal", "Shaffer", "Choi", "Carver", "Shelton", 
						"House", "Lyons", "Moser", "Dickinson", "Dodson", "Spencer", "Burgess", "Liu", 
						"Wong", "Blackburn", "McKay", "Frazier", "Braswell", "Donovan", "Barrett", "Nance", 
						"Washington", "Rogers", "McMahon", "Miles", "Kramer", "Bowles", "Brown", "Bolton", 
						"Craven", "Hendrix", "Saunders", "Lehman", "Sherrill", "Cash", "Sullivan", "Mack", 
						"Rice", "Ayers", "Cherry", "Richmond", "York", "Wiley", "Harrington", "Reed", 
						"Nash", "Kent", "Holland", "Clements", "Hawley", "Skinner", "Hamrick", "Winters", 
						"Dolan", "Turner", "Beatty", "Douglas", "Hendricks", "Mayer", "Cochran", "Reilly", 
						"Jensen", "Yates", "Haynes", "Harmon", "Dawson", "Barefoot", "Pope", "Schwartz", 
						"Singleton", "Ballard", "Spivey", "Denton", "Huff", "Berger", "McCall", "Pollard", 
						"Garcia", "Crane", "Wolf", "Dalton", "Currin", "Stanton", "Carey", "Hess", 
						"Robinson", "Mills", "McDonald", "Lanier", "Harris", "Parsons", "Vaughn", "Banks", 
						"Oakley", "Rubin", "Maynard", "Livingston", "Lam", "Thompson", "Creech", "Dillon", 
						"Foster", "Roy", "Barbour", "Burke", "Ritchie", "Odom", "Pearce", "Rosenberg", 
						"O'Connor", "Cates", "McIntosh", "Olson", "Cox", "Erickson", "Briggs", "Klein", 
						"Goldberg", "Hinson", "Weiss", "Pritchard", "Lassiter", "Massey", "Stark", "Dunlap", 
						"Humphrey", "Horowitz", "Lutz", "Hoover", "Kang", "Ellington", "Lynn", "Albright", 
						"Alston", "Burnette", "O'Neal", "Morris", "Callahan", "Conway", "Savage", "Henson", 
						"Wang", "Ellis", "Pierce", "Woodward", "Godfrey", "Langston", "Eaton", "Lowe", 
						"Fuller", "Simmons", "Knight", "Gold", "Hensley", "French", "Hughes", "Pate", 
						"Burnett", "Francis", "Horn", "Forrest", "Levin", "Durham", "Guthrie", "Freedman", 
						"Wiggins", "Best", "Crawford", "Drake", "Curtis", "Walter", "Jenkins", "Hood", 
						"Jiang", "Johnson", "Craig", "McIntyre", "Brantley", "Kelley", "Smith", "Wall", 
						"Quinn", "Hicks", "Garrison", "Dickerson", "Waller", "Carter", "Katz", "Hull"};
				
				if(nameType.equals("first")) {
				    Random rand = new Random();
				    int randomInt = rand.nextInt(firstNames.length);
				    return firstNames[randomInt];
				}
				else if(nameType.equals("last")) {
					Random rand = new Random();
				    int randomInt = rand.nextInt(lastNames.length);
				    return lastNames[randomInt];
				}
				    else {
					return "";
				}
		}

		public static Date getDate(){
			Random  rnd;
			Date    dt;
			long    ms;

			// Get a new random instance, seeded from the clock
			rnd = new Random();

			// Get an Epoch value roughly between 1940 and 2010
			// -946771200000L = January 1, 1940
			// Add up to 70 years to it (using modulus on the next long)
			ms = -846771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

			// Construct a date
			dt = new Date(ms);
			return dt;
		}

		public static String getAddress() {
			
			String result;

			String[] streets = {"Locust Street", "Bridge Street", "Madison Avenue", "Jackson Street", "Spruce Street", "Ridge Road", "Meadow Lane", "Grove Street", 
					"5th Street", "Pearl Street", "Lincoln Street", "Pleasant Street", "Pennsylvania Avenue", "Dogwood Drive", "Madison Street", "Lincoln Avenue", 
					"Jefferson Avenue", "Adams Street", "Academy Street", "7th Street", "4th Street West", "Route 1", "Green Street", "East Street", 
					"3rd Street West", "11th Street", "Summit Avenue", "River Street", "Elizabeth Street", "Cherry Lane", "9th Street", "6th Street", 
					"2nd Avenue", "12th Street", "Virginia Avenue", "Hill Street", "Hickory Lane", "Charles Street", "5th Avenue", "10th Street", 
					"Liberty Street", "Fairway Drive", "Woodland Drive", "Winding Way", "Vine Street", "Route 30", "Monroe Street", "Delaware Avenue", 
					"Colonial Drive", "Church Road", "Broadway", "1st Avenue", "Valley Road", "Sunset Drive", "Prospect Avenue", "Lake Street", 
					"Brookside Drive", "3rd Avenue", "2nd Street West", "Mill Road", "Hillside Avenue", "Dogwood Lane", "College Street", "13th Street", 
					"Willow Street", "Railroad Street", "Oak Lane", "New Street", "Laurel Lane", "Lakeview Drive", "Harrison Street", "Division Street", 
					"8th Street", "6th Street West", "5th Street North", "4th Avenue", "Summit Street", "Sherwood Drive", "Route 6", "Route 32", 
					"Riverside Drive", "Railroad Avenue", "Primrose Lane", "Penn Street", "Park Drive", "Laurel Street", "Hillcrest Drive", "George Street", 
					"Clinton Street", "Beech Street", "7th Avenue", "5th Street West", "Poplar Street", "Lafayette Avenue", "King Street", "Holly Drive", 
					"Hillside Drive", "Heather Lane", "Grant Street", "Durham Road", "Cedar Lane", "Buckingham Drive", "6th Avenue", "4th Street North", 
					"Wood Street", "Williams Street", "Warren Street", "Walnut Avenue", "Surrey Lane", "Route 29", "Route 10", "Ridge Avenue", 
					"Mulberry Street", "Linden Street", "James Street", "Front Street North", "Franklin Avenue", "Elm Avenue", "College Avenue", "Clark Street", 
					"Circle Drive", "Berkshire Drive", "6th Street North", "Woodland Avenue", "William Street", "Smith Street", "Shady Lane", "Oxford Court", 
					"Myrtle Avenue", "Maple Lane", "Lafayette Street"};
			
			String[] cities = {"Bridgeport", "Brighton", "Brownsville", "Bryan", "Buffalo", "Burbank", "Burlington", "Cambridge", 
					"Canton", "Cape Coral", "Carrollton", "Cary", "Cathedral City", "Cedar Rapids", "Champaign", "Chandler", 
					"Charleston", "Charlotte", "Chattanooga", "Chesapeake", "Chicago", "Chula Vista", "Cincinnati", "Clarke County", 
					"Clarksville", "Clearwater", "Cleveland", "College Station", "Colorado Springs", "Columbia", "Columbus", "Concord", 
					"Coral Springs", "Corona", "Corpus Christi", "Costa Mesa", "Dallas", "Daly City", "Danbury", "Davenport", 
					"Davidson County", "Dayton", "Daytona Beach", "Deltona", "Denton", "Denver", "Des Moines", "Detroit", 
					"Downey", "Duluth", "Durham", "El Monte", "El Paso", "Elizabeth", "Elk Grove", "Elkhart", 
					"Erie", "Escondido", "Eugene", "Evansville", "Fairfield", "Fargo", "Fayetteville", "Fitchburg", 
					"Flint", "Fontana", "Fort Collins", "Fort Lauderdale", "Fort Smith", "Fort Walton Beach", "Fort Wayne", "Fort Worth", 
					"Frederick", "Fremont", "Fresno", "Fullerton", "Gainesville", "Garden Grove", "Garland", "Gastonia", 
					"Gilbert", "Glendale", "Grand Prairie", "Grand Rapids", "Grayslake", "Green Bay", "GreenBay", "Greensboro", 
					"Greenville", "Gulfport-Biloxi", "Hagerstown", "Hampton", "Harlingen", "Harrisburg", "Hartford", "Havre de Grace", 
					"Hayward", "Hemet", "Henderson", "Hesperia", "Hialeah", "Hickory", "High Point", "Hollywood", 
					"Honolulu", "Houma", "Houston", "Howell", "Huntington", "Huntington Beach", "Huntsville", "Independence", 
					"Indianapolis", "Inglewood", "Irvine", "Irving", "Jackson", "Jacksonville", "Jefferson", "Jersey City", 
					"Johnson City", "Joliet", "Kailua", "Kalamazoo", "Kaneohe", "Kansas City", "Kennewick", "Kenosha", 
					"Killeen", "Kissimmee", "Knoxville", "Lacey", "Lafayette", "Lake Charles", "Lakeland", "Lakewood", 
					"Lancaster", "Lansing", "Laredo", "Las Cruces", "Las Vegas", "Layton"};
			
			String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", 
					"FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", 
					"KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", 
					"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", 
					"NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", 
					"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", 
					"WI", "WY"};
			

			
			Random rnd;
			rnd = new Random();
			int streetNumber = rnd.nextInt(9999);
			int zipCode = (rnd.nextInt(89999) + 10000);
			int streetIndex = rnd.nextInt(streets.length - 1);
			int cityIndex = rnd.nextInt(cities.length - 1);
			int stateIndex = rnd.nextInt(states.length - 1);
			
			result = (streetNumber + " " 
			+ streets[streetIndex] + ", " 
			+ cities[cityIndex]    + ", " 
			+ states[stateIndex]   + " " 
			+ zipCode);
			
			return result;
		}

	
}
