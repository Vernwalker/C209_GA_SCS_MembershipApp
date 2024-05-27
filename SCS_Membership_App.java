/**
 * I declare that this code was written by me.


 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 18 Jul 2022 10:46:00 am
 */

package C209_GA;




import java.util.regex.Pattern;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * @author 21045050
 *
 */
public class SCS_Membership_App extends Application {
	
	private HBox hb = new HBox();
	private HBox hb2 = new HBox();
	private HBox hb3 = new HBox();
	private VBox vb = new VBox();

	private Text welcome = new Text("Welcome to SCS Membership");
	private Text displayError = new Text();

	private Button viewMem = new Button("View All Members");
	private Button searchButton = new Button("    Search    ");
	private Button addMem = new Button("   Add Member   ");
	private Button deleteMem = new Button(" Delete Member");
	private Button updateMem = new Button("Update Member");

	private MenuItem earlyDate = new MenuItem("Earliest Date");
	private MenuItem lateDate = new MenuItem("Latest Date");
	private MenuItem sortName = new MenuItem("Name: A-Z");
	private MenuItem sortName2 = new MenuItem("Name: Z-A");
	private MenuItem sortID = new MenuItem("ID: Smallest to Largest");
	private MenuItem sortID2 = new MenuItem("ID: Largest to Smallest");
	private MenuItem viewOrd = new MenuItem("View Ordinary");
	private MenuItem viewStud = new MenuItem("View Student");
	private MenuItem viewLife = new MenuItem("View Lifetime");
	private MenuButton filter = new MenuButton("Sort By", null, earlyDate, lateDate,
			sortName, sortName2, sortID, sortID2);
	private MenuButton viewCate = new MenuButton("View By Category", null, 
			viewOrd, viewStud, viewLife);

	private TextArea ta = new TextArea();
	private TextField searchBar = new TextField();
	
	Scene mainScene = new Scene(vb);

	private ArrayList<Member> memberList = new ArrayList<Member>();
	
	private ArrayList<Member> nameList = new ArrayList<Member>();
	
	private ArrayList<Member> nameListZ = new ArrayList<Member>();
	
	private ArrayList<Member> dateList = new ArrayList<Member>();
	
	private ArrayList<Member> latedateList = new ArrayList<Member>();
	
	private ArrayList<Member> idList = new ArrayList<Member>();
	
	private ArrayList<Member> idList2 = new ArrayList<Member>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public void start(Stage primaryStage) {
		
		style();

		ta.setEditable(false);
		ta.setPrefHeight(500);
		vb.setPadding(new Insets(20,20,20,20));
		
		hb3.setSpacing(10);
		hb3.setAlignment(Pos.CENTER);
		hb3.getChildren().add(welcome);

		hb2.setSpacing(10);
		hb2.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(searchBar, searchButton, displayError);

		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(viewMem, viewCate, filter);

		vb.setSpacing(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(hb3, hb2, hb, ta, addMem, updateMem, deleteMem);

	    primaryStage.setTitle("SCS Membership App");
		primaryStage.setResizable(false);
		primaryStage.setWidth(1200);
		primaryStage.setHeight(800);
		primaryStage.setScene(mainScene);
		primaryStage.show();

		EventHandler<ActionEvent> allMem = (ActionEvent e) -> viewAllMembers();
		viewMem.setOnAction(allMem);

		EventHandler<ActionEvent> ord = (ActionEvent e) -> viewOrdinary();
		viewOrd.setOnAction(ord);

		EventHandler<ActionEvent> stud = (ActionEvent e) -> viewStudent();
		viewStud.setOnAction(stud);

		EventHandler<ActionEvent> life = (ActionEvent e) -> viewLifetime();
		viewLife.setOnAction(life);

		EventHandler<ActionEvent> search = (ActionEvent e) -> searchMemberID();
		searchButton.setOnAction(search);

		EventHandler<ActionEvent> ed = (ActionEvent e) -> EarliestDate();
		earlyDate.setOnAction(ed);
		
		EventHandler<ActionEvent> ld = (ActionEvent e) -> LatestDate();
		lateDate.setOnAction(ld);
		
		EventHandler<ActionEvent> nameA = (ActionEvent e) -> NameAZ();
		sortName.setOnAction(nameA);
		
		EventHandler<ActionEvent> nameZ = (ActionEvent e) -> NameZA();
		sortName2.setOnAction(nameZ);
		
		EventHandler<ActionEvent> ID = (ActionEvent e) -> ID1();
		sortID.setOnAction(ID);
		
		EventHandler<ActionEvent> ID2 = (ActionEvent e) -> ID2();
		sortID2.setOnAction(ID2);
		
		EventHandler<ActionEvent> add = (ActionEvent e) -> (new SCS_addMember()).start(new Stage());
		addMem.setOnAction(add);

		EventHandler<ActionEvent> update = (ActionEvent e) -> (new SCS_updateMember()).start(new Stage());
		updateMem.setOnAction(update);

		EventHandler<ActionEvent> delete = (ActionEvent e) -> (new SCS_deleteMember()).start(new Stage());
		deleteMem.setOnAction(delete);
		
		String jdbcURL = "jdbc:mysql://localhost/gademodb";
		String dbUsername = "root";
		String dbPassword = "";

		DBUtil.init(jdbcURL, dbUsername, dbPassword);
		loadMembers();		
	}
	
	private void loadMembers() {
		String sql = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID ORDER BY Category";
		ResultSet RS = DBUtil.getTable(sql);
		
		String sqlNameA = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID ORDER BY Name";
		ResultSet RSName = DBUtil.getTable(sqlNameA);
		
		String sqlNameZ = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID ORDER BY Name DESC";
		ResultSet RSNameZ = DBUtil.getTable(sqlNameZ);
		
		String sqlDate = "SELECT ID, Name, Category, MemberUntil FROM member ORDER BY MemberUntil";
		ResultSet RSDate = DBUtil.getTable(sqlDate);
		
		String sqlDate2 = "SELECT ID, Name, Category, MemberUntil FROM member ORDER BY MemberUntil DESC";
		ResultSet RSDate2 = DBUtil.getTable(sqlDate2);
		
		String sqlID = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID ORDER BY member.ID";
		ResultSet RSID = DBUtil.getTable(sqlID);
		
		String sqlID2 = "SELECT * FROM member LEFT JOIN citation ON member.ID = citation.ID ORDER BY member.ID DESC";
		ResultSet RSID2 = DBUtil.getTable(sqlID2);
		try {
			while (RS.next()) {
				if (RS.getString("Category").equals("Ordinary")) {
					memberList.add(new Ordinary(RS.getString("ID"), RS.getString("Name"), 
							RS.getString("Category"), RS.getDate("MemberUntil")));
				} else if (RS.getString("Category").equals("Lifetime")) {
					memberList.add(new Lifetime(RS.getString("ID"),RS.getString("Name"), 
							RS.getString("Category"), RS.getString("Citation")));
				} else if (RS.getString("Category").equals("Student")) { 
					memberList.add(new Student(RS.getString("ID"),RS.getString("Name"), 
							RS.getString("Category"), RS.getString("School")));
				}  
			}
			
			while (RSName.next()) {
				if (RSName.getString("Category").equals("Ordinary")) {
					nameList.add(new Ordinary(RSName.getString("ID"), RSName.getString("Name"), 
							RSName.getString("Category"), RSName.getDate("MemberUntil")));
				} else if (RSName.getString("Category").equals("Lifetime")) {
					nameList.add(new Lifetime(RSName.getString("ID"),RSName.getString("Name"), 
							RSName.getString("Category"), RSName.getString("Citation")));
				} else if (RSName.getString("Category").equals("Student")) { 
					nameList.add(new Student(RSName.getString("ID"),RSName.getString("Name"), 
							RSName.getString("Category"), RSName.getString("School")));
				} 
			}
			
			while (RSNameZ.next()) {
				if (RSNameZ.getString("Category").equals("Ordinary")) {
					nameListZ.add(new Ordinary(RSNameZ.getString("ID"), RSNameZ.getString("Name"), 
							RSNameZ.getString("Category"), RSNameZ.getDate("MemberUntil")));
				} else if (RSNameZ.getString("Category").equals("Lifetime")) {
					nameListZ.add(new Lifetime(RSNameZ.getString("ID"),RSNameZ.getString("Name"), 
							RSNameZ.getString("Category"), RSNameZ.getString("Citation")));
				} else if (RSNameZ.getString("Category").equals("Student")) { 
					nameListZ.add(new Student(RSNameZ.getString("ID"),RSNameZ.getString("Name"), 
							RSNameZ.getString("Category"), RSNameZ.getString("School")));
				} 
			}
			
			while (RSDate.next()) {
				if (RSDate.getString("Category").equals("Ordinary")) {
					dateList.add(new Ordinary(RSDate.getString("ID"), RSDate.getString("Name"), 
							RSDate.getString("Category"), RSDate.getDate("MemberUntil")));
				}
			}
			
			
			while (RSDate2.next()) {
				if (RSDate2.getString("Category").equals("Ordinary")) {
					latedateList.add(new Ordinary(RSDate2.getString("ID"), RSDate2.getString("Name"), 
							RSDate2.getString("Category"), RSDate2.getDate("MemberUntil")));
				} 
			}
			
			while (RSID.next()) {
				if (RSID.getString("Category").equals("Ordinary")) {
					idList.add(new Ordinary(RSID.getString("ID"), RSID.getString("Name"), 
							RSID.getString("Category"), RSID.getDate("MemberUntil")));
				} else if (RSID.getString("Category").equals("Lifetime")) {
					idList.add(new Lifetime(RSID.getString("ID"),RSID.getString("Name"), 
							RSID.getString("Category"), RSID.getString("Citation")));
				} else if (RSID.getString("Category").equals("Student")) { 
					idList.add(new Student(RSID.getString("ID"),RSID.getString("Name"), 
							RSID.getString("Category"), RSID.getString("School")));
				}
			}
			
			while (RSID2.next()) {
				if (RSID2.getString("Category").equals("Ordinary")) {
					idList2.add(new Ordinary(RSID2.getString("ID"), RSID2.getString("Name"), 
							RSID2.getString("Category"), RSID2.getDate("MemberUntil")));
				} else if (RSID2.getString("Category").equals("Lifetime")) {
					idList2.add(new Lifetime(RSID2.getString("ID"),RSID2.getString("Name"), 
							RSID2.getString("Category"), RSID2.getString("Citation")));
				} else if (RSID2.getString("Category").equals("Student")) { 
					idList2.add(new Student(RSID2.getString("ID"),RSID2.getString("Name"), 
							RSID2.getString("Category"), RSID2.getString("School")));
				}
			}
			
			RSID.last();
			RSDate.last();
			RSDate2.last();
			RSName.last();
			RSNameZ.last();
			RS.last();
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	private void viewAllMembers() {
		memberList.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %-25s %-23s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
			for (Member i : memberList) {
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-10s %-20s %-20s %s\n", 
								ord.getID(), ord.getName(), ord.getCategory(), formatDate);
					} else if (i instanceof Student) {
						Student stud = (Student)i;
						output += String.format("%-10s %-20s %-45s %s\n", 
								stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());	
					} else if (i instanceof Lifetime) {
						Lifetime life = (Lifetime)i;
						if (i.getID().equals("M1003")) {
							String[] splitArray = life.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), s);
								break;	
							}
						} else {
							output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), life.displayCitation());
						}
					}
				}
		ta.setText(output);	
	}

	private void viewOrdinary() {
		String output = "";
		String formatDate = "";
		output += String.format("%-10s %-20s %s\n", "ID", "Name", "MemberUntil");
		for (Member i: memberList) {
			if (i instanceof Ordinary) {
				// Format data from yyyy-MM-dd to dd MMMM yyyy to make the date appear neater.
				Ordinary o = (Ordinary)i;
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate memberDate = LocalDate.parse(o.getMemberUntil().toString(), format);
				DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
				formatDate = memberDate.format(format2);
				output += String.format("%-10s %-20s %s\n", o.getID(), o.getName(), formatDate);

			}	
		}
		ta.setText(output);
	}

	private void viewStudent() {
		String output = "";
		output += String.format("%-10s %-20s %s\n", "ID", "Name", "School");
		for (Member i: memberList) {
			if (i instanceof Student) {				
				Student stu = (Student)i;
				output += String.format("%-10s %-20s %s\n", stu.getID(), stu.getName(), stu.getSchool());	

			}
		}
		ta.setText(output);
	}

	private void viewLifetime() {
		String output = "";
		output += String.format("%-10s %-20s %s\n", "ID", "Name", "Citation");
		for (Member i: memberList) {
			if (i instanceof Lifetime) {					
				Lifetime LT = (Lifetime)i;
				if (i.getID().equals("M1003")) {
					String[] splitArray = LT.displayCitation().split("\n");
					for (String s: splitArray) {
						s = splitArray[0] + " " + splitArray[1];
						output += String.format("%-10s %-20s %s\n", LT.getID(), LT.getName(), s);
						break;	
					}
				} else {
					output += String.format("%-10s %-20s %s\n", LT.getID(), LT.getName(), LT.displayCitation());
				}
			}	
		}
		ta.setText(output);
	}

	private void searchMemberID() {
		String output = "";
		String formatDate = "";
		boolean isTrue = false;
		for (Member i : memberList) {
				if (searchBar.getText().isEmpty()) {
					displayError.setText("Please Enter an ID");
				} else {
					String regExID = "[M][1]{1}[0]{2}[0-9]{1,2}";
					boolean IDmatch = Pattern.matches(regExID, searchBar.getText());
					if (IDmatch == true) {
						try {
							String sql = "SELECT ID FROM member WHERE ID='" + i.getID() + "'";
							ResultSet RS = DBUtil.getTable(sql);
							while (RS.next()) {
								if (searchBar.getText().equals(RS.getString("ID"))) {
									hb2.getChildren().remove(displayError);
									isTrue = true;
									if (i instanceof Ordinary) {
										Ordinary ord = (Ordinary)i;
										DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
										LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
										DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
										formatDate = memberDate.format(format2);
										output += String.format("ID: %-10s\nName: %-25s\nCategory: %-20s\nMemberUntil: %s", 
											ord.getID(), ord.getName(), ord.getCategory(), formatDate);
										isTrue = true;
									} else if (i instanceof Student) {
										Student stud = (Student)i;
										output += String.format("ID: %-10s\nName: %-25s\nCategory: %-60s\nSchool: %s", 
											stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());
										isTrue = true;
									} else if (i instanceof Lifetime) {
										 Lifetime life = (Lifetime)i;
										 if (life.getID().equals("M1003")) {
										// This is to prevent the other half of the sentence is not display in another line.
											 String[] splitArray = life.displayCitation().split("\n");
											 for (String s: splitArray) {
												 s = splitArray[0] + " " + splitArray[1];
												 output += String.format("ID: %-10s\nName: %-25s\nCategory: %-5s\nCitation: %s", 
										        	life.getID(), life.getName(), life.getCategory(), s);
												 isTrue = true;
										         break;
										    }
										 } else {
										  output += String.format("ID: %-10s\nName: %-25s\nCategory: %-5s\nCitation: %s", 
										        life.getID(), life.getName(), life.getCategory(), life.displayCitation());
										  isTrue = true;
										 }
									}
								} 
							}
							DBUtil.close();
							if (isTrue == false) {
								displayError.setText("No Such ID exists");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						displayError.setText("Invalid ID Format");
					}
				}
		}
		ta.setText(output);
	}

	private void EarliestDate() {
		dateList.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %s\n", "ID", "Name", "Category", "MemberUntil");
		for (Member i : dateList) {
			if (i instanceof Ordinary) {
				Ordinary ord = (Ordinary)i;
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
				DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
				formatDate = memberDate.format(format2);
				output += String.format("%-10s %-20s %-20s %s\n", 
					ord.getID(), ord.getName(), ord.getCategory(), formatDate);

			}	
		}
	ta.setText(output);
	}
	
	private void LatestDate() {
		latedateList.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %s\n", "ID", "Name", "Category", "MemberUntil");
		for (Member i : latedateList) {
			if (i instanceof Ordinary) {
				Ordinary ord = (Ordinary)i;
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
				DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
				formatDate = memberDate.format(format2);
				output += String.format("%-10s %-20s %-20s %s\n", 
					ord.getID(), ord.getName(), ord.getCategory(), formatDate);

			}	
		}
	ta.setText(output);
	}
	
	private void NameAZ() {
		nameList.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %-25s %-23s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
			for (Member i : nameList) {
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-10s %-20s %-20s %s\n", 
								ord.getID(), ord.getName(), ord.getCategory(), formatDate);
					} else if (i instanceof Student) {
						Student stud = (Student)i;
						output += String.format("%-10s %-20s %-45s %s\n", 
								stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());	
					} else if (i instanceof Lifetime) {
						Lifetime life = (Lifetime)i;
						if (life.getID().equals("M1003")) {
							String[] splitArray = life.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), s);
								break;	
							}
						} else {
							output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), life.displayCitation());
						}
					}
				}
		ta.setText(output);	
	}
	
	private void NameZA() {
		nameListZ.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %-25s %-23s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
			for (Member i : nameListZ) {
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-10s %-20s %-20s %s\n", 
								ord.getID(), ord.getName(), ord.getCategory(), formatDate);
					} else if (i instanceof Student) {
						Student stud = (Student)i;
						output += String.format("%-10s %-20s %-45s %s\n", 
								stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());	
					} else if (i instanceof Lifetime) {
						Lifetime life = (Lifetime)i;
						if (life.getID().equals("M1003")) {
							String[] splitArray = life.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), s);
								break;	
							}
						} else {
				
							output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), life.displayCitation());
						}
					}
				}
		ta.setText(output);
	}
	
	private void ID1() {
		idList.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %-25s %-23s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
			for (Member i : idList) {
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-10s %-20s %-20s %s\n", 
								ord.getID(), ord.getName(), ord.getCategory(), formatDate);
					} else if (i instanceof Student) {
						Student stud = (Student)i;
						output += String.format("%-10s %-20s %-45s %s\n", 
								stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());	
					} else if (i instanceof Lifetime) {
						Lifetime life = (Lifetime)i;
						if (life.getID().equals("M1003")) {
							String[] splitArray = life.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), s);
								break;	
							}
						} else {
				
							output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), life.displayCitation());
						}
					}
				}
		ta.setText(output);
	}
	
	private void ID2() {
		idList2.clear();
		loadMembers();
		String formatDate = "";
		String output = String.format("%-10s %-20s %-20s %-25s %-23s %s\n", "ID", "Name", "Category", "MemberUntil", "School", "Citation");
			for (Member i : idList2) {
					if (i instanceof Ordinary) {
						Ordinary ord = (Ordinary)i;
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate memberDate = LocalDate.parse(ord.getMemberUntil().toString(), format);
						DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						formatDate = memberDate.format(format2);
						output += String.format("%-10s %-20s %-20s %s\n", 
								ord.getID(), ord.getName(), ord.getCategory(), formatDate);
					} else if (i instanceof Student) {
						Student stud = (Student)i;
						output += String.format("%-10s %-20s %-45s %s\n", 
								stud.getID(), stud.getName(), stud.getCategory(), stud.getSchool());	
					} else if (i instanceof Lifetime) {
						Lifetime life = (Lifetime)i;
						if (life.getID().equals("M1003")) {
							String[] splitArray = life.displayCitation().split("\n");
							for (String s: splitArray) {
								s = splitArray[0] + " " + splitArray[1];
								output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), s);
								break;	
							}
						} else {
				
							output += String.format("%-10s %-20s %-70s %s\n", life.getID(), life.getName(), life.getCategory(), life.displayCitation());
						}
					}
				}
		ta.setText(output);
	}
	
	private void style() {

		viewMem.setStyle(
			" -fx-background-radius: 2em;" +
			"-fx-min-width: 30px; " +
            "-fx-min-height: 40px; " +
            "-fx-max-width: 200px; " +
            "-fx-max-height: 100px;" +
            "-fx-background-color: TEAL");
       
		filter.setStyle(
				" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
			    "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
		        "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		viewCate.setStyle(
				" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
		        "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		searchBar.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 60px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;");
		
		searchButton.setStyle(" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
	            "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		addMem.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		updateMem.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		deleteMem.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		searchBar.setPromptText("Search Member ID");
		filter.setTextFill(Color.BLACK);
		filter.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		filter.setBackground(Background.fill(Color.TEAL));
		viewCate.setTextFill(Color.BLACK);
		viewCate.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		viewCate.setBackground(Background.fill(Color.TEAL));
		viewMem.setTextFill(Color.BLACK);
		viewMem.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		viewMem.setBackground(Background.fill(Color.TEAL));
		searchButton.setTextFill(Color.BLACK);
		searchButton.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		searchButton.setBackground(Background.fill(Color.TEAL));
		vb.setBackground(Background.fill(Color.BLACK));
		ta.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
		searchBar.setBackground(Background.fill(Color.GRAY));
		searchBar.setPrefSize(200, 30);
		welcome.setFill(Color.WHITE);
		welcome.setFont(Font.font("Comic Sans MS", 40));
		addMem.setTextFill(Color.BLACK);
		addMem.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		addMem.setBackground(Background.fill(Color.TEAL));
		updateMem.setTextFill(Color.BLACK);
		updateMem.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		updateMem.setBackground(Background.fill(Color.TEAL));
		deleteMem.setTextFill(Color.BLACK);
		deleteMem.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		deleteMem.setBackground(Background.fill(Color.TEAL));
		displayError.setFill(Color.WHITE);
		displayError.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		
	}

}
