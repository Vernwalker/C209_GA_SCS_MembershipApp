/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 28 Jul 2022 12:29:07 am
 */

package C209_GA;

/**
 * @author 21045050
 *
 */
import java.util.regex.Pattern;



import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SCS_addMember extends Application{
	
	private VBox addVB = new VBox();
	private HBox addHB = new HBox();
	private HBox hb = new HBox();
	
	private Text addNewMem = new Text("Add New Member");
	private Text displayError = new Text();
	private Text displayInvalid = new Text();
	
	private Button conSpecial = new Button("Continue");
	private Button add = new Button("Add");
	private Button clear = new Button("Clear Input");
	private Button clearS = new Button("Clear Input");
	
	private TextField addID = new TextField();
	private TextField addName = new TextField();
	private TextField addCategory = new TextField();
	private TextField addSpecial = new TextField();
	
	private Label displayisAdd = new Label();
	
	Scene addScene = new Scene(addVB);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage addMemStage) {
		// TODO Auto-generated method stub
		style();
		
		String jdbcURL = "jdbc:mysql://localhost/gademodb";
		String dbUsername = "root";
		String dbPassword = "";
		
		DBUtil.init(jdbcURL, dbUsername, dbPassword);
		
		addHB.setSpacing(20);
		addHB.getChildren().addAll(conSpecial, clear);
		addHB.setAlignment(Pos.CENTER);
		
		hb.setSpacing(20);
		hb.getChildren().addAll(add, clearS);
		hb.setAlignment(Pos.CENTER);
		
		addVB.setSpacing(20);
		addVB.setPadding(new Insets(20,20,20,20));
		addVB.getChildren().addAll(addNewMem, addID, addName, addCategory, addHB, displayInvalid);
		addVB.setAlignment(Pos.CENTER);

		addMemStage.setWidth(500);
		addMemStage.setHeight(600);
		addMemStage.setScene(addScene);
		addMemStage.setResizable(false);
		addMemStage.setFullScreen(false);
		addMemStage.show();

		EventHandler<ActionEvent> con = (ActionEvent e) -> ContinueAdd();
		conSpecial.setOnAction(con);

		EventHandler<ActionEvent> addM = (ActionEvent e) -> addMem();
		add.setOnAction(addM);
		
		EventHandler<ActionEvent> clearInput = (ActionEvent e) -> clearFields();
		clear.setOnAction(clearInput);
		
		EventHandler<ActionEvent> clearSpecial = (ActionEvent e) -> clearSpecialField();
		clearS.setOnAction(clearSpecial);
		
	}

	private void ContinueAdd() {
		
		boolean isEntered = false;
		try {
			if (isEntered == false) {
				if (addID.getText().isEmpty() == true && addName.getText().isEmpty() == true && addCategory.getText().isEmpty() == true) {
					displayInvalid.setText("Inputs are all empty");
			    } else if (addID.getText().isEmpty() == true && addName.getText().isEmpty() == true) {
			    	displayInvalid.setText("ID and Name are empty");
				} else if (addID.getText().isEmpty() == true && addCategory.getText().isEmpty() == true) {
					displayInvalid.setText("ID and Category are empty");
				} else if (addName.getText().isEmpty() == true && addCategory.getText().isEmpty() == true) {
					displayInvalid.setText("Name and Category are empty");
				} else if (addID.getText().isEmpty() == true) {
					displayInvalid.setText("ID is empty");
				} else if (addName.getText().isEmpty() == true) {
					displayInvalid.setText("Name is empty");
				} else if (addCategory.getText().isEmpty() == true) {
					displayInvalid.setText("Category is empty");
				} else {
					String regexID = "[M][1]{1}[0]{2}[0-9]{1,2}";
					boolean matchID = Pattern.matches(regexID, addID.getText());
						if (addCategory.getText().equals("Ordinary")) {
							addHB.getChildren().remove(clear);
							addVB.getChildren().remove(displayInvalid);
							addHB.setAlignment(Pos.CENTER);
							if (matchID == true) {
								addSpecial.setPromptText("Add MemberUntil");						
								addVB.getChildren().addAll(addSpecial, hb, displayError);
							} else {
								displayInvalid.setText("Invalid ID Format");
							}
						} else if (addCategory.getText().equals("Student")) {
							addHB.getChildren().remove(clear);
							addVB.getChildren().remove(displayInvalid);
							addHB.setAlignment(Pos.CENTER);
							if (matchID == true) {
								addSpecial.setPromptText("Add School");						
								addVB.getChildren().addAll(addSpecial, hb, displayError);
							} else {
								displayInvalid.setText("Invalid ID Format");
							}
						} else if (addCategory.getText().equals("Lifetime")) {
							addHB.getChildren().remove(clear);
							addVB.getChildren().remove(displayInvalid);
							addHB.setAlignment(Pos.CENTER);
							if (matchID == true) {
								addSpecial.setPromptText("Add Citation");
								addVB.getChildren().addAll(addSpecial, hb, displayError);
							} else {
								displayInvalid.setText("Invalid ID Format");
							}
						} else {
							displayInvalid.setText("Invalid Category");
						}
					} 
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
	}
	
	private void addMem() {
			if (addCategory.getText().equalsIgnoreCase("Ordinary")) {
				if (addSpecial.getText().isEmpty()) {
					displayError.setText("Input is empty");
				} else {
					String regexDate = "(202[0-9])\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
					boolean matchDate = Pattern.matches(regexDate, addSpecial.getText());
					if (matchDate == true) {
						String addSQL = "INSERT INTO member(ID, Name, Category, MemberUntil)" 
								+ "VALUES ('" + addID.getText() + "', '" + addName.getText() + "', "
								+ "'" + addCategory.getText() + "', '" + addSpecial.getText() + "')";
						int rows = DBUtil.execSQL(addSQL);
						if (rows == 1) {
							addVB.getChildren().remove(displayError);
							displayisAdd.setText("New Member, " + addName.getText() + " is added");
							addVB.getChildren().add(displayisAdd);
						} else {
							addVB.getChildren().remove(displayError);
							displayisAdd.setText("Member not added successfully");
							addVB.getChildren().add(displayisAdd);
						}
					} else {
						displayError.setText("Invalid Date Format");
					}
					
				}			
			} else if (addCategory.getText().equalsIgnoreCase("Student")) {
				if (addSpecial.getText().isEmpty()) {
					displayError.setText("Input is empty");
				} else {
					try {
						String isSchool = "SELECT School FROM school";
						ResultSet RS = DBUtil.getTable(isSchool);
						while (RS.next()) {
							if (addSpecial.getText().equals(RS.getString("School"))) {
								String schoolSQL = "INSERT INTO member(ID, Name, Category, School)"
										+ "VALUES ('" + addID.getText() + "', '" + addName.getText() + "', '" + addCategory.getText()
										+ "', '" + addSpecial.getText() + "')";
								int rows = DBUtil.execSQL(schoolSQL);
								if (rows == 1) {
									addVB.getChildren().remove(displayError);
									displayisAdd.setText("New Member, " + addName.getText() + " is added");
									addVB.getChildren().add(displayisAdd);
								} else {
									addVB.getChildren().remove(displayError);
									displayisAdd.setText("Member not added successfully");
									addVB.getChildren().add(displayisAdd);
								}
							} else {
								displayError.setText("Invalid School");
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}		
				}
			} else if (addCategory.getText().equalsIgnoreCase("Lifetime")) {
				if (addSpecial.getText().isEmpty()) {
					displayError.setText("Input is empty");
				} else {
					String addSQL = "INSERT INTO citation(ID, citation)" +
				"VALUES ('" + addID.getText() + "', '" + addSpecial.getText() + "')";
					String addCitation = "INSERT INTO member (ID, Name, Category)" + 
				"VALUES ('" + addID.getText() + "', '" + addName.getText() + "', '" + addCategory.getText() + "')";
					int rows = DBUtil.execSQL(addSQL);
					int rowMember = DBUtil.execSQL(addCitation);
					if (rows == 1 && rowMember == 1) {
						addVB.getChildren().remove(displayError);
						displayisAdd.setText("Member added successfully");
						addVB.getChildren().add(displayisAdd);
					} else {
						addVB.getChildren().remove(displayError);
						displayisAdd.setText("Member not added successfully");
						addVB.getChildren().add(displayisAdd);		
					}
				}
			}
		
	}
	
	private void clearFields() {
		addID.clear();
		addName.clear();
		addCategory.clear();
	}
	
	private void clearSpecialField() {
		addSpecial.clear();
	}
	
	private void style() {
		
		addID.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 400px; " +
                "-fx-max-height: 100px;");
		
		addName.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 400px; " +
                "-fx-max-height: 100px;");
		
		addCategory.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 400px; " +
                "-fx-max-height: 100px;");
		
		add.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		conSpecial.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 125px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 250px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
			
		clear.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 125px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 250px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		clearS.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		addSpecial.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 400px; " +
                "-fx-max-height: 100px;");
		
		addVB.setBackground(Background.fill(Color.BLACK));;
		addNewMem.setFill(Color.WHITE);
		addNewMem.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
		addID.setPromptText("Add ID");
		addName.setPromptText("Add Name");
		addCategory.setPromptText("Add Category");
		displayisAdd.setTextFill(Color.WHITE);
		displayisAdd.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		displayError.setFill(Color.WHITE);
		displayError.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		displayInvalid.setFill(Color.WHITE);
		displayInvalid.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		conSpecial.setTextFill(Color.BLACK);
		conSpecial.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		conSpecial.setBackground(Background.fill(Color.TEAL));
		clear.setTextFill(Color.BLACK);
		clear.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		clear.setBackground(Background.fill(Color.TEAL));
		add.setTextFill(Color.BLACK);
		add.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		add.setBackground(Background.fill(Color.TEAL));
		clearS.setTextFill(Color.BLACK);
		clearS.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		clearS.setBackground(Background.fill(Color.TEAL));
	}
}
