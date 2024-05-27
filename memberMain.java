package C209_GA;
/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 9 Jul 2022 2:59:45 pm
 */

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class memberMain {
	
	// Connection is to connect to a database.
	Connection con;
	// Create SQL statement
	Statement state;
	// Representing a table of data which is linked with Statement.
	ResultSet Rs;
	
	ArrayList<Member> memberList = new ArrayList<Member>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		memberMain M2 = new memberMain();
		M2.start();
		
	}
	
	private void start() {
		
		try {
			// Establish the database URL on browser.
			String connection = "jdbc:mysql://localhost/demodb";
			// Create a user.
			String user = "root";
			// A empty password.
			String password = "";
			
			// To get connection using parameters of connection of database, the user and password.
			con = DriverManager.getConnection(connection, user, password);
			// Create SQL statement from the Connection made with the database.
			state = con.createStatement();
			// Execute the SQL statement in the parameter.
			// * is to SELECT all attributes from memeber table.
			// LEFT JOIN is to join another table, citation with their common attribute, ID.
			/* I use LEFT JOIN because it can display all data from both table while 
			INNER JOIN only displays the citation attributes.*/
			Rs = state.executeQuery("SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID");
			
			// Loop through the table rows.
			while (Rs.next()) {
				// A condition to get Category to be equal to Ordinary, add it to the Ordinary class.
				if (Rs.getString("Category").equals("Ordinary")) {
					memberList.add(new Ordinary(Rs.getString("ID"), Rs.getString("Name"), 
							Rs.getString("Category"), Rs.getDate("MemberUntil")));
				// If Category column is Lifetime, add it to the Lifetime class.
				} else if (Rs.getString("Category").equals("Lifetime")) {
					memberList.add(new Lifetime(Rs.getString("ID"),Rs.getString("Name"), 
							Rs.getString("Category"), Rs.getString("Citation")));
					// If Category column is Student, add it to the Student class.
				} else if (Rs.getString("Category").equals("Student")) { 
					memberList.add(new Student(Rs.getString("ID"),Rs.getString("Name"), 
							Rs.getString("Category"), Rs.getString("School")));
				}  
			}

			int option = -1;
			
			while (option != 4) {
				
				Menu();
				option = Helper.readInt("\nEnter choice > ");
				
				if (option == 1) {
					viewAllMembers();
				} else if (option == 2) {
					viewByCategory();
				} else if (option == 3) {
					viewByID();
				} else if (option == 4) {
					// close the database connection.
					con.close();
					state.close();
					Rs.close();
					System.out.println("Good Bye!");
				} else {
					System.out.println("Invalid Option!");
				}	
			} 
		// Catch exception if database is not connected.
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		}		
	}
		
	private void viewAllMembers() {
		//Display the columns.
		Helper.line(100, "=");
		System.out.println("View All Members");
		Helper.line(100, "=");
		String formatDate = "";
		String output = "";
		output += String.format("%-7s %-15s %-10s %-17s %-20s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
		//Loop through the arrayList
		for (Member i : memberList) {
			// check if the object is from Ordinary class
			if (i instanceof Ordinary) {
				Ordinary ord = (Ordinary)i;
				// Format the date to 'Day of month, Name of Month, Year'
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
				DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
				formatDate = memberDate.format(format2);
				output += String.format("%-7s %-15s %-10s %s\n", i.getID(), 
						i.getName(), i.getCategory(), formatDate);
				// check if the object is from Student class
				} else if (i instanceof Student) {
					Student stu = (Student)i;
					output += String.format("%-7s %-15s %-29s %s\n", i.getID(), 
							i.getName(), i.getCategory(), stu.getSchool());
					// check if the object is from Lifetime class
				} else if (i instanceof Lifetime) {
					Lifetime LT = (Lifetime)i;
					// A condition for M1003 to split the sentence into an Array.
					if (i.getID().equals("M1003")) {
						// This is to prevent the other half of the sentence is not display in another line.
						String[] splitArray = LT.displayCitation().split("\n");
						for (String s: splitArray) {
							s = splitArray[0] + " " + splitArray[1];
							output += String.format("%-7s %-15s %-49s %s\n", i.getID(), i.getName(), i.getCategory(), s);
							break;
						}
					} else {
						output += String.format("%-7s %-15s %-49s %s\n", i.getID(), i.getName(), i.getCategory(), LT.displayCitation());
					}
				}
		}
		System.out.println(output);
	}

	private void viewByCategory() {
		
		String searchCate = Helper.readString("Enter category to view > ");
		String output = "";
		String formatDate = "";
		// A condition when the input is not Ordinary or Lifetime or Student.
		if (searchCate.equalsIgnoreCase("Ordinary") || searchCate.equalsIgnoreCase("Lifetime") || searchCate.equalsIgnoreCase("Student")) {
			Helper.line(100, "=");
			System.out.println("Category: " + searchCate.toUpperCase());
			Helper.line(100, "=");
			if (searchCate.equalsIgnoreCase("Ordinary")) {
				output += String.format("%-7s %-17s %s\n", "ID", "Name", "MemberUntil");
				for (Member i: memberList) {
					if (i instanceof Ordinary) {
						// Format data from yyyy-MM-dd to dd MMMM yyyy to make the date appear neater.
						Ordinary o = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(o.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-7s %-17s %s\n", i.getID(), i.getName(), formatDate);
						
					}	
				}		
			} else if (searchCate.equalsIgnoreCase("Lifetime")) {
				output += String.format("%-7s %-15s %s\n", "ID", "Name", "Citation");
				for (Member i: memberList) {
					if (i instanceof Lifetime) {					
						Lifetime LT = (Lifetime)i;
						if (i.getID().equals("M1003")) {
							String[] splitArray = LT.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-7s %-15s %-49s\n", i.getID(), i.getName(), s);
								break;	
							}
						} else {
							output += String.format("%-7s %-15s %-49s\n", i.getID(), i.getName(), LT.displayCitation());
						}
					}	
				}
			} else if (searchCate.equalsIgnoreCase("Student")) {
				output += String.format("%-7s %-17s %s\n", "ID", "Name", "School");
				for (Member i: memberList) {
					if (i instanceof Student) {				
						Student stu = (Student)i;
						output += String.format("%-7s %-17s %s\n", i.getID(), i.getName(), stu.getSchool());	
						
					}
				}				
			}
		} else {
			// Display a message when the input is not the same as the member types.
			System.out.println("Invalid Member Type.");
		}
		
		System.out.println(output);	
	}
	// Own enhancement
	private void viewByID() {

		String searchID = Helper.readString("\nEnter ID to view > ");
		String regEx = "[M][0-9]{4}";		
		boolean matchID = Pattern.matches(regEx, searchID);
		// This is to display members individually
		if (matchID == true) {
			Helper.line(100, "=");
			System.out.println("View Member");
			Helper.line(100, "=");
			for (Member i : memberList) {
				if (searchID.equals(i.getID())) { 
					System.out.println("ID: " + i.getID() + "\nName: " + i.getName() + "\nCategory: " + i.getCategory());
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String mem = ord.getMemberUntil().toString();
						LocalDate memberDate = LocalDate.parse(mem, format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						String formateMem = memberDate.format(format2);
						System.out.println("Member Expiry Date: " + formateMem);											
					} else if (i instanceof Student) {
						Student stu = (Student)i;
						System.out.println("School: " + stu.getSchool());										
					} else if (i instanceof Lifetime) {					
						Lifetime LT = (Lifetime)i;
						System.out.println("Citation: " + LT.displayCitation());									
					}
				} 
			}
		} else {
			// Display a message when input is not the same format as ID.
			System.out.println("Invalid ID Format.");	
		}
	}
	
	private static void Menu() {
		
		Helper.line(100, "=");
		System.out.println("SCS MEMBERSHIP APP");
		Helper.line(100, "=");
		System.out.println("  1. View ALL Members");
		System.out.println("  2. View Members by Category");
		System.out.println("  3. Search Member by ID");
		System.out.println("  4. Quit");
		
	}
}
