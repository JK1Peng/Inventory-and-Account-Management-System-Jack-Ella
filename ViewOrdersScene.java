import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ViewOrdersScene extends SceneBasic {
	
	Button returnButton = new Button("Return to menu");
	Label profile = new Label("temp");
	Label profileTop = new Label("Stock# Description Quantity");
	Label stock = new Label("");
	Label descriptionT = new Label("");
	Label quantityT = new Label("");
	private GridPane gridPane = new GridPane();
	
	public ViewOrdersScene() {

		super("Your Orders");
		final int FONT_SIZE = 20;
		int WIDTH = 200;
		gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5); 
        gridPane.setAlignment(Pos.CENTER);
		profileTop.setFont(new Font(FONT_SIZE));
        profile.setAlignment(Pos.CENTER);
		root.getChildren().addAll(profileTop);
		root.getChildren().addAll(gridPane);
        returnButton.setMinWidth(WIDTH);
        root.getChildren().addAll(returnButton);
        returnButton.setOnAction(e -> SceneManager.setCustomerScene());
	}
	
	public void getOrders() {
		int count = 0;
		try {
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... orders");
			outgoing.println("ORDERS");
			outgoing.flush();


	        BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for orders..."); // For debugging
	        String nextL = incoming.readLine();
	        while(!nextL.equals("DONE")) {
	        	String stocknumber = nextL;
	        	String description = incoming.readLine();
	        	String quantity = incoming.readLine();
	        	stock = new Label(stocknumber);
	        	descriptionT = new Label(description);
	        	quantityT = new Label(quantity);
	        	stock.setFont(new Font(20));
	        	descriptionT.setFont(new Font(20));
	        	quantityT.setFont(new Font(20));
				gridPane.add(stock, 0, count);
				gridPane.add(descriptionT, 1, count);
				gridPane.add(quantityT, 2, count);
				nextL = incoming.readLine();
				count++;
	        }
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
        
	}
	
	
	}




