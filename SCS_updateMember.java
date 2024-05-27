/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 28 Jul 2022 12:38:29 am
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author 21045050
 *
 */
public class SCS_updateMember extends Application{

	/**
	 * @param args
	 */
	
	private VBox updateVB = new VBox();
	private HBox updateHB = new HBox();
	private HBox updateHB2 = new HBox();
	private Text updateMember = new Text("Update Member");
	
	private Button con = new Button("Continue");
	private Button update = new Button("Update");
	private Button clearField = new Button("Clear Input");
	private Button clearField2 = new Button("Clear Input");
	
	private TextField updateSpecial = new TextField();
	private TextField askID = new TextField();
	private TextField askCategory = new TextField();
	
	private Label displayisupdated = new Label();
	private Label displayError = new Label();
	
	Scene updateScene = new Scene(updateVB);
	
	ArrayList<Member> memberList = new ArrayList<Member>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage updateMemStage) {
		// TODO Auto-generated method stub
		
		updateHB.setSpacing(20);
		updateHB.getChildren().addAll(con, clearField);
		updateHB.setAlignment(Pos.CENTER);
		
		updateHB2.setSpacing(20);
		updateHB2.getChildren().addAll(update, clearField2);
		updateHB2.setAlignment(Pos.CENTER);
			
		updateVB.setSpacing(20);
		updateVB.setPadding(new Insets(20,20,20,20));
		updateVB.getChildren().addAll(updateMember, askID, updateHB, displayError);
		updateVB.setAlignment(Pos.TOP_CENTER);
		
		updateMemStage.setWidth(400);
		updateMemStage.setHeight(500);
		updateMemStage.setScene(updateScene);
		updateMemStage.setResizable(false);
		updateMemStage.setFullScreen(false);
		updateMemStage.show();
		
		EventHandler<ActionEvent> contin = (ActionEvent e) -> con();
		con.setOnAction(contin);
		
		EventHandler<ActionEvent> clearInput = (ActionEvent e) -> clearInputField();
		clearField.setOnAction(clearInput);
		
		EventHandler<ActionEvent> clearInput2 = (ActionEvent e) -> clearInputField2();
		clearField2.setOnAction(clearInput2);
		
		EventHandler<ActionEvent> up = (ActionEvent e) -> updateMember();
		update.setOnAction(up);
		
		style();
		
		String jdbcURL = "jdbc:mysql://localhost/demodb";
		String dbUsername = "root";
		String dbPassword = "";
		
		DBUtil.init(jdbcURL, dbUsername, dbPassword);
	}
	
	public void con() {
		
		boolean isTrue = false;
		try {
			String sql = "SELECT ID, Category FROM member WHERE ID='" + askID.getText() + "'";
			ResultSet RS = DBUtil.getTable(sql);
			if (isTrue == false) {
				if (askID.getText().isEmpty() == true) {
					displayError.setText("ID are empty");
				} else {
					String regexID = "[M][1]{1}[0]{2}[0-9]{1,2}";
					boolean matchID = Pattern.matches(regexID, askID.getText());
					if (matchID == true) {
						while (RS.next()) {
							updateHB.getChildren().removeAll(clearField);
							updateVB.getChildren().remove(displayError);
							if (RS.getString("Category").equals("Ordinary")) {
								updateSpecial.setPromptText("Enter new Date");
								updateVB.getChildren().addAll(updateSpecial, updateHB2, displayisupdated);
							} else if (RS.getString("Category").equals("Student")) {
								updateSpecial.setPromptText("Enter new School");
								updateVB.getChildren().addAll(updateSpecial, updateHB2, displayisupdated);
							} else if (RS.getString("Category").equals("Lifetime")) {
								updateSpecial.setPromptText("Enter new Citation");
								updateVB.getChildren().addAll(updateSpecial, updateHB2, displayisupdated);
							}	 
						}
					} else {
						displayError.setText("Invalid ID Format");
					}						  				
				} 
			}
			DBUtil.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearInputField() {
		askID.clear();
		askCategory.clear();
	}
	
	private void clearInputField2() {
		updateSpecial.clear();
	}
	
	public void updateMember() {
		boolean isTrue = false;
		boolean isHere = false;
		try {
			if (isTrue == false) {
				String sql = "SELECT Category FROM member WHERE ID='" + askID.getText() + "'";
				ResultSet RS = DBUtil.getTable(sql);
				if (updateSpecial.getText().isEmpty() == true ) {
					displayisupdated.setText("Input is empty");
				} else {
					while (RS.next()) {
						if (RS.getString("Category").equals("Ordinary")) {
							String regexDate = "(202[0-9])\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
							boolean matchDate = Pattern.matches(regexDate, updateSpecial.getText());
							if (matchDate == true) {
								DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								LocalDate memberDate = LocalDate.parse(updateSpecial.getText(), format);	
								String updateSQLDate = "UPDATE member SET MemberUntil='" + memberDate + 
									"' WHERE ID='"+ askID.getText() + "'";	
								int rows = DBUtil.execSQL(updateSQLDate);
								if (rows == 1) {
									displayisupdated.setText(askID.getText() + " is updated successfully");
									isHere = true;
								} else {
									displayisupdated.setText(askID.getText() + " is not updated successfully");	
								}
							} else {
								if (isHere == false) {
									displayisupdated.setText("Invalid Date Format");
								}
							}
						} else if (RS.getString("Category").equals("Student")) {	
							try {
								String SchoolSQL = "SELECT School FROM school";
								ResultSet Rs = DBUtil.getTable(SchoolSQL);
								while (Rs.next()) {
									if (Rs.getString("School").equals(updateSpecial.getText())) {
										String updateSQLSchool = "UPDATE member SET School='" + updateSpecial.getText() + 
												"' WHERE ID='"+ askID.getText() + "'";	
										int rowSchool = DBUtil.execSQL(updateSQLSchool);
										if (rowSchool == 1) {
											displayisupdated.setText(askID.getText() + " is updated successfully");
											isHere = true;
										} else {
											displayisupdated.setText(askID.getText() + " is not updated successfully");	
										}
									} else {
										if (isHere == false) {
											displayisupdated.setText("No such School");
										}
										
									}
								}
								DBUtil.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else if (RS.getString("Category").equals("Lifetime")) {
							String updateSQLCitation = "UPDATE citation SET citation='" + updateSpecial.getText() +
								"' WHERE ID='" + askID.getText() + "'";
							int rowCitation = DBUtil.execSQL(updateSQLCitation);
							if (rowCitation == 1) {
								displayisupdated.setText(askID.getText() + " is updated successfully");	
							} else {
								displayisupdated.setText(askID.getText() + " is not updated successfully");
							}
						}
					}
			    }
			}	
			DBUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
		
	private void style() {
		
		con.setStyle(" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
	            "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		update.setStyle(" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
	            "-fx-min-height: 40px; " +
	            "-fx-max-width: 100px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		clearField.setStyle(" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
	            "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		clearField2.setStyle(" -fx-background-radius: 2em;" +
				"-fx-min-width: 30px; " +
	            "-fx-min-height: 40px; " +
	            "-fx-max-width: 200px; " +
	            "-fx-max-height: 100px;" +
	            "-fx-background-color: TEAL");
		
		updateSpecial.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 60px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 300px; " +
                "-fx-max-height: 100px;");
		
		askID.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 60px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 300px; " +
                "-fx-max-height: 100px;");
		
		askCategory.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 60px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 300px; " +
                "-fx-max-height: 100px;");	
		
		askID.setPromptText("Enter ID");
		askCategory.setPromptText("Enter Category");
		updateMember.setFill(Color.WHITE);
		updateMember.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
		updateVB.setBackground(Background.fill(Color.BLACK));
		update.setTextFill(Color.BLACK);
		update.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		update.setBackground(Background.fill(Color.TEAL));
		con.setTextFill(Color.BLACK);
		con.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		con.setBackground(Background.fill(Color.TEAL));
		clearField.setTextFill(Color.BLACK);
		clearField.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		clearField.setBackground(Background.fill(Color.TEAL));
		clearField2.setTextFill(Color.BLACK);
		clearField2.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		clearField2.setBackground(Background.fill(Color.TEAL));
		displayisupdated.setTextFill(Color.WHITE);
		displayisupdated.setFont(Font.font("verdana", 20));
		displayError.setTextFill(Color.WHITE);
		displayError.setFont(Font.font("verdana", 20));
	}
	
          
	
}
