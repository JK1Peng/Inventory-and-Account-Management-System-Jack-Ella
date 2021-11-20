import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane; 
import javafx.geometry.Insets; 
import java.util.*;
import javafx.scene.paint.Color;
import java.net.*;
import java.io.*;

public class LoginScene extends SceneBasic {
	private Label userText = new Label("Username");
//	private TextField userField = new TextField();
	private TextField userField = new TextField("admin"); // FOR TESTING
	private Label passText = new Label("Password");
//	private PasswordField passField = new PasswordField();
	private TextField passField = new TextField("password"); // FOR TESTING
	private Button loginButton = new Button("Login");
	private Button settingsButton = new Button("Settings");
	private Button chatButton = new Button("Chat");
	private Label errorMessage = new Label();
	private Socket connection;
//	private String hostName = "localhost";
	private String hostName = "127.0.0.1";
	private int LISTENING_PORT = 32007;

	public LoginScene() {
        super("Login Menu");
        //Creating Grid Pane 
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        gridPane.add(userText, 0, 0);
        gridPane.add(userField, 1, 0);
        gridPane.add(passText, 0,1);
        gridPane.add(passField, 1, 1);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(loginButton, settingsButton,chatButton);
        gridPane.add(buttonBox, 1, 2);
        errorMessage.setTextFill(Color.RED);
        gridPane.add(errorMessage, 1, 3);
        gridPane.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(gridPane);
		loginButton.setOnAction(e -> login());
		settingsButton.setOnAction(e -> SceneManager.setSettingsScene());
	}
	
	private void login() {
		try {
			connection = new Socket(hostName, LISTENING_PORT);
			SceneManager.setSocket(connection); // Client socket
		}
	    catch (Exception e) {
	        System.out.println("Error:  " + e);
	    }
		String username = userField.getText();
		String password = passField.getText();
        try {
        	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			outgoing.println("LOGIN"); // Tell server we're going to log in
			outgoing.println(username);
			outgoing.println(password);
			outgoing.flush();  // Make sure the data is actually sent!
            System.out.println("Sent login info"); // For debugging
//            outgoing.close();

            BufferedReader incoming = new BufferedReader( 
                    new InputStreamReader(connection.getInputStream()) );
            System.out.println("Waiting for account type...");
            String reply = incoming.readLine();
//            incoming.close();
            if (reply.equals("ADMIN")) {
            	errorMessage.setText("");
            	SceneManager.setAdminScene();
            }
            else if (reply.equals("CUSTOMER")) {
            	errorMessage.setText("");
            	SceneManager.setCustomerScene();
            }
            else
            	errorMessage.setText(reply);
        }
        catch (Exception e) {
        	errorMessage.setText("Error trying to connect to server.");
            System.out.println("Error:  " + e);
        }
	}
}
