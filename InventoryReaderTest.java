import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.net.*;
import java.io.*;
class InventoryReaderTest {
	static HashMap<String, String> data = new HashMap<String, String>();	
	@Test
	void test() {
		data = InventoryReader.readFile("inventory.xml");
			String x = data.get("stockNumber");
			String y = data.get("description");
			System.out.println(x);
			System.out.println(y);
			assert y.equals("box");
			assert x.equals("45");
			
		}
	}


