//Program by Ella Withington 
import java.util.HashMap;
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

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;
import javafx.scene.paint.Color;

public class PlaceOrderScene extends SceneBasic {
	private Button submitButton = new Button("Submit");
	private Button cancelButton = new Button("Cancel");
	private GridPane gridPane = new GridPane();
	private Label oldText = new Label("Stock #");
	private TextField oldField = new TextField();
	private Label newText = new Label("Quantity");
	private TextField newField = new TextField();
	private final int FONT_SIZE = 20;
    public PrintWriter outgoing;

	public PlaceOrderScene() {
        super("Ordering");
        //Creating Grid Pane 
		final int FONT_SIZE = 20;
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);  
        Label userLabel = new Label("Stock #");
        userLabel.setFont(new Font(FONT_SIZE));
        Label accountLabel = new Label("Description");
        accountLabel.setFont(new Font(FONT_SIZE));
        gridPane.add(userLabel, 0, 0);
        gridPane.add(accountLabel, 1, 0);
		
        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane);
        int WIDTH = 200;
        cancelButton.setMinWidth(WIDTH);
        submitButton.setMinWidth(WIDTH);
        root.getChildren().addAll(submitButton);
        root.getChildren().addAll(cancelButton);
        submitButton.setOnAction(e -> sendOrder());
        cancelButton.setOnAction(e -> SceneManager.setCustomerScene());
        

	}
	
    public void getInventory() {
        try{
        Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... INVENTORY");
            String message = "INVENTORY";
			outgoing.println(message);
			outgoing.flush();
            
            BufferedReader incoming;
            incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for inventory..."); // For debugging
	        String key = incoming.readLine();
            int row = 1; // Gridepane row index
            while (!key.equals("DONE")) {
                Label userLabel2 = new Label(key);
	        	userLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(userLabel2, 0, row);
	            // Add account type
		        String value = incoming.readLine();
				System.out.println(key);
				System.out.println(value);
	            Label accountLabel2 = new Label(value);
	            accountLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(accountLabel2, 1, row);
                key = incoming.readLine();
                row++;
				
            }
			row++;
			gridPane.add(oldText, 0, row);
			gridPane.add(oldField, 1, row);
			row++;
        gridPane.add(newText, 0,row);
        gridPane.add(newField, 1, row);
        }
        
    catch (Exception e) {
            System.out.println("Error:  " + e);
        }
        
        
        
  
}

	public void sendOrder() {
		try {
			String stockNum = oldField.getText();
			String quantity = newField.getText();
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... Orders");
			outgoing.println("ORDERS");
			outgoing.flush();
			outgoing.println(stockNum);
			outgoing.println(quantity);
			outgoing.println("DONE");
			outgoing.flush();

		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
    
}
