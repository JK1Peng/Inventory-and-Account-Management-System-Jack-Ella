//Author: Jack P
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StoreThread extends Thread {
	
	private Socket client;
	public HashMap<String,Account>accounts = new HashMap<String,Account>();
	public Account userAccount;
    private BufferedReader incoming;
    private PrintWriter outgoing; 
	
	public StoreThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
	        try {
	        		accounts = AccountsReader.readFile("accounts.xml");
	                System.out.println("Connection from " + client.getInetAddress().toString() );
	                incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
	                outgoing = new PrintWriter( client.getOutputStream() );
	                System.out.println("Waiting for request...");
	                String request = incoming.readLine();
	                System.out.println("Request: " + request);
	                while (!request.equals("QUIT")) {
	                	switch (request) {
	                		case "LOGIN":
	                			System.out.println("Login..."); // For debugging
	                            login(accounts, incoming, outgoing);
	                			break;
	                		case "ACCOUNT_LIST":
	                			System.out.println("Sending account list..."); // For debugging
	                			sendAccountList(outgoing);
	                			break;
	                		case "PROFILE":
	                			System.out.println("Sending profile..."); // For debugging
	                			sendProfile(outgoing);
	                			break;
	                		case "CHANGE_PASSWORD":
	                			System.out.println("Change password..."); // For debugging
	                			changePassword(userAccount, incoming, outgoing);
	                			break;
	                		case "INVENTORY":
	                			System.out.println("Sending Inventory..."); // For debugging
	                			sendInventory(outgoing);
                			 	break;
	                		case "ORDERS":
	                			System.out.println("Sending Orders..."); // For debugging
	                			getOrder(incoming);
                			 	break;
	                		
	                	}
	                    request = incoming.readLine();
	                    System.out.println("Request: " + request); // For debugging
	                }
	                System.out.println("Quitting"); // For debugging
	                client.close();

	        }
	        catch (Exception e) {
	            System.out.println("Sorry, the server has shut down.");
	            System.out.println("Error:  " + e);
	            return;
	        }

	}

	public void login(HashMap<String,Account>accounts,
			BufferedReader incoming, PrintWriter outgoing) {
			accounts  = AccountsReader.readFile("accounts.xml");
			boolean foundUser = false;
		 try {
	            String username = incoming.readLine();
	            String password = incoming.readLine();
	            System.out.println("Received: " + username + ", " + password);
	            Account account;
	            String reply = "";
	            String id ;
	    		for(int i = 0; i < 100;i++) { // assume biggest ID 100
	    			id = i+"";
	    			if(accounts.containsKey(id)) {			
	    				 if(accounts.get(id).getUsername().equals(username)) {
	    					 foundUser = true;
	    					 if(accounts.get(id).verifyPassword(password)) {
	    						 account = accounts.get(id);
	    						 if (account instanceof AdminAccount) {
	 	        	    			reply = "ADMIN";
	 	        	                System.out.println("Found admin account");
	 	    					}
	    						 else {
	 	        	    			reply = "CUSTOMER";
	 	        	                System.out.println("Found Customer account");
	 	    					}
	 	    					userAccount = account; // Set current user
	    					 }
	    					 else {
	 	    					reply = "ERROR: Invalid password";
	 	    				 }
	    				 }
	    			}
	    
		        }
	    		if (!foundUser) {
	    			reply = "ERROR: Invalid username";
    			}
	            System.out.println("Sending reply...");
	            outgoing.println(reply);
	            outgoing.flush();  // Make sure the data is actually sent!
	    	}
	    	
	        catch (Exception e){
	            System.out.println("Error: " + e);
	        }
	}
	
	public void sendAccountList(PrintWriter outgoing) {
		String id;
		for (int i = 0; i< 100; i++) {
			id = i+"";
			if(accounts.containsKey(id)) {
	        	outgoing.println(accounts.get(id).getUsername());
	        	if (accounts.get(id) instanceof AdminAccount)
	        		outgoing.println("Administrator");
	        	else
	        		outgoing.println("Customer");
			}
		}
        outgoing.println("DONE");
        outgoing.flush();
	}
	
	public void sendProfile(PrintWriter outgoing) {
		outgoing.println(((CustomerAccount) userAccount).getProfile());
    	outgoing.flush();
	}
	
	public void changePassword(Account userAccount,
			BufferedReader incoming, PrintWriter outgoing) {
		String id;
		try {
    		String oldPassword = incoming.readLine();
    		String newPassword = incoming.readLine();
    		System.out.println("Received: " + oldPassword + ", " + newPassword);
            String reply = "";
			if (userAccount.verifyPassword(oldPassword)) {
				userAccount.setPassword(newPassword);
				// Save new password
		        try {
		        	PrintWriter file = new PrintWriter("accounts.xml");
		        	file.println("<ACCOUNTS>");
		            for (int i = 0; i < 100; i++) {
		            	id = i+"";
		            	if(accounts.containsKey(id)) {
		            		if (accounts.get(id) instanceof AdminAccount) {
		            			file.println("   "+"<ADMINISTRATOR>");
		            			file.println("         "+"<id>"+id+"</id>");
		            			file.println("         "+"<Username>"+accounts.get(id).getUsername()+"</Username>");
		            			file.println("         "+"<Password>"+accounts.get(id).getPassword()+"</Password>");
		            			file.println("   "+"</ADMINISTRATOR>");
		            		}
		            		else {
		            			file.println("   "+"<CUSTOMER>");
		            			file.println("          "+"<id>"+id+"</id>");
		            			file.println("          "+"<Username>"+accounts.get(id).getUsername()+"</Username>");
		            			file.println("          "+"<Password>"+accounts.get(id).getPassword()+"</Password>");
		            			file.println("          "+"<Profile>"+((CustomerAccount)accounts.get(id)).getProfile()+"</Profile>");
		            			file.println("   "+"</CUSTOMER>");
		            		}
		            	}
		            }
		            file.println("</ACCOUNTS>");
		            file.close();
		        }
		        catch (IOException e) {
		            System.out.println("Error writing data file.");
		            System.exit(1);
		        }
				// Send reply to client
				if (userAccount instanceof AdminAccount) {
					reply = "ADMIN";
					System.out.println("Found admin account");
				}
				else {
					reply = "CUSTOMER";
					System.out.println("Found customer account");
				}
			}
			else {
				reply = "ERROR: Invalid password";
			}
    		System.out.println("Sending reply...");
    		outgoing.println(reply);
    		outgoing.flush();  // Make sure the data is actually sent!
    	}
    	catch (Exception e){
    		System.out.println("Error: " + e);
    	}
	}
	
	
	public void getOrder(BufferedReader incoming) {
		
	}
	
	public void sendInventory(PrintWriter outgoing) {

		
	}
	
	public void viewOrders(PrintWriter outgoing) {
		HashMap<String,String>data;
		data = InventoryReader.readFile("inventory.xml");
		String stocknumber;
		for(int i = 0; i < 100;i++) {
			stocknumber = i+"";
			if(data.containsKey(stocknumber)) {
				outgoing.println(stocknumber);
				outgoing.println(data.get(stocknumber));//description
				outgoing.flush();
			}
		}
	}
	
}
