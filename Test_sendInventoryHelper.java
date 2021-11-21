
	// Author: Ella Withington
	// Creates ServerSocket for JUnit test for sendAccountList method in StoreThread.
	import java.io.PrintWriter;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.util.*;

	public class Test_sendInventoryHelper {
	    public static void main(String[] args) {
			
			int LISTENING_PORT = 32007;
			ServerSocket listener;  // Listens for incoming connections.
	        Socket client;      // For communication with the connecting program.
	        try {
		        listener = new ServerSocket(LISTENING_PORT);
	            System.out.println("Listening on port " + LISTENING_PORT);
	           	client = listener.accept(); 
	    		PrintWriter outgoing = new PrintWriter(client.getOutputStream());
	    		
	    		StoreThread.sendInventory(outgoing);
	    		client.close();
	    		listener.close();
	        }
	        catch (Exception e) {
	            System.out.println("Error:  " + e);
	            return;
	        }
	    }
	}

