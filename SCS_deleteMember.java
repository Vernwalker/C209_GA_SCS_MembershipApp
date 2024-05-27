/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * Vernon Ong, 29 Jul 2022 1:01:03 am
 */

package C209_GA;

/**
 * @author 21045050
 *
 */
import java.util.regex.Pattern;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SCS_deleteMember extends Application{

	/**
	 * @param args
	 */
	
	private VBox delVB = new VBox();
	private HBox delHB = new HBox();
	
	private Text del = new Text("Delete Member");
	private Button delete = new Button("Delete Member");
	private Button clearInput = new Button("Clear");
	private RadioButton confirm = new RadioButton("Confirm");
	private RadioButton notConfirm = new RadioButton("Not Confirm");
	private TextField ID = new TextField();
	
	private Label display = new Label();
	
	Scene delScene = new Scene(delVB);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage deleteStage) {
		// TODO Auto-generated method stub
		delVB.setSpacing(20);
		delVB.setPadding(new Insets(30,30,30,30));
		delVB.getChildren().addAll(del, ID, delete, clearInput, display);
		delVB.setAlignment(Pos.TOP_CENTER);
		
		deleteStage.setWidth(400);
		deleteStage.setHeight(500);
		deleteStage.setScene(delScene);
		deleteStage.setResizable(false);
		deleteStage.setFullScreen(false);
		deleteStage.show();
		
		EventHandler<ActionEvent> deleteMember = (ActionEvent e) -> delMem();
		delete.setOnAction(deleteMember);
		
		EventHandler<ActionEvent> confirmDelete = (ActionEvent e) -> confirmDel();
		confirm.setOnAction(confirmDelete);
		
		EventHandler<ActionEvent> notconfirmDelete = (ActionEvent e) -> notconfirmDel();
		notConfirm.setOnAction(notconfirmDelete);
		
		EventHandler<ActionEvent> clearText = (ActionEvent e) -> clearField();
		clearInput.setOnAction(clearText);
		
		style();
		
		String jdbcURL = "jdbc:mysql://localhost/gademodb";
		String dbUsername = "root";
		String dbPassword = "";
		
		DBUtil.init(jdbcURL, dbUsername, dbPassword);
		
	}
	
	private void delMem() {
		if (ID.getText().isEmpty() == true) {
			display.setText("Input is empty");
		} else {
			String regexID = "[M][1]{1}[0]{2}[0-9]{1,2}";
			boolean matchID = Pattern.matches(regexID, ID.getText());
			if (matchID == true) {
				delHB.setSpacing(20);
				delVB.getChildren().remove(display);
				delHB.getChildren().addAll(confirm, notConfirm);
				delHB.setAlignment(Pos.CENTER);
				delVB.getChildren().addAll(delHB);
			} else {
				display.setText("Invalid ID Format");
			}
		}	
	}
	
	private void clearField() {
		ID.clear();
	}
	private void notconfirmDel() {
		delHB.getChildren().removeAll(confirm, notConfirm);
		delVB.getChildren().remove(delHB);
	}

	private void confirmDel() {
		delVB.getChildren().remove(delHB);
		String deleteSQL = "DELETE FROM member WHERE ID='" + ID.getText() + "'";
		String deleteSQLC = "DELETE FROM citation WHERE ID='"+ ID.getText() + "'";
		int rows = DBUtil.execSQL(deleteSQL);
		int rowsC = DBUtil.execSQL(deleteSQLC);
		if (rows == 1) {
			display.setText(ID.getText() + " has been deleted");
			delVB.getChildren().add(display);
		} else if (rows == 1 && rowsC == 1){
			display.setText(ID.getText() + " has been deleted");
		} else {
			display.setText("Deletion not successful");
			delVB.getChildren().add(display);
		}
		DBUtil.close();
	}
	
	private void style() {
		
		ID.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 150px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 300px; " +
                "-fx-max-height: 100px;");
		
		delete.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		clearInput.setStyle("-fx-background-radius: 2em; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 200px; " +
                "-fx-max-height: 100px;" +
                "-fx-background-color: TEAL");
		
		ID.setPromptText("Enter Member ID");
		delVB.setBackground(Background.fill(Color.BLACK));
		del.setFill(Color.WHITE);
		del.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
		display.setTextFill(Color.WHITE);
		display.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		display.setTextFill(Color.WHITE);
		delete.setTextFill(Color.BLACK);
		delete.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		delete.setBackground(Background.fill(Color.TEAL));
		clearInput.setTextFill(Color.BLACK);
		clearInput.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		//clearInput.setBackground(Background.fill(Color.TEAL));
		confirm.setTextFill(Color.WHITE);
		confirm.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		notConfirm.setTextFill(Color.WHITE);
		notConfirm.setFont(Font.font("verdana", FontWeight.BOLD, 15));
	}
}
