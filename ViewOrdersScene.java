import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ViewOrdersScene extends SceneBasic {
	
	Button returnButton = new Button("Return to menu");
	Label profile = new Label("temp");
	Label profileTop = new Label("Stock# Description Quantity");
	
	public ViewOrdersScene() {
		super("Your Orders");
		final int FONT_SIZE = 20;
		profileTop.setFont(new Font(FONT_SIZE));
		root.getChildren().addAll(profileTop);
        profile.setFont(new Font(FONT_SIZE));
        profile.setAlignment(Pos.CENTER);
        root.getChildren().addAll(profile);
		int WIDTH = 200;
        returnButton.setMinWidth(WIDTH);
        root.getChildren().addAll(returnButton);
        returnButton.setOnAction(e -> SceneManager.setCustomerScene());
	}
	
	public void getOrders() {
		try {
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... orders");
			outgoing.println("ORDERS");
			outgoing.flush();


	        BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for orders..."); // For debugging
	        String orderText = incoming.readLine();
	        profile.setText(orderText);

		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
	}




