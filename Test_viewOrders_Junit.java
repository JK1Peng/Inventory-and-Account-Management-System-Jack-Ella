import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_viewOrders_Junit {
	private static int LISTENING_PORT = 32007;
	private static BufferedReader incoming; // Stream for reading data.
	private static Socket client;

	@BeforeEach
	void setUp() throws Exception {
		client = new Socket("localhost", LISTENING_PORT);
		incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	@Test
	void test() {
		String reply1 = "", reply2 = "", reply3 = "";
		try {
			reply1 = incoming.readLine();
			reply2 = incoming.readLine();
			reply3 = incoming.readLine();
		} catch (Exception e) {
			System.out.println("Error:  " + e);
			return;
		}
		assert reply1.equals("45");
		assert reply2.equals("box");
		assert reply3.equals("7");
	}

	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		client.close();
	}
}
