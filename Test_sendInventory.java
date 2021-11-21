import static org.junit.jupiter.api.Assertions.*;
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * @author ellawithington
 *
 */
class Test_sendInventory {
	private static int LISTENING_PORT = 32007;
    private static BufferedReader incoming;
    private static PrintWriter outgoing;   // Stream for sending data.
    private static Socket client;

	// Setup socket and streams for all tests
	@BeforeEach
	void setUp() throws Exception {
		client = new Socket("localhost", LISTENING_PORT);
        incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outgoing = new PrintWriter( client.getOutputStream() );
	}
	 
	// Main test for sendAccountList: Expects usernames and account typess
	@Test
	void test() {
		String key = "";
		String value = "";
		try {
			outgoing.println("INVENTORY");
			outgoing.flush();
	        System.out.println("Waiting for inventory...");
	        key = incoming.readLine();	
	        value = incoming.readLine();
	       
	        }

        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
		assert key.equals("45");
		assert value.equals("box");
        
	}

	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}