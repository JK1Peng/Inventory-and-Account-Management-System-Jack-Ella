

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
class Test_getOrder {
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
		int id = 0;
		int stockNum = 0;
		int quantity = 0;
		
		try {
			outgoing.println("ORDERS");
			outgoing.flush();
			outgoing.println(1);
			outgoing.println(45);
			outgoing.println(3);
			outgoing.println("DONE");
			outgoing.flush();
			FileInputStream fileInputStream = new FileInputStream("Binary.data");
			InputStreamReader fileReader = new InputStreamReader(fileInputStream, "UTF-8");
	        
	        stockNum = fileReader.read();
	        id = fileReader.read();
	        quantity = fileReader.read();
	        System.out.println(id);
	        System.out.println(stockNum);
	        System.out.println(quantity);
	        
	        }

        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
		assert id == 1;
		assert stockNum == 45;
		assert quantity == 3;
        
	}

	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}