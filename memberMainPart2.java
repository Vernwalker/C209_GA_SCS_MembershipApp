package C209_GA;
/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 5 Jun 2022 12:56:13 am
 */

/**
 * @author 21045050
 *
 */

import java.util.ArrayList;


import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class memberMainPart2 {
	
	ArrayList<Member> memberList = new ArrayList<Member>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		memberMainPart2 M1 = new memberMainPart2();
		M1.start();
		
	}
	
private void start() {
		
		try {
			// Establish the database URL on browser.
			String connection = "jdbc:mysql://localhost/demodb";
			// Create a user.
			String user = "root";
			// A empty password.
			String password = "";
			
			DBUtil.init(connection, user, password);
			String sqlState = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID";
			ResultSet Rs = DBUtil.getTable(sqlState);
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
			
			while (option != 5) {
				
				Menu();
				option = Helper.readInt("\nEnter choice > ");
				
				if (option == 1) {
					viewAllMembers();
				} else if (option == 2) {
					viewByCategory();
				} else if (option == 3) {
					addMember();
				} else if (option == 4) {
					viewByID();
				} else if (option == 5) {
					// close the database connection.
					DBUtil.close();
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
	
	public void addMember()  {
		
		try {
			String newID = Helper.readString("Enter new member ID > ");
			String newName = Helper.readString("Enter new member's name > ");
			String whatCate = Helper.readString("Enter which category the new member will be at > ");
			if (whatCate.equalsIgnoreCase("Ordinary")) {
				String exDate = Helper.readString("Enter member expiry date > ");
				java.util.Date formatDate = new SimpleDateFormat("yyyy-mm-dd").parse(exDate);
				java.sql.Date sqlDateFormat = new java.sql.Date(formatDate.getTime());
				String addOrd = "INSERT INTO member(ID, Name, Category, MemberUntil)" + 
				"VALUES ('" + newID + "', '" + newName + "', '" + whatCate + "', '" + sqlDateFormat + "')";
				int addRows = DBUtil.execSQL(addOrd);
				if (addRows == 1) {
					System.out.println("New Member has been added");
					memberList.add(new Ordinary(newID, newName, whatCate, sqlDateFormat));
				} else {
					System.out.println("New Member is not added");
				}	
			} else if (whatCate.equalsIgnoreCase("Student")) {
				String whatSchool = Helper.readString("Enter the member's school > ");
				String addStudent = "INSERT INTO member(ID, Name, Category, School)" +
				"VALUE ('" + newID + "', '" + newName + "', '" + whatCate + "', '" + whatSchool + "')";
				int addRows = DBUtil.execSQL(addStudent);
				if (addRows == 1) {
					System.out.println("New Member has been added");
					memberList.add(new Student(newID, newName, whatCate, whatSchool));
				} else {
					System.out.println("New Member is not added");
				}
			} else if (whatCate.equalsIgnoreCase("Lifetime")) {
				String citation = Helper.readString("Enter the citation > ");
				String addLife = "INSERT INTO member(ID, Name, Category)" + 
				"VALUE ('" + newID + "', '" + newName + "', '" + whatCate + "')";
				String addCit = "INSERT INTO citation(ID, Citation)" + 
				"VALUE ('" + newID + "', '" + citation + "')";
				int addRows = DBUtil.execSQL(addLife);
				int addRowsCit = DBUtil.execSQL(addCit);
				if (addRows == 1 && addRowsCit == 1) {
					System.out.println("New Member has been added");
					memberList.add(new Lifetime(newID, newName, whatCate, citation));
				} else {
					System.out.println("New Member is not added");
				}
			} else {
				System.out.println("Invalid cateogry.");
			}
			
		} catch (ParseException pe) {
			pe.printStackTrace();
		} 
			
	}
	
	private static void Menu() {
		
		Helper.line(100, "=");
		System.out.println("SCS MEMBERSHIP APP");
		Helper.line(100, "=");
		System.out.println("  1. View ALL Members");
		System.out.println("  2. View Members by Category");
		System.out.println("  3. Add new Member");
		System.out.println("  4. Search Member by ID");
		System.out.println("  5. Quit");
		
	}
}
