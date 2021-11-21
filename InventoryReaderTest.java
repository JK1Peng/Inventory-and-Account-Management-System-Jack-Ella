import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.net.*;
import java.io.*;
/**
 * @author ellawithington
 *
 */
class InventoryReaderTest {
	static HashMap<String, String> data = new HashMap<String, String>();	
	@Test
	void test() {
		data = InventoryReader.readFile("inventory.xml");
		for (String key : data.keySet()) {
			String x = key;
			String y = data.get(key);
			assert y.equals("box");
			assert x.equals("45");
			
		}
	}
}




