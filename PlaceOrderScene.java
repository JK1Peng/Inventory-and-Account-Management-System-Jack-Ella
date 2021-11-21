
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
import javafx.scene.paint.Color;

public class PlaceOrderScene extends SceneBasic {
	private Button submit = new Button("Submit");
	private Button cancel = new Button("Cancel");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 20;

	public PlaceOrderScene() {
        super("Ordering");
        //Creating Grid Pane 
		final int FONT_SIZE = 20;
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);  
        Label userLabel = new Label("Username");
        userLabel.setFont(new Font(FONT_SIZE));
        Label accountLabel = new Label("Account");
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
        submitButton.setOnAction(e -> sendOrder);
        cancelButton.setOnAction(e -> SceneManager.setCustomerScene());
	}
	
    
    public static HashMap<String,String> getInventory(){
        HashMap<String, String> data = new HashMap<String, String>();
        
        //read inventory
	System.out.println(filename);
    File dataFile = new File(filename);
    if ( ! dataFile.exists() ) {
        System.out.println("No data file found.");
        System.exit(1);
    }
    try( Scanner scanner = new Scanner(dataFile) ) {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            int separatorPosition = input.indexOf('<');
            int separatorPosition2 = input.indexOf('>');
            input = input.substring(separatorPosition + 1, separatorPosition2);
            if(input.equals("stockNumber")){
                String value = scanner.nextLine();
                value = value.trim();
                data.put("stockNumber", value);
            }
            if(input.equals("description")){
                String value = scanner.nextLine();
                value = value.trim();
                data.put("description", value);
            }
        }
    }
    catch (IOException e) {
        System.out.println("Error in data file.");
        System.exit(1);
    }
    for (String key : data.keySet()) {
        System.out.print("Key = " + key + ", ");
        System.out.println("Data = " + data.get(key));
        }
    return data;
}
}


	public void sendOrder() {
		try {
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... INVENTORY");
			outgoing.println("ACCOUNT_LIST");
			outgoing.flush();
//			outgoing.close(); // CAUSES SOCKET TO CLOSE?

	        BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for account list...");
	        String username = incoming.readLine();
	        int row = 1; // Gridepane row index
	        while (!username.equals("DONE")) {
	        	// Add username
	        	Label userLabel2 = new Label(username);
	        	userLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(userLabel2, 0, row);

	            // Add account type
		        String type = incoming.readLine();
	            Label accountLabel2 = new Label(type);
	            accountLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(accountLabel2, 1, row);

	            // Start reading next account
		        System.out.println("Received " + username + ", " + type); // For debugging
		        row++;
	            username = incoming.readLine();
                SceneManager.setCustomerScene();
	        }
//	        incoming.close();
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
    
}

