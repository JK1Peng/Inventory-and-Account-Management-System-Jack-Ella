	// Author: Ella Withington
	// Creates ServerSocket for JUnit test for getOrder method in StoreThread.
	import java.io.BufferedReader;
	import java.net.ServerSocket;
	import java.io.InputStreamReader;
	import java.net.Socket;
	import java.util.*;

	public class Test_getOrderHelper {
	    public static void main(String[] args) {
			
			int LISTENING_PORT = 32007;
			ServerSocket listener;  // Listens for incoming connections.
	        Socket client;      // For communication with the connecting program.
	        try {
		        listener = new ServerSocket(LISTENING_PORT);
	            System.out.println("Listening on port " + LISTENING_PORT);
	           	client = listener.accept(); 
	           	BufferedReader incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    		
	    		StoreThread.getOrder(incoming);
	    		client.close();
	    		listener.close();
	        }
	        catch (Exception e) {
	            System.out.println("Error:  " + e);
	            return;
	        }
	    }
	}