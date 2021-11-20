//Author:Jack Peng
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.*;
import java.io.*;

class AccountsReaderTest {
	static HashMap<String, Account> dataSet = new HashMap<String,Account>();	
	static ArrayList<Account>accounts = new ArrayList<Account>();
	@Test
	void test() {
			AdminAccount adminTest = new AdminAccount(20,"admin","adminpass",accounts);
			CustomerAccount customerTest = new CustomerAccount(12,"client","clientpass","This is client");
			dataSet = AccountsReader.readFile("accounts.xml");
			accounts.add(adminTest);
			accounts.add(customerTest);
			
			assert customerTest.getID().equals(dataSet.get("12").getID());
			assert customerTest.getPassword().equals(dataSet.get("12").getPassword());
			assert customerTest.getUsername().equals(dataSet.get("12").getUsername());
			assert customerTest.getProfile().equals(((CustomerAccount)dataSet.get("12")).getProfile());
			
			assert adminTest.getID().equals(dataSet.get("20").getID());
			assert adminTest.getPassword().equals(dataSet.get("20").getPassword());
			assert adminTest.getUsername().equals(dataSet.get("20").getUsername());
		
			
	}
}
