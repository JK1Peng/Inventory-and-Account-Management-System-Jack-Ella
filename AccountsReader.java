//Author:Jack Peng
import java.io.*;
import java.util.*;

public class AccountsReader {
		static HashMap<String, Account> dataSet = new HashMap<String, Account>();
    
    	public static void main(String[] args) {
    		readFile("accounts.xml");
    	}

    	public static HashMap<String,Account> readFile(String filename){
    			ArrayList<Account>accounts 
    						= new ArrayList<Account>();//account list
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
    					String next = input.substring(separatorPosition + 1, separatorPosition2);
    					if(next.equals("CUSTOMER")) {
    						String input1 = "";
    						while(scanner.hasNextLine() && !input1.equals("/CUSTOMER")){
    							input1 = scanner.nextLine();
    							String currentLine = input1;
    							int separatorPosition3 = input1.indexOf('<');
    							int separatorPosition4 = input1.indexOf('>');
    							int separatorPosition5 = input1.indexOf('<',separatorPosition4+1);
    							input1 = input1.substring(separatorPosition3 + 1, separatorPosition4);
    							if(input1.equals("id")){
            						String id = currentLine.substring(separatorPosition4 + 1, separatorPosition5);
            						//line of username
            						String usernameLine = scanner.nextLine();
            						int separatorPosition6 = usernameLine.indexOf('>');
        							int separatorPosition7 = usernameLine.indexOf('<',separatorPosition6+1);
            						String username = usernameLine.substring(separatorPosition6 + 1, separatorPosition7);
            						//line of passowrd
            						String passwordLine = scanner.nextLine();
            						int separatorPosition8 = passwordLine.indexOf('>');
        							int separatorPosition9 = passwordLine.indexOf('<',separatorPosition8+1);
            						String password = passwordLine.substring(separatorPosition8 + 1, separatorPosition9);
            						//line of profile
            						String profileLine = scanner.nextLine();
            						int separatorPositionA = profileLine.indexOf('>');
        							int separatorPositionB = profileLine.indexOf('<',separatorPositionA+1);
            						String profile= profileLine.substring(separatorPositionA + 1, separatorPositionB);
            						CustomerAccount customer = new CustomerAccount(Integer.parseInt(id),username, password, profile);
            						accounts.add(customer);
            						dataSet.put(id, customer);
            						//System.out.println(" "+username+" "+password+" "+profile);
            					}
            					
    						}
    					}
    						else if(next.equals("ADMINISTRATOR")) {
    						String input1 = "";
    						while(scanner.hasNextLine() && !input1.equals("/ADMINISTRATOR")){
    							input1 = scanner.nextLine();
    							String currentLine = input1;
    							int separatorPosition3 = input1.indexOf('<');
    							int separatorPosition4 = input1.indexOf('>');
    							int separatorPosition5 = input1.indexOf('<',separatorPosition4+1);
    							input1 = input1.substring(separatorPosition3 + 1, separatorPosition4);
    							if(input1.equals("id")){
            						String id = currentLine.substring(separatorPosition4 + 1, separatorPosition5);
            						//line of username
            						String usernameLine = scanner.nextLine();
            						int separatorPosition6 = usernameLine.indexOf('>');
        							int separatorPosition7 = usernameLine.indexOf('<',separatorPosition6+1);
            						String username = usernameLine.substring(separatorPosition6 + 1, separatorPosition7);
            						//line of passowrd
            						String passwordLine = scanner.nextLine();
            						int separatorPosition8 = passwordLine.indexOf('>');
        							int separatorPosition9 = passwordLine.indexOf('<',separatorPosition8+1);
            						String password = passwordLine.substring(separatorPosition8 + 1, separatorPosition9);
            						AdminAccount admin = new AdminAccount(Integer.parseInt(id),username, password, accounts);
            						accounts.add(admin);
            						dataSet.put(id, admin);
            					}
        
    						}
    					}
    						
    					
    					}
    					
    			}
    			catch (IOException e) {
    				System.out.println("Error in data file.");
    				System.exit(1);
    			}
    		/*	
    			for (String key : dataSet.keySet()) {
    				System.out.print("Key = " + key + ", ");
    				System.out.println("Data =" + dataSet.get(key));
    			}
    			*/
    			return dataSet;
    	}
}