package com.csumb.cst363;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * Controller class for patient interactions.
 *   register as a new patient.
 *   update patient profile.
 */
@Controller
public class ControllerPatient {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * Request blank patient registration form.
	 */
	@GetMapping("/patient/new")
	public String newPatient(Model model) {
		// return blank form for new patient registration
		model.addAttribute("patient", new Patient());
		return "patient_register";
	}
	
	
	public boolean checkString(String raw_str) {
		String str = raw_str.trim();
		if (str.equals("") | str == null) {
			return false;
		}
		int str_len = str.length();
		for (int i=0;i<str_len;i++) {
			if(Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkCity(String raw_city) {
		String str = raw_city.trim();
		if (str.equals("") | str == null) {
			return false;
		}
		int str_len = str.length();
		for (int i=0;i<str_len;i++) {
			if(Character.isLetter(str.charAt(i)) == false & str.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}
	
	public boolean zipcheck(String str) {
		if (str.equals("") | str == null) {
			return false;
		}
		int str_len = str.length();
		if (str_len<5) {
			return false;
		}else {
			if (str_len == 5) {
				for (int i=0; i<str_len; i++) {
					if(Character.isDigit(str.charAt(i)) == false) {
						return false;
					}
				}
			return true;
			}else if (str_len == 10) {
				for (int i=0; i<5; i++) {
					if(Character.isDigit(str.charAt(i)) == false) {
						return false;
					}
				}if(str.charAt(5) != '-') {
					return false;
				}for (int i=6; i<str_len; i++) {
					if(Character.isDigit(str.charAt(i)) == false) {
						return false;
					}
				}return true;
			}else {
				return false;
			}
		}
	}
	
	public boolean ssncheck(String ssn) {
		if (ssn.equals("") | ssn == null | ssn.length() != 9) {
			return false;
		}
		if (ssn.charAt(0) == '0' |ssn.charAt(0) == '9') {
			return false;
		}else if (ssn.charAt(3) == '0' & ssn.charAt(4) == '0') {
			return false;
		}else if (ssn.substring(5,9).equals("0000")) {
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * Process new patient registration	 */
	@PostMapping("/patient/new")
	public String newPatient(Patient p, Model model) {
		try (Connection con = getConnection();){
			String doc_name = p.getPrimaryName();
			String[] split_name = doc_name.trim().split("\\s+");
			
			int first_index;
			int last_index;
			boolean comma;
			
			if (doc_name.toLowerCase().contains("dr.") & !doc_name.contains(",") & split_name.length == 3) {
				first_index = 1;
				last_index = split_name.length -1;
				comma = false;
			}else if (doc_name.contains(",") & split_name.length == 2) {
				last_index = 0;
				first_index = 1;
				comma = true;
			}else {
				model.addAttribute("message", "Invalid format used for Doctor Name. "
						+ "Use 'Dr. FirstName LastName' or 'LastName, FirstName' format");
				model.addAttribute("patient", p);
				return "patient_register";
			}
			
			String doc_first_name = split_name[first_index];
			String doc_last_name;
			if (comma) {
				doc_last_name = split_name[last_index].replace(",","");
			}else {
				doc_last_name = split_name[last_index];
			}
			
			PreparedStatement ps2 = con.prepareStatement("select ssn, specialtyID from doctor where firstName LIKE ? AND lastName = ?");
			ps2.setString(1, "%"+doc_first_name+"%");
			ps2.setString(2, doc_last_name);
			ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			int doc_ssn = rs2.getInt("ssn");
			int doc_spec = rs2.getInt("specialtyID");
			
			Date dob = p.getDateOfBirth();
			int birthyear = Integer.parseInt(dob.toString().substring(0,4));
			
			if (birthyear > 2022 | birthyear<1900) {
				model.addAttribute("message", "Invalid year used for date of birth. "
						+ "Please correct and try again.");
				model.addAttribute("patient", p);
				return "patient_register";
			}
				
			int patientAge = LocalDate.now().compareTo(dob.toLocalDate());
			
			String tempSSN = Integer.toString(p.getSSN());
			
			if (doc_spec <=2  | (patientAge <18 & doc_spec == 3)){
				// Data validation Checks
				if (checkString(p.getFirstName()) == false) {
					model.addAttribute("message", "Invalid First Name Entered. Please enter"
							+ "name with only alphabetic characters");
					model.addAttribute("patient", p);
					return "patient_register";
				}else if (checkString(p.getLastName()) == false) {
					model.addAttribute("message", "Invalid Last Name Entered. Please enter"
							+ "name with only alphabetic characters");
					model.addAttribute("patient", p);
					return "patient_register";
				}else if (checkCity(p.getCity()) == false) {
					model.addAttribute("message", "Invalid city name entered. Please enter city "
							+ "name with only alphabetic characters");
					model.addAttribute("patient", p);
					return "patient_register";
				}else if (checkString(p.getState()) == false) {
					model.addAttribute("message", "Invalid state name entered. Please enter state "
							+ "name with only alphabetic characters");
					model.addAttribute("patient", p);
					return "patient_register";
				}else if (zipcheck(p.getZipcode()) == false) {
					model.addAttribute("message", "Invalid zip code entered. Please enter five digit "
							+ "zip code (#####) or 5+4 digit zip code (#####-####)");
					model.addAttribute("patient", p);
					return "patient_register";
				}else if (ssncheck(tempSSN)== false) {
					model.addAttribute("message", "Invalid SSN entereed. Please re-enter 9 digit SSN");
					model.addAttribute("patient", p);
					return "patient_register";
				}
				
				LocalDate ldt = LocalDate.now();
				Date currentYear = Date.valueOf(ldt);
				Date dateOfBirth = p.getDateOfBirth();
				int age = currentYear.getYear() - dateOfBirth.getYear();
				
				PreparedStatement ps = con.prepareStatement("insert into patient(ssn, firstname, lastname, dateofbirth,"
					+ "address, doctorssn, age, primaryPhysician ) values(?, ?, ?, ?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, p.getSSN());
				ps.setString(2, p.getFirstName());
				ps.setString(3, p.getLastName());
				ps.setDate(4, dob);
				String patient_address = p.getStreet() + ", "+ p.getCity() + ", " + p.getState() + " " + p.getZipcode();
				ps.setString(5, patient_address);
				ps.setInt(6, doc_ssn);
				ps.setInt(7,  age);
				ps.setString(8, doc_name);
				p.setAge(age);
				p.setPrimaryPhysician(doc_name);
				p.setPrimaryName(doc_name);
				p.setAddress(patient_address);
				ps.executeUpdate();
			
				// display message and patient information
				model.addAttribute("message", "Registration successful.");
				model.addAttribute("patient", p);
				return "patient_show";
			}else {
				//ADD logic for different cases: invalid doctor name, invalid doctor, etc.
				PreparedStatement ps3 = con.prepareStatement("select count(ssn) as doc_count from doctor where firstName LIKE ? AND lastName = ?");
				ps3.setString(1, "%"+doc_first_name+"%");
				ps3.setString(2, doc_last_name);
				
				ResultSet rs3 = ps3.executeQuery();
				rs3.next();
				int doc_count = rs3.getInt("doc_count");
				
				if (doc_count == 0) {
					model.addAttribute("message", "Invalid Doctor Selected. Please enter another doctor name.");
					model.addAttribute("patient", p);
					return "patient_register";
				}else {
					model.addAttribute("message", "Invalid Doctor Selected. "
							+ "Please select doctor with internal medicine, family practice, or pediactrics speciality.");
					model.addAttribute("patient", p);
					return "patient_register";
				}
				
			}
				
		}catch (SQLException e){
				
				model.addAttribute("message", "SQL Error."+e.getMessage());
				model.addAttribute("patient", p);
				return "patient_register";
		}

	}
	
	/*
	 * Request blank form to search for patient by and and id
	 */
	@GetMapping("/patient/edit")
	public String getPatientForm(Model model) {
		return "patient_get";
	}
	
	
	/*
	 * Perform search for patient by patient id and name.
	 */
	@PostMapping("/patient/show")
	public String getPatientForm(Patient patient, Model model) {
		
		try (Connection conn = getConnection();){
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM patient WHERE SSN = ? AND lastName = ?;");
			
			ps.setInt(1, patient.getSSN());
			ps.setString(2, patient.getLastName());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				patient.setSSN(rs.getInt("SSN"));
				patient.setFirstName(rs.getString("firstName"));
				patient.setLastName(rs.getString("lastName"));
				patient.setDateOfBirth(rs.getDate("dateOfBirth"));
				patient.setAge(rs.getInt("age"));
				patient.setAddress(rs.getString("address"));
				patient.setPrimaryPhysician(rs.getString("primaryPhysician"));
				patient.setDoctorSSN(rs.getInt("doctorSSN"));
				model.addAttribute("patient", patient);
				return "patient_show";
			} else {
				model.addAttribute("message", "Patient not found.");
				return "patient_get";
			}
			
	} catch (SQLException e) {
		System.out.println("SQL error in getPatient "+e.getMessage());
		model.addAttribute("message", "SQL Error."+e.getMessage());
		model.addAttribute("patient", patient);
		return "patient_get";
	}
}

	/*
	 *  Display patient profile for patient id.
	 */
	@GetMapping("/patient/edit/{patientSSN}")
	public String updatePatient(@PathVariable int patientSSN, Model model) {
		Patient patient = new Patient();
		patient.setSSN(patientSSN);
		try (Connection conn = getConnection();) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM patient WHERE SSN = ?;");
			ps.setInt(1, patientSSN);
		
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				patient.setSSN(rs.getInt("SSN"));
				patient.setFirstName(rs.getString("firstName"));
				patient.setLastName(rs.getString("lastName"));
				patient.setDateOfBirth(rs.getDate("dateOfBirth"));
				patient.setAge(rs.getInt("age"));
				patient.setAddress(rs.getString("address"));
				patient.setPrimaryPhysician(rs.getString("primaryPhysician"));
				patient.setDoctorSSN(rs.getInt("doctorSSN"));
				model.addAttribute("patient", patient);
				return "patient_edit";
			} else {
				model.addAttribute("message", "Unable to find patient.");
				model.addAttribute("patient", patient);
				return "patient_get";
			}
			
		} catch (SQLException e) {
			model.addAttribute("message", "SQL Error."+e.getMessage());
			model.addAttribute("patient", patient);
			return "patient_get";
		}	
	
}
	
	
	/*
	 * Process changes to patient profile.  
	 */
	@PostMapping("/patient/edit")
	public String updatePatient(Patient p, Model model) {

		try (Connection conn = getConnection();) {
			PreparedStatement ps = conn.prepareStatement("UPDATE patient SET firstName = ?, lastName = ?, "
					+ "dateOfBirth = ?, age = ?, address = ?, primaryPhysician = ? WHERE SSN = ?; ");		
		
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setDate(3, p.getDateOfBirth());
			ps.setInt(4, p.getAge());
			ps.setString(5, p.getAddress());
			ps.setString(6, p.getPrimaryPhysician());
			ps.setInt(7, p.getSSN());
			
			// Replaces all non alphanumeric with white space except spaces, commas, periods, and apostrophes
			p.setFirstName(p.getFirstName().replaceAll("[^a-zA-Z0-9\\s ,.']", " "));
			p.setLastName(p.getLastName().replaceAll("[^a-zA-Z0-9\\s ,.']", " "));
			p.setAddress(p.getAddress().replaceAll("[^a-zA-Z0-9\\s ,.']", " "));
			p.setPrimaryPhysician(p.getPrimaryPhysician().replaceAll("[^a-zA-Z0-9\\s ,.']", " "));
		
			// Checks valid full name
			if(p.getFirstName().isBlank() || p.getLastName().isBlank()) {
				model.addAttribute("message", "Error. Name is required");
				model.addAttribute("patient", p);
				return "patient_edit";
			}
			// Checks valid address
			if(p.getAddress().isBlank()) {
				model.addAttribute("message", "Error. Adress is required");
				model.addAttribute("patient", p);
				return "patient_edit";
			}
			//Checks valid primary care doctor name
			if(p.getPrimaryPhysician().isBlank()) {
				model.addAttribute("message", "Error. Primary Care Physician is required");
				model.addAttribute("patient", p);
				return "patient_edit";
			}

			if (ps.executeUpdate()==1) {
				model.addAttribute("message", "Update successful");
				model.addAttribute("patient", p);
				return "patient_show";
				
			}else {
				model.addAttribute("message", "Error. Update was not successful");
				model.addAttribute("patient", p);
				return "patient_edit";
			}
				
		} catch (SQLException e) {
			model.addAttribute("message", "SQL Error."+e.getMessage());
			model.addAttribute("patient", p);
			return "patient_edit";
		}
	}

	/*
	 * return JDBC Connection using jdbcTemplate in Spring Server
	 */

	private Connection getConnection() throws SQLException {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		return conn;
	}

}
